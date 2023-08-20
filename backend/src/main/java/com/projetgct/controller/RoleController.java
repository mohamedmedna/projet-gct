package com.projetgct.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projetgct.entities.Role;
import com.projetgct.repositories.RoleRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RoleController {



	@Autowired
	private RoleRepository repo;

	@PostMapping("/addrole")
	public ResponseEntity<Role> addRole(@RequestBody Role role) {
		try {
			Role _role = repo.save(new Role(role.getRoleName(),role.getRoleDescription()));
			return new ResponseEntity<>(_role, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllroles(@RequestParam(required = false) String nom) {
		try {
			List<Role> roles = new ArrayList<Role>();
			if (nom == null)
				repo.findAll().forEach(roles::add);

			if (roles.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(roles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@DeleteMapping("/role/{id}")
	public ResponseEntity<HttpStatus> deleteRole(@PathVariable("id") String id) {
		try {
			repo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	

	

	/*@GetMapping("/roless")
	@ResponseBody
	public List<String> getAllRolesNames() {
		List<Role> roles = repo.findAll();
		return roles.stream().map(Role::getRoleName).collect(Collectors.toList());
	}*/
	@GetMapping("/roless")
	@ResponseBody
	public List<String> getAllRolesNames() {
	    List<Role> roles = repo.findAll();
	    List<String> roleNames = new ArrayList<>();

	    for (Role role : roles) {
	        roleNames.add(role.getRoleName());
	    }

	    return roleNames;
	}


}
