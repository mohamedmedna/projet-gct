package com.projetgct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.projetgct.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{

	
	User findByUserName(String userName);

}
