package com.projetgct.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetgct.entities.Role;
import com.projetgct.repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repo;

	public Role createNewrole(Role role) {
		return repo.save(role);

	}

}
