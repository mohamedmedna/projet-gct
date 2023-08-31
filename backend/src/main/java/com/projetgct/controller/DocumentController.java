package com.projetgct.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetgct.entities.Document;
import com.projetgct.entities.Formulaire;
import com.projetgct.entities.GeneratedDocument;
import com.projetgct.entities.Servic;
import com.projetgct.repositories.DocumentRepo;
import com.projetgct.repositories.GeneratedDocumentRepo;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Controller
@CrossOrigin("http://localhost:4200")
public class DocumentController {

	@Autowired
	private DocumentRepo repo;

	@Autowired
	private GeneratedDocumentRepo generatedDocumentRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${python.script.path}")
	private Resource pythonScriptPath;

	@Value("${doc.folder.path}")
	private Resource docFolder;

	@PostMapping(value = "/adddocument")
	@Transactional
	public ResponseEntity<String> addDocument(@RequestParam("name") String name,
			@RequestParam("nomservice") String nomservice, @RequestParam("formulairenom") String formulairenom,
			@RequestPart("file") MultipartFile file) {
		try {
			Document document = new Document();
			document.setName(name);

			jakarta.persistence.Query query = entityManager
					.createQuery("SELECT s FROM Servic s WHERE s.nomservice = :nomservice");
			query.setParameter("nomservice", nomservice);
			Servic service = (Servic) query.getSingleResult();

			jakarta.persistence.Query query1 = entityManager
					.createQuery("SELECT f FROM Formulaire f WHERE f.nom = :formulairenom");
			query1.setParameter("formulairenom", formulairenom);
			Formulaire formulaire = (Formulaire) query1.getSingleResult();

			if (service == null || formulaire == null) {
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
	public ResponseEntity<Resource> downloadDocument(@PathVariable("iddoc") Long iddoc) {
	    Document document = repo.findById(iddoc).orElse(null);

	    if (document == null) {
	        return ResponseEntity.notFound().build();
	    }

	    ByteArrayResource resource = new ByteArrayResource(document.getDoc_content());

	    return ResponseEntity.ok()
	    		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + ".docx" + "\"")
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

	@PostMapping("/{id}/generate-updated-docx")
	public ResponseEntity<byte[]> generateUpdatedDocx(@PathVariable("id") Long documentId,
			@RequestBody Formulaire formulaire) throws Exception {
		Optional<Document> optionalDocument = repo.findById(documentId);
		if (!optionalDocument.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Document document = optionalDocument.get();
		byte[] docContent = document.getDoc_content();
		String base64EncodedDocContent = Base64.encodeBase64String(docContent);

		Map<String, String> replacements = new HashMap<>();
		if (formulaire.isNumConsultationestVisible()) {
			replacements.put("#numConsultation", formulaire.getNumConsultation());

		}
		if (formulaire.isTitreConsultationestVisible()) {
			replacements.put("#titreConsultation", formulaire.getTitreConsultation());

		}
		if (formulaire.isObjetConsultationestVisible()) {
			replacements.put("#objetConsultation", formulaire.getObjetConsultation());

		}
		if (formulaire.isConditionsParticipationestVisible()) {
			replacements.put("#conditionsParticipation", formulaire.getConditionsParticipation());

		}
		if (formulaire.isDureeGarantieestVisible()) {
			replacements.put("#dureeGarantie", formulaire.getDureeGarantie());

		}
		if (formulaire.isDelaiLivraisonestVisible()) {
			replacements.put("#delaiLivraison", formulaire.getDelaiLivraison());

		}

		String pythonScript = pythonScriptPath.getFile().getAbsolutePath();
		String docFolderPath = docFolder.getFile().getAbsolutePath();
		String modifiedFilePath = docFolderPath + "/output_modified.docx";
      
          
 
		File replacementsFile = File.createTempFile("replacements", ".json");
		try (FileWriter fileWriter = new FileWriter(replacementsFile)) {
			ObjectMapper objectMapper = new ObjectMapper();
			String replacementsJson = objectMapper.writeValueAsString(replacements);
			fileWriter.write(replacementsJson);
		}

		ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonScript, base64EncodedDocContent,
				docFolderPath, replacementsFile.getAbsolutePath());

		processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
		processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
		Process process = processBuilder.start();

		int exitCode = process.waitFor();

		if (exitCode == 0) {
			byte[] modifiedDocContent = Files.readAllBytes(Paths.get(modifiedFilePath));
			GeneratedDocument generatedDocument = new GeneratedDocument();
			Random rand = new Random();
			int rand_int = rand.nextInt(50);
			generatedDocument.setDocumentName(document.getName() + rand_int + "_modified.docx");
			generatedDocument.setDoc_content(modifiedDocContent);
			generatedDocument.setServic(document.getServic());
			generatedDocument = generatedDocumentRepo.save(generatedDocument);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDisposition(
					ContentDisposition.parse("attachment; filename=" + document.getName() + "_modified.docx"));

			return new ResponseEntity<>(modifiedDocContent, headers, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
