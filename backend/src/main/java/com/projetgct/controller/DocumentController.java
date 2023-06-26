package com.projetgct.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/adddocument")
    @Transactional
    public ResponseEntity<String> addDocument(
            @RequestParam String name,
            @RequestParam String docUrl,
            @RequestParam Long idService
    ) {
        try {
            Document document = new Document();
            document.setDocUrl(docUrl);
            document.setName(name);

            Servic service = entityManager.find(Servic.class, idService);
            if (service == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found.");
            }
            document.setServic(service);

            entityManager.persist(document);
            return ResponseEntity.status(HttpStatus.CREATED).body("Document added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding document.");
        }
    }

	/*@PostMapping("/adddocument")
	public ResponseEntity<Document> addDocument(@RequestBody Document doc){
		try {
			Document document=repo.save(new Document(doc.getName(), doc.getDocUrl(),doc.getServic()));
			return new ResponseEntity<>(document,HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}*/

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
}

