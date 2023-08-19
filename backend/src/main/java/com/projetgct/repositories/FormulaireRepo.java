package com.projetgct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.projetgct.entities.Formulaire;

@Repository
public interface FormulaireRepo extends JpaRepository<Formulaire, Long> {

}
