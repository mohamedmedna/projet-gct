package com.projetgct.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
	
	
	 
	
	/*
	 * @GetMapping("generatepdf") public ResponseEntity<HttpStatus> generatepdf()
	 * throws Exception { final Redactor redactor = new
	 * Redactor("/home/mohamed/Downloads/Mini CC 27-04-2023 sans retenue de garantie.pdf"
	 * );
	 * 
	 * try { Redaction[] redactionList=new Redaction[] { // Find exact phrase in PDF
	 * and replace it with some other text using Java new
	 * RegexRedaction("#numConsultation", new ReplacementOptions("[censored]")), new
	 * RegexRedaction("#titreConsultation", new ReplacementOptions("[med]")), new
	 * DeleteAnnotationRedaction(), new EraseMetadataRedaction(MetadataFilters.All)
	 * 
	 * }; redactor.apply(redactionList);
	 * 
	 * 
	 * // Save the redacted file at a different location with a different name.
	 * FileOutputStream stream = new
	 * FileOutputStream("/home/mohamed/Downloads/exactPhrase.pdf");
	 * RasterizationOptions rasterOptions = new RasterizationOptions();
	 * rasterOptions.setEnabled(false); redactor.save(stream,rasterOptions);
	 * 
	 * return new ResponseEntity<>(HttpStatus.NO_CONTENT); } catch (IOException e) {
	 * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	 * 
	 * } }
	 */
	
	/*
	 * @GetMapping("generatepdf") public ResponseEntity<String> generatepdf() { try
	 * { Map<String, String> map = new HashMap<>(); map.put("#numConsultation",
	 * "1247");
	 * 
	 * File template = new
	 * File("/home/mohamed/Downloads/Mini CC 27-04-2023 sans retenue de garantie.pdf"
	 * ); PDDocument document = Loader.loadPDF(template);
	 * 
	 * PDDocumentCatalog docCatalog = document.getDocumentCatalog(); PDAcroForm
	 * acroForm = docCatalog.getAcroForm();
	 * 
	 * if (acroForm != null) { List<PDField> fields = acroForm.getFields(); for
	 * (PDField field : fields) { for (Map.Entry<String, String> entry :
	 * map.entrySet()) { if (entry.getKey().equals(field.getFullyQualifiedName())) {
	 * field.setValue(entry.getValue()); field.setReadOnly(true); } } } } else { //
	 * Handle the case when the PDF template doesn't have an AcroForm return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).
	 * body("The PDF template does not have form fields."); }
	 * 
	 * File out = new File("/home/mohamed/Downloads/out.pdf"); document.save(out);
	 * document.close();
	 * 
	 * return ResponseEntity.ok().build(); } catch (IOException e) {
	 * e.printStackTrace(); return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); } }
	 */


	@GetMapping("/generatepdf")
	public ResponseEntity<Resource> generatePdf(
	        @RequestParam("inputPdfFile") MultipartFile inputPdfFile,
	        @RequestParam("oldWord") String oldWord,
	        @RequestParam("newWord") String newWord) {
	    try {
	        File inputPdf = convertMultipartFileToFile(inputPdfFile);

	        PdfReader reader = new PdfReader(inputPdf.getAbsolutePath());

	        File outputPdf = new File("output.pdf");
	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputPdf));

	        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	            String text = PdfTextExtractor.getTextFromPage(reader, i);

	            int index = text.indexOf(oldWord);
	            if (index != -1) {
	                text = text.substring(0, index) + newWord + text.substring(index + oldWord.length());
	                PdfContentByte cb = stamper.getOverContent(i);
	                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
	                cb.setFontAndSize(bf, 12);
	                cb.setTextMatrix(36, 36);
	                cb.showText(text);
	            }
	        }

	        stamper.close();
	        reader.close();

	        Resource resource = new FileSystemResource(outputPdf);
	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.pdf")
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(resource);
	    } catch (IOException | DocumentException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
	    File file = new File(multipartFile.getOriginalFilename());
	    try (FileOutputStream fos = new FileOutputStream(file)) {
	        fos.write(multipartFile.getBytes());
	    }
	    return file;
	}


}