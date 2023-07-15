package com.projetgct.entities;


import java.sql.Date;
import java.util.Set;

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
    private boolean numConsultationestVisible=true;
    
    
    private String titreConsultation;
    private boolean titreConsultationestVisible=true;
    
    
    private String objetConsultation;
    private boolean objetConsultationestVisible=true;
    
    private String conditionsParticipation;
    private boolean conditionsParticipationestVisible=true;
    
    private Date delaiLivraison;
    private boolean delaiLivraisonestVisible=true;
    
    private Date dureeGarantie;
    private boolean dureeGarantieestVisible=true;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formulaire")
    private Set<Document> documents;


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

    public Date getDelaiLivraison() {
        return delaiLivraison;
    }

    public void setDelaiLivraison(Date delaiLivraison) {
        this.delaiLivraison = delaiLivraison;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isNumConsultationestVisible() {
		return numConsultationestVisible;
	}

	public void setNumConsultationestVisible(boolean numConsultationestVisible) {
		this.numConsultationestVisible = numConsultationestVisible;
	}

	public boolean isTitreConsultationestVisible() {
		return titreConsultationestVisible;
	}

	public void setTitreConsultationestVisible(boolean titreConsultationestVisible) {
		this.titreConsultationestVisible = titreConsultationestVisible;
	}

	public boolean isObjetConsultationestVisible() {
		return objetConsultationestVisible;
	}

	public void setObjetConsultationestVisible(boolean objetConsultationestVisible) {
		this.objetConsultationestVisible = objetConsultationestVisible;
	}

	public boolean isConditionsParticipationestVisible() {
		return conditionsParticipationestVisible;
	}

	public void setConditionsParticipationestVisible(boolean conditionsParticipationestVisible) {
		this.conditionsParticipationestVisible = conditionsParticipationestVisible;
	}

	public boolean isDelaiLivraisonestVisible() {
		return delaiLivraisonestVisible;
	}

	public void setDelaiLivraisonestVisible(boolean delaiLivraisonestVisible) {
		this.delaiLivraisonestVisible = delaiLivraisonestVisible;
	}

	public Date getDureeGarantie() {
		return dureeGarantie;
	}

	public void setDureeGarantie(Date dureeGarantie) {
		this.dureeGarantie = dureeGarantie;
	}

	public boolean isDureeGarantieestVisible() {
		return dureeGarantieestVisible;
	}

	public void setDureeGarantieestVisible(boolean dureeGarantieestVisible) {
		this.dureeGarantieestVisible = dureeGarantieestVisible;
	}
	
	


	
    
}

