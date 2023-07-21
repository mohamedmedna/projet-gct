package com.projetgct.controller;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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

import java.io.ByteArrayOutputStream;








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
    public ResponseEntity<byte[]> generateUpdatedPdf(@PathVariable("id") Long documentId, @RequestBody Formulaire formulaire) {
        InputStream input = null;
        Optional<Document> optionalDocument = repo.findById(documentId);
        if (!optionalDocument.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Document document = optionalDocument.get();

        byte[] originalPdfContent = document.getDoc_content();
            input = new ByteArrayInputStream(originalPdfContent);
        

        try (PDDocument pdfDoc = PDDocument.load(input)) {
            PDDocumentCatalog docCatalog = pdfDoc.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();
            
            if (acroForm == null) {
                System.err.println("err.");
            }

            PDField numConsulation = acroForm.getField("#numConsultation");
            numConsulation.setValue(formulaire.getNumConsultation());

           PDField titreConsultation = acroForm.getField("#titreConsultation");
            titreConsultation.setValue(formulaire.getTitreConsultation());

            PDField objetConsultation = acroForm.getField("#objetConsultation");
            objetConsultation.setValue(formulaire.getObjetConsultation());

            PDField conditionsParticipation = acroForm.getField("#conditionsParticipation");
            conditionsParticipation.setValue(formulaire.getConditionsParticipation());

            PDField delaiLivraison = acroForm.getField("#delaiLivraison");
            delaiLivraison.setValue(String.valueOf(formulaire.getDelaiLivraison()));

            PDField dureeGarantie = acroForm.getField("#dureeGarantie");
            dureeGarantie.setValue(String.valueOf(formulaire.getDureeGarantie()));

          
            acroForm.flatten();

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
        }
    }

}

