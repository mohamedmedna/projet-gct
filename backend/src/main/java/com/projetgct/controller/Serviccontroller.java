package com.projetgct.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projetgct.entities.Servic;
import com.projetgct.repositories.ServiceRepo;

@Controller
@CrossOrigin("http://localhost:4200")
public class Serviccontroller {

	@Autowired
	ServiceRepo repo;

	@PostMapping("/addservice")
	public ResponseEntity<Servic> addservic(@RequestBody Servic serv) {
		try {
			Servic servic = repo.save(new Servic(serv.getNomservice()));
			return new ResponseEntity<>(servic, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/servicess")
	@ResponseBody
	public List<String> getAllServicesnames() {
		List<Servic> services = repo.findAll();
		return services.stream().map(Servic::getNomservice).collect(Collectors.toList());
	}

	@GetMapping("/servics")
	public ResponseEntity<List<Servic>> getAllServices(@RequestParam(required = false) String nom) {
		try {
			List<Servic> services = new ArrayList<Servic>();
			if (nom == null)
				repo.findAll().forEach(services::add);

			if (services.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(services, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/service/{id}")
	public ResponseEntity<Servic> updateservic(@PathVariable("id") Long id, @RequestBody Servic servic) {
		Optional<Servic> servicdata = repo.findById(id);

		if (servicdata.isPresent()) {
			Servic _servic = servicdata.get();
			_servic.setNomservice(servic.getNomservice());

			return new ResponseEntity<Servic>(repo.save(_servic), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@DeleteMapping("/service/{id}")
	public ResponseEntity<HttpStatus> deleteServic(@PathVariable("id") Long id) {
		try {
			repo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
