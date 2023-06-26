package com.projetgct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetgct.entities.Document;

@Repository
public interface DocumentRepo extends JpaRepository<Document,Long> {
	




}
