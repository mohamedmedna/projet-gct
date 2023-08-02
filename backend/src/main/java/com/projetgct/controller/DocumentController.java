package com.projetgct.controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Make sure to import the correct Optional class

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


import com.projetgct.entities.Document;
import com.projetgct.entities.Formulaire;
import com.projetgct.entities.Servic;
import com.projetgct.repositories.DocumentRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import com.groupdocs.redaction.Redaction;
import com.groupdocs.redaction.Redactor;
import com.groupdocs.redaction.options.RasterizationOptions;
import com.groupdocs.redaction.redactions.DeleteAnnotationRedaction;
import com.groupdocs.redaction.redactions.EraseMetadataRedaction;
import com.groupdocs.redaction.redactions.ExactPhraseRedaction;
import com.groupdocs.redaction.redactions.MetadataFilters;
import com.groupdocs.redaction.redactions.ReplacementOptions;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;





@Controller
@CrossOrigin("http://localhost:4200")
public class DocumentController {
	


	@Autowired
	private DocumentRepo repo;
	
	@Autowired
	
	@PersistenceContext
    private EntityManager entityManager;

	

	
	@PostMapping(value="/adddocument")
	@Transactional
	public ResponseEntity<String> addDocument(@RequestParam("name") String name, 
	                                          @RequestParam("nomservice") String nomservice, 
	                                          @RequestParam("formulairenom") String formulairenom,
	                                          @RequestPart("file") MultipartFile file) {
	    try {
	        Document document = new Document();
	        document.setName(name);

	        jakarta.persistence.Query query = entityManager.createQuery("SELECT s FROM Servic s WHERE s.nomservice = :nomservice");
	        query.setParameter("nomservice", nomservice);
	        Servic service = (Servic) query.getSingleResult();
	        
	        jakarta.persistence.Query query1 = entityManager.createQuery("SELECT f FROM Formulaire f WHERE f.nom = :formulairenom");
	        query1.setParameter("formulairenom", formulairenom);
	        Formulaire formulaire = (Formulaire) query1.getSingleResult();

	        if (service == null || formulaire==null) {
	            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	        }
	        


	        document.setServic(service);
	        document.setFormulaire(formulaire);
	        document.setDoc_content(file.getBytes());
	        entityManager.persist(document);
	        return ResponseEntity.status(HttpStatus.CREATED).body("Document ajouté avec succès.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du document.");
	    }
	}



	@GetMapping("/download/{iddoc}")
	public ResponseEntity<ByteArrayResource> downloadDocument(@PathVariable("iddoc") Long iddoc) {
	    Document document = repo.findById(iddoc).orElse(null);

	    if (document == null) {
	        return ResponseEntity.notFound().build();
	    }

	    ByteArrayResource resource = new ByteArrayResource(document.getDoc_content());

	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getName())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .contentLength(document.getDoc_content().length)
	            .body(resource);
	}


	@GetMapping("/documents")
	public ResponseEntity<List<Document>> getAllDocuments(@RequestParam(required = false) String nom) {
		try {
			List<Document> documents = new ArrayList<Document>();
			if (nom == null)
				repo.findAll().forEach(documents::add);
		
			if (documents.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(documents, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

}
	@GetMapping("/documents/{serviceName}")
	public ResponseEntity<List<Document>> getDocumentsByService(@PathVariable("serviceName") String serviceName) {
	    try {
	        List<Document> documents = repo.findByServiceName(serviceName);
	        return new ResponseEntity<>(documents, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@DeleteMapping("/documents/{id}")
	public ResponseEntity<HttpStatus> deleteDocument(@PathVariable("id") Long id) {
		try {
			repo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	
	 
	
	
	  @PostMapping("/{id}/generate-updated-pdf") 
	  public ResponseEntity<byte[]> generateUpdatedPdf(@PathVariable("id") Long documentId, @RequestBody Formulaire formulaire) throws Exception {
		  Optional<Document> optionalDocument = repo.findById(documentId); 
		  if (!optionalDocument.isPresent()) { 
			  return ResponseEntity.notFound().build(); 
			  }
	  
	  Document document = optionalDocument.get(); 
	  byte[] originalPdfContent =document.getDoc_content(); 
	  
	  
	  final Redactor redactor = new Redactor(new ByteArrayInputStream(originalPdfContent));
	  
	  
	  try { 
		  Redaction[] redactionList=new Redaction[] 
			  {  
		new ExactPhraseRedaction("#numConsultation", new ReplacementOptions(formulaire.getNumConsultation())),
		new ExactPhraseRedaction("#titreConsultation",new ReplacementOptions(formulaire.getTitreConsultation())), 
		new ExactPhraseRedaction("#objetConsultation",new ReplacementOptions(formulaire.getObjetConsultation())),
		new ExactPhraseRedaction("#conditionsParticipations",new ReplacementOptions(formulaire.getConditionsParticipation())),
		new ExactPhraseRedaction("#delaiLivraison",new ReplacementOptions(String.valueOf(formulaire.getDelaiLivraison()))),
		new ExactPhraseRedaction("#dureeGarantie",new ReplacementOptions(String.valueOf(formulaire.getDureeGarantie()))),
		new DeleteAnnotationRedaction(), 
		new EraseMetadataRedaction(MetadataFilters.All)
	  }; 
		  
		  redactor.apply(redactionList);
	  
	  
	  
	  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	  RasterizationOptions rasterOptions = new RasterizationOptions();
	  rasterOptions.setEnabled(false); 
	  redactor.save(outputStream, rasterOptions);
	  
	  byte[] updatedPdfContent = outputStream.toByteArray();
	  
	  
	 HttpHeaders headers = new HttpHeaders(); 
	 headers.setContentType(MediaType.APPLICATION_PDF);
	 headers.setContentDisposition(ContentDisposition.parse("attachment; filename="+document.getName()+"_modified.pdf"));
	  
	  return new ResponseEntity<>(updatedPdfContent, headers, HttpStatus.OK); 
	  }
	  catch (IOException e ) { 
		  e.printStackTrace(); 
		  return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); } 
	  
	  }
}
	
/*	@PostMapping("/{id}/generate-updated-pdf")
	public ResponseEntity<byte[]> generateUpdatedPdf(@PathVariable("id") Long documentId, @RequestBody Formulaire formulaire) {
	    Optional<Document> optionalDocument = repo.findById(documentId);
	    if (!optionalDocument.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    Document document = optionalDocument.get();

	    byte[] originalPdfContent = document.getDoc_content();

	    try (PDDocument pdfDoc = PDDocument.load(new ByteArrayInputStream(originalPdfContent))) {
	        List<PDPage> pagesToRemove = new ArrayList<>();

	        for (PDPage page : pdfDoc.getPages()) {
	            PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, page, PDPageContentStream.AppendMode.OVERWRITE, true);

	            // Set the font and position for the updated text
	            contentStream.setFont(PDType1Font.HELVETICA, 12);
	            contentStream.beginText();
	            contentStream.newLineAtOffset(50, 700);

	            // Replace placeholders in the text and write it to the updated page
	            String text = new PDFTextStripper().getText(pdfDoc);
	            String updatedText = text
	                    .replace("#numConsultation", formulaire.getNumConsultation())
	                    .replace("#titreConsultation", formulaire.getTitreConsultation())
	                    .replace("#objetConsultation", formulaire.getObjetConsultation())
	                    .replace("#conditionsParticipation", formulaire.getConditionsParticipation())
	                    .replace("#delaiLivraison", String.valueOf(formulaire.getDelaiLivraison()))
	                    .replace("#dureeGarantie", String.valueOf(formulaire.getDureeGarantie()));

	            contentStream.showText(updatedText);
	            contentStream.endText();
	            contentStream.close();
	        }

	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        pdfDoc.save(outputStream);
	        byte[] updatedPdfContent = outputStream.toByteArray();

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDisposition(ContentDisposition.parse("attachment; filename=updated-document.pdf"));

	        return new ResponseEntity<>(updatedPdfContent, headers, HttpStatus.OK);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    */
