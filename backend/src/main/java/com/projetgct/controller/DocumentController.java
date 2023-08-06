package com.projetgct.controller;



import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.Map;
import java.util.Optional;


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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetgct.entities.Document;
import com.projetgct.entities.Formulaire;
import com.projetgct.entities.Servic;
import com.projetgct.repositories.DocumentRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;






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
	        byte[] pdfContent = document.getDoc_content();
	        String base64EncodedPdf = Base64.encodeBase64String(pdfContent);

		       List<List<Map<String, String>>> redactions = new ArrayList<>();

		       List<Map<String, String>> page1Redactions = new ArrayList<>();
		       if (formulaire.isNumConsultationestVisible()) {
		           page1Redactions.add(Map.of(
		               "top_left_x", "610",
		               "top_left_y", "1089",
		               "bottom_right_x", "1058",
		               "bottom_right_y", "1135",
		               "text", formulaire.getNumConsultation()
		           ));
		       }

		       if (formulaire.isTitreConsultationestVisible()) {
		           page1Redactions.add(Map.of(
		               "top_left_x", "785",
		               "top_left_y", "1294",
		               "bottom_right_x", "1227",
		               "bottom_right_y", "1336",
		               "text", formulaire.getTitreConsultation()
		           ));
		       }
		       redactions.add(page1Redactions);

		        List<Map<String, String>> page2Redactions = new ArrayList<>();
		        if(formulaire.isObjetConsultationestVisible()) {
		        page2Redactions.add(Map.of(
		            "top_left_x", "767",
		            "top_left_y", "291",
		            "bottom_right_x", "1055",
		            "bottom_right_y", "321",
		            "text", formulaire.getObjetConsultation()
		        ));
		        }
		        if(formulaire.isConditionsParticipationestVisible()) {
		        page2Redactions.add(Map.of(
		            "top_left_x", "531",
		            "top_left_y", "355",
		            "bottom_right_x", "1051",
		            "bottom_right_y", "391",
		            "text", formulaire.getConditionsParticipation()
		        ));
		        }
		        if(formulaire.isNumConsultationestVisible()) {
		        page2Redactions.add(Map.of(
		            "top_left_x", "992",
		            "top_left_y", "1019",
		            "bottom_right_x", "1273",
		            "bottom_right_y", "1048",
		            "text", formulaire.getNumConsultation()
		        ));
		        }
		        redactions.add(page2Redactions);

		        List<Map<String, String>> page3Redactions = new ArrayList<>();
		        if(formulaire.isDelaiLivraisonestVisible()) {
		        page3Redactions.add(Map.of(
		            "top_left_x", "787",
		            "top_left_y", "608",
		            "bottom_right_x", "1009",
		            "bottom_right_y", "635",
		            "text", String.valueOf(formulaire.getDelaiLivraison())
		        ));
		        }
		        if(formulaire.isDureeGarantieestVisible()) {
		        page3Redactions.add(Map.of(
		            "top_left_x", "711",
		            "top_left_y", "782",
		            "bottom_right_x", "924",
		            "bottom_right_y", "811",
		            "text", String.valueOf(formulaire.getDureeGarantie())
		        ));
		        }
		        redactions.add(page3Redactions);
		        
		        PDDocument pdfDocument = PDDocument.load(pdfContent);
		        int numberOfPages = pdfDocument.getNumberOfPages();
		        pdfDocument.close();
		        

		        
		        for (int i = 0; i < numberOfPages-3; i++) {
		            redactions.add(new ArrayList<>());
		        }

		            ObjectMapper objectMapper = new ObjectMapper();
		            String redactionsJson = objectMapper.writeValueAsString(redactions);
		            
		            
		           
		            String pythonScript = "/home/mohamed/projet-gct/pdf_redaction.py";
		            String imageFolderPath = "/home/mohamed/projet-gct/output_images/document";

		            ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonScript, base64EncodedPdf, imageFolderPath, redactionsJson);
		            processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
		            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
		            Process process = processBuilder.start();

		            int exitCode = process.waitFor();

		            if (exitCode == 0) {
		                byte[] redactedPdfContent = Files.readAllBytes(Paths.get(imageFolderPath + "_redacted.pdf")); 
		                HttpHeaders headers = new HttpHeaders();
		                headers.setContentType(MediaType.APPLICATION_PDF);
		                headers.setContentDisposition(ContentDisposition.parse("attachment; filename=" + document.getName() + "_modified.pdf"));
		                return new ResponseEntity<>(redactedPdfContent, headers, HttpStatus.OK);
		            } else {
		                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		            }
		        }
	

		        
		}
		        
	   

	    
	    






