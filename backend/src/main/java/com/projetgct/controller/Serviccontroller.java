package com.projetgct.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projetgct.entities.Servic;
import com.projetgct.repositories.ServiceRepo;

@Controller
@CrossOrigin("http://localhost:4200")
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
@ResponseBody
public List<String> getAllServices() {
    List<Servic> services = repo.findAll();
    return services.stream().map(Servic::getNomservice).collect(Collectors.toList());
}


}


