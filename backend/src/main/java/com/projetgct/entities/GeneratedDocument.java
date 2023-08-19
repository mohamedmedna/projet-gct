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
@Table(name="documentGenerated")
public class GeneratedDocument {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="iddocgenerated")
    private Long id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="doc_content",length=1000000000)
    private  byte[] doc_content;
    
    @ManyToOne
    @JoinColumn(name="idservice",nullable=false)
    private Servic servic;
    
   /* @ManyToOne
    @JoinColumn(name="Id",nullable=false)
    private User user;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentName() {
		return name;
	}

	public void setDocumentName(String documentName) {
		this.name = documentName;
	}

	public byte[] getDoc_content() {
		return doc_content;
	}

	public void setDoc_content(byte[] doc_content) {
		this.doc_content = doc_content;
	}

	public GeneratedDocument(Long id, String documentName, byte[] doc_content,Servic servic) {
		super();
		this.id = id;
		this.name = documentName;
		this.doc_content = doc_content;
		this.servic=servic;
		/*this.user=user;*/
	}

	public GeneratedDocument() {
		super();
	}
	
	public Servic getServic() {
		return servic;
	}


	public void setServic(Servic servic) {
		this.servic = servic;
	}

	/*public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/
	
	
    
    
}

