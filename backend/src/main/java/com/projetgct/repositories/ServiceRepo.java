package com.projetgct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetgct.entities.Servic;

@Repository
public interface ServiceRepo extends JpaRepository<Servic, Long> {

}
