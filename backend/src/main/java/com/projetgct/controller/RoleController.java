package com.projetgct.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projetgct.entities.Role;
import com.projetgct.entities.Servic;
import com.projetgct.repositories.RoleRepository;
import com.projetgct.services.RoleService;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	@Autowired
	private RoleRepository repo;
	
	@PostMapping({"/createNewRole"})
	public Role createNewRole(@RequestBody Role role) {
		return service.createNewrole(role);
		
		
	}
	
	@GetMapping("/roless")
	@ResponseBody
	public List<String> getAllRolesNames() {
		List<Role> roles = repo.findAll();
		return roles.stream().map(Role::getRoleName).collect(Collectors.toList());
	}

}
