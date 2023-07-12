package com.projetgct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.projetgct.entities.ChampVisibilite;
import com.projetgct.entities.Formulaire;
import com.projetgct.repositories.FormulaireRepo;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:4200")

public class FormulaireController {
    

    @Autowired
    FormulaireRepo repo;
   
    @GetMapping("/formulaires")
    public ResponseEntity<List<Formulaire>> getAllFormulaires() {
        List<Formulaire> formulaires = repo.findAll();

        for (Formulaire formulaire : formulaires) {
            List<ChampVisibilite> champsVisibilite = repo.findByFormulaireId(formulaire.getId());
            formulaire.setChampsVisibilite(champsVisibilite);
        }

        return new ResponseEntity<>(formulaires, HttpStatus.OK);
    }

    @PostMapping("/addformulaire")
    public ResponseEntity<Formulaire> addFormulaire(@RequestBody Formulaire formulaire) {
        Formulaire newFormulaire = repo.save(formulaire);
        return new ResponseEntity<>(newFormulaire, HttpStatus.CREATED);
    }

  

    @DeleteMapping("/formulaire/{id}")
    public ResponseEntity<HttpStatus> deleteFormulaire(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

