package com.projetgct.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="document")
public class Document {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="iddoc")
private Long iddoc;

@Column(name="name")
private String name;

@Column(name="doc_url")
private String doc_url;

@ManyToOne
@JoinColumn(name="idservice",nullable=false)
private Servic servic;


public Document() {
	super();
}


public Document(String name, String docUrl, Servic servic) {
	super();
	this.name = name;
	this.doc_url = docUrl;
	this.servic = servic;
}





public Long getIddoc() {
	return iddoc;
}


public void setIddoc(Long iddoc) {
	this.iddoc = iddoc;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public String getDocUrl() {
	return doc_url;
}


public void setDocUrl(String docUrl) {
	this.doc_url = docUrl;
}


public Servic getServic() {
	return servic;
}


public void setServic(Servic servic) {
	this.servic = servic;
}










}
