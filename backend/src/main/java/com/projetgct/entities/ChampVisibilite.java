package com.projetgct.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChampVisibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "formulaire_id", nullable = false)
    private Formulaire formulaire;

    private String champNom;
    private boolean visible;
	public ChampVisibilite() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Formulaire getFormulaire() {
		return formulaire;
	}
	public void setFormulaire(Formulaire formulaire) {
		this.formulaire = formulaire;
	}
	public String getChampNom() {
		return champNom;
	}
	public void setChampNom(String champNom) {
		this.champNom = champNom;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
    
}
    
    
    
