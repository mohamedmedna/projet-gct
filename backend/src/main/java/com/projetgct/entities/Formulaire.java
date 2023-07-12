package com.projetgct.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Formulaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;
    private String nom;

    private String numConsultation;
    private String titreConsultation;
    private String objetConsultation;
    private String conditionsParticipation;
    private String delaiLivraison;
    @OneToMany(mappedBy = "formulaire", cascade = CascadeType.ALL)
    private List<ChampVisibilite> champsVisibilite;

    public Formulaire() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<ChampVisibilite> getChampsVisibilite() {
		return champsVisibilite;
	}

	public void setChampsVisibilite(List<ChampVisibilite> champsVisibilite) {
		this.champsVisibilite = champsVisibilite;
	}
	
	
    
}

