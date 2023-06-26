package com.projetgct.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.projetgct.entities.Servic;
import com.projetgct.repositories.ServiceRepo;

@Controller
public class Serviccontroller {

@Autowired
private ServiceRepo repo;

@PostMapping("/addservice")
public ResponseEntity<Servic> addservic(@RequestBody Servic serv){
	try {
		Servic servic=repo.save(new Servic(serv.getIdservice(), serv.getNomservice()));
		return new ResponseEntity<>(servic,HttpStatus.OK);
		
	}catch(Exception e) {
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}

@GetMapping("/services")
public ResponseEntity<List<Servic>> getAllservices(@RequestParam(required = false) String nom) {
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

}
