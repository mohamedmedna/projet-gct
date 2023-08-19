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
@Table(name = "document")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iddoc")
	private Long iddoc;

	@Column(name = "name")
	private String name;

	@Column(name = "doc_content", length = 1000000000)
	private byte[] doc_content;

	@ManyToOne
	@JoinColumn(name = "idservice", nullable = false)
	private Servic servic;

	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private Formulaire formulaire;

	public Document() {
		super();
	}

	public Document(String name, byte[] doc_content, Servic servic, Formulaire formulaire) {
		super();
		this.name = name;
		this.doc_content = doc_content;
		this.servic = servic;
		this.formulaire = formulaire;
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

	public byte[] getDoc_content() {
		return doc_content;
	}

	public void setDoc_content(byte[] doc_content) {
		this.doc_content = doc_content;
	}

	public Servic getServic() {
		return servic;
	}

	public void setServic(Servic servic) {
		this.servic = servic;
	}

	public Formulaire getFormulaire() {
		return formulaire;
	}

	public void setFormulaire(Formulaire formulaire) {
		this.formulaire = formulaire;
	}

}
