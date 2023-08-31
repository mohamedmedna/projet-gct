
package com.projetgct.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projetgct.entities.Role;
import com.projetgct.entities.Servic;
import com.projetgct.entities.User;
import com.projetgct.repositories.RoleRepository;
import com.projetgct.repositories.ServiceRepo;
import com.projetgct.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
public class UserController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ServiceRepo servicRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${app.init.role.name}")
	private String roleName;

	@Value("${app.init.role.description}")
	private String roleDescription;

	@Value("${app.init.service.name}")
	private String serviceName;

	@Value("${app.init.user.name}")
	private String userName;

	@Value("${app.init.user.username}")
	private String userUsername;

	@Value("${app.init.user.password}")
	private String userPassword;

	@PostConstruct
	@Transactional
	public String initRoleAndUserAdmin() {
		Optional<Role> existingAdminRole = roleRepository.findById(roleName);

		try {
			if (!existingAdminRole.isPresent()) {
				Role adminRole = new Role();
				adminRole.setRoleName(roleName);
				adminRole.setRoleDescription(roleDescription);
				roleRepository.save(adminRole);

				Optional<Servic> existingService = servicRepository.findByNomservice(serviceName);

				if (!existingService.isPresent()) {
					Servic servic = new Servic();
					servic.setNomservice(serviceName);
					servicRepository.save(servic);

					User adminUser = new User();
					adminUser.setNomPrenom(userName);
					adminUser.setUserName(userUsername);
					adminUser.setPassword(userPassword);
					adminUser.setServic(servic);

					Set<Role> adminRoles = new HashSet<>();
					adminRoles.add(adminRole);
					adminUser.setRole(adminRoles);
					userRepository.save(adminUser);

					return "Role, User, and Service admin Initialized!";
				} else {
					return "Role and User admin Initialized, but Service already exists.";
				}
			} else {
				return "Role and User admin already exist.";
			}
		} catch (Exception e) {
			return "Error";
		}
	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@PostMapping("/addUser")
	@Transactional
	public ResponseEntity<String> addUser(@RequestParam("userName") String userName,
			@RequestParam("nomPrenom") String nomPrenom, @RequestParam("userPassword") String userPassword,
			@RequestParam("roleNames") Set<String> roleNames, @RequestParam("nomservice") String nomservice) {
		try {
			User user = new User();
			user.setUserName(userName);
			user.setNomPrenom(nomPrenom);
			user.setUserPassword(userPassword);

			Set<Role> roles = new HashSet<>();
			for (String roleName : roleNames) {
				Role role = roleRepository.findByRoleName(roleName);
				if (role != null) {
					roles.add(role);
				}
			}
			user.setRole(roles);

			jakarta.persistence.Query query = entityManager
					.createQuery("SELECT s FROM Servic s WHERE s.nomservice = :nomservice");
			query.setParameter("nomservice", nomservice);
			Servic service = (Servic) query.getSingleResult();

			if (service == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

			user.setServic(service);
			entityManager.persist(user);
			return ResponseEntity.status(HttpStatus.CREATED).body("User ajouté avec succès.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erreur lors de l'ajout de l'utilisateur.");
		}
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String nom) {
		try {
			List<User> Users = new ArrayList<User>();
			if (nom == null)
				userRepository.findAll().forEach(Users::add);

			if (Users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(Users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long Id) {
		Optional<User> userData = userRepository.findById(Id);
		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		Optional<User> userData = userRepository.findById(id);

		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setUserName(user.getUserName());
			_user.setNomPrenom(user.getNomPrenom());
			_user.setPassword(user.getPassword());

			return new ResponseEntity<User>(userRepository.save(_user), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	/*
	 * @DeleteMapping("/users/{id}") public ResponseEntity<HttpStatus>
	 * deleteUser(@PathVariable("id") Long id) { try { jakarta.persistence.Query
	 * query =
	 * entityManager.createQuery("DELETE FROM User_Role u WHERE u.user_id = :id");
	 * query.setParameter("id", id); query.executeUpdate();
	 * 
	 * userRepository.deleteById(id);
	 * 
	 * return new ResponseEntity<>(HttpStatus.NO_CONTENT); } catch (Exception e) {
	 * return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

}
