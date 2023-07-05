package com.projetgct.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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
	                                          @RequestParam("form") String form,
	                                          @RequestPart("file") MultipartFile file) {
	    try {
	        Document document = new Document();
	        document.setName(name);
	        document.setForm(form);

	        jakarta.persistence.Query query = entityManager.createQuery("SELECT s FROM Servic s WHERE s.nomservice = :nomservice");
	        query.setParameter("nomservice", nomservice);
	        Servic service = (Servic) query.getSingleResult();

	        if (service == null) {
	            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	        }

	        document.setServic(service);
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
}

