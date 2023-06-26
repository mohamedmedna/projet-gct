package com.projetgct.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="service")
public class Servic {

@Id
@Column(name = "idservice")
private Long idservice;

@Column(name = "nomservice")
private String nomservice;

@OneToMany(cascade = CascadeType.ALL, mappedBy = "servic")
private Set<Document> documentsS;

public Long getIdservice() {
	return idservice;
}
public void setIdservice(Long idservice) {
	this.idservice = idservice;
}
public String getNomservice() {
	return nomservice;
}
public void setNomservice(String nomservice) {
	this.nomservice = nomservice;
}
public Servic(Long idservice, String nomservice) {
	super();
	this.idservice = idservice;
	this.nomservice = nomservice;
}
public Servic() {
	super();
}



}
