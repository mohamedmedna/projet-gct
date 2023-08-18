
package com.projetgct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projetgct.entities.JwtRequest;
import com.projetgct.entities.JwtResponse;
import com.projetgct.services.JwtService;

@RestController

@CrossOrigin(origins = "http://localhost:4200")
public class JwtController {

	@Autowired
	private JwtService jwtService;

	@PostMapping({ "/authenticate" })
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		return jwtService.CreateJwtToken(jwtRequest);
	}
}
