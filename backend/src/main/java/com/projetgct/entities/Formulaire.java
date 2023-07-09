package com.projetgct.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Formulaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String numConsultation;
    private String titreConsultation;
    private String objetConsultation;
    private String conditionsParticipation;
    private String delaiLivraison;
    private String autreAttribut;
    private boolean isDisabled;
	public Formulaire(String nom, String numConsultation, String titreConsultation, String objetConsultation,
			String conditionsParticipation, String delaiLivraison, String autreAttribut, boolean isDisabled) {
		super();
		this.nom = nom;
		this.numConsultation = numConsultation;
		this.titreConsultation = titreConsultation;
		this.objetConsultation = objetConsultation;
		this.conditionsParticipation = conditionsParticipation;
		this.delaiLivraison = delaiLivraison;
		this.autreAttribut = autreAttribut;
		this.isDisabled = isDisabled;
	}
	public Formulaire() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getNumConsultation() {
		return numConsultation;
	}
	public void setNumConsultation(String numConsultation) {
		this.numConsultation = numConsultation;
	}
	public String getTitreConsultation() {
		return titreConsultation;
	}
	public void setTitreConsultation(String titreConsultation) {
		this.titreConsultation = titreConsultation;
	}
	public String getObjetConsultation() {
		return objetConsultation;
	}
	public void setObjetConsultation(String objetConsultation) {
		this.objetConsultation = objetConsultation;
	}
	public String getConditionsParticipation() {
		return conditionsParticipation;
	}
	public void setConditionsParticipation(String conditionsParticipation) {
		this.conditionsParticipation = conditionsParticipation;
	}
	public String getDelaiLivraison() {
		return delaiLivraison;
	}
	public void setDelaiLivraison(String delaiLivraison) {
		this.delaiLivraison = delaiLivraison;
	}
	public String getAutreAttribut() {
		return autreAttribut;
	}
	public void setAutreAttribut(String autreAttribut) {
		this.autreAttribut = autreAttribut;
	}
	public boolean isDisabled() {
		return isDisabled;
	}
	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

   
}
