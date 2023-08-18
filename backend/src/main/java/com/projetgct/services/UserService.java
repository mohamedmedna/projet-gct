
package com.projetgct.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetgct.entities.Role;
import com.projetgct.entities.Servic;
import com.projetgct.entities.User;
import com.projetgct.repositories.RoleRepository;
import com.projetgct.repositories.ServiceRepo;
import com.projetgct.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ServiceRepo serviceRepository;

	public User addUserauth(User user) {
		return userRepository.save(user);

	}

	/*public void initRolesAndUserauth() {
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin Role");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setRoleName("Supervisor");
		userRole.setRoleDescription("supervisor Role");
		roleRepository.save(userRole);

		Servic servic = serviceRepository.findById((long) 1).orElse(null);

		User adminUser = new User();
		adminUser.setNomPrenom("Admin Account");
		adminUser.setUserName("admin");
		adminUser.setServic(servic);
		adminUser.setPassword(getEncodedPassword("admin"));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userRepository.save(adminUser);

		User supervisor = new User();
		supervisor.setNomPrenom("Supervisor Account");
		supervisor.setUserName("supervisor");
		supervisor.setServic(servic);

		supervisor.setPassword(getEncodedPassword("supervisor"));
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(userRole);
		supervisor.setRole(userRoles);
		userRepository.save(supervisor);

	}*/

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

}
