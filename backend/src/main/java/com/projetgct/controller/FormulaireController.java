package com.projetgct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import com.projetgct.entities.Formulaire;
import com.projetgct.repositories.FormulaireRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:4200")

public class FormulaireController {
    

    @Autowired
    FormulaireRepo repo;
   
    @GetMapping("/formulaires")
    public ResponseEntity<List<Formulaire>> getAllFormulaires() {
        List<Formulaire> formulaires = repo.findAll();
        return new ResponseEntity<>(formulaires, HttpStatus.OK);
    }

    @PostMapping("/addformulaire")
    public ResponseEntity<Formulaire> addFormulaire(@RequestBody Formulaire formulaire) {
        Formulaire newFormulaire = repo.save(formulaire);
        return new ResponseEntity<>(newFormulaire, HttpStatus.CREATED);
    }
    
    @PutMapping("/formulaire/{id}")
    public ResponseEntity<Formulaire> updateFormulaire(@PathVariable("id") Long id, @RequestBody Formulaire updatedFormulaire) {
        Optional<Formulaire> existingFormulaireOptional = repo.findById(id);
        if (existingFormulaireOptional.isPresent()) {
            Formulaire existingFormulaire = existingFormulaireOptional.get();
            existingFormulaire.setNumConsultationestVisible(updatedFormulaire.isNumConsultationestVisible());
            existingFormulaire.setTitreConsultationestVisible(updatedFormulaire.isTitreConsultationestVisible());
            existingFormulaire.setObjetConsultationestVisible(updatedFormulaire.isObjetConsultationestVisible());
            existingFormulaire.setConditionsParticipationestVisible(updatedFormulaire.isConditionsParticipationestVisible());
            existingFormulaire.setDelaiLivraisonestVisible(updatedFormulaire.isDelaiLivraisonestVisible());
            
            updatedFormulaire = repo.save(existingFormulaire);
            return new ResponseEntity<>(updatedFormulaire, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    @GetMapping("/formulaire/{id}")
    public ResponseEntity<Formulaire> getFormulaireById(@PathVariable("id") Long id) {
        Optional<Formulaire> formulaireOptional = repo.findById(id);
        return formulaireOptional.map(formulaire -> new ResponseEntity<>(formulaire, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @GetMapping("/listesformulaires")
    @ResponseBody
    public List<String> getAllFormsnames() {
        List<Formulaire> formulaires = repo.findAll();
        return formulaires.stream().map(Formulaire::getNom).collect(Collectors.toList());
    }

    @PutMapping("/{id}/update-champ-visibility")
    public ResponseEntity<Formulaire> updateChampVisibility(
            @PathVariable("id") Long id,
            @RequestParam("champ") String champ,
            @RequestParam("visible") boolean visible) {
        Optional<Formulaire> optionalFormulaire = repo.findById(id);
        if (optionalFormulaire.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Formulaire formulaire = optionalFormulaire.get();
        switch (champ) {
            case "numConsultation":
                formulaire.setNumConsultationestVisible(visible);
                break;
            case "titreConsultation":
                formulaire.setTitreConsultationestVisible(visible);
                break;
            case "objetConsultation":
                formulaire.setObjetConsultationestVisible(visible);
                break;
            case "conditionsParticipation":
                formulaire.setConditionsParticipationestVisible(visible);
                break;
            case "delaiLivraison":
                formulaire.setDelaiLivraisonestVisible(visible);
                break;
            case "dureeGarantie":
            	formulaire.setDureeGarantieestVisible(visible);
            	break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Formulaire updatedFormulaire = repo.save(formulaire);
        return new ResponseEntity<>(updatedFormulaire, HttpStatus.OK);
    }



    @DeleteMapping("/formulaire/{id}")
    public ResponseEntity<HttpStatus> deleteFormulaire(@PathVariable Long id) {
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

