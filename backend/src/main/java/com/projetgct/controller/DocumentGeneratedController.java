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
import org.springframework.web.bind.annotation.RequestParam;

import com.projetgct.entities.Document;
import com.projetgct.entities.GeneratedDocument;
import com.projetgct.repositories.GeneratedDocumentRepo;

@Controller
@CrossOrigin("http://localhost:4200")
public class DocumentGeneratedController {

	@Autowired
	private GeneratedDocumentRepo generatedDocumentRepo;

	@GetMapping("/generateddocuments")
	public ResponseEntity<List<GeneratedDocument>> getAllDocumentsGenerated(
			@RequestParam(required = false) String nom) {
		try {
			List<GeneratedDocument> documents = new ArrayList<GeneratedDocument>();
			if (nom == null)
				generatedDocumentRepo.findAll().forEach(documents::add);

			if (documents.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(documents, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/generateddocuments/{serviceName}")
	public ResponseEntity<List<GeneratedDocument>> getGeneratedDocumentsByService(
			@PathVariable("serviceName") String serviceName) {
		try {
			List<GeneratedDocument> generatedDocuments = generatedDocumentRepo.findByServiceName(serviceName);
			return new ResponseEntity<>(generatedDocuments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/generatedDownload/{iddoc}")
	public ResponseEntity<ByteArrayResource> downloadDocument(@PathVariable("iddoc") Long iddoc) {
		GeneratedDocument document = generatedDocumentRepo.findById(iddoc).orElse(null);

		if (document == null) {
			return ResponseEntity.notFound().build();
		}

		ByteArrayResource resource = new ByteArrayResource(document.getDoc_content());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getDocumentName())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(document.getDoc_content().length)
				.body(resource);
	}

	@DeleteMapping("/generateddocuments/{id}")
	public ResponseEntity<HttpStatus> deleteDocumentGenerated(@PathVariable("id") Long id) {
		try {
			generatedDocumentRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
