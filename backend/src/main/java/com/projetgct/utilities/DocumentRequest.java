package com.projetgct.utilities;

public class DocumentRequest {
    private String name;
    private String docUrl;
    private String nomservice;
    
    
    
    
	public DocumentRequest() {
		super();
	}
	public DocumentRequest(String name, String docUrl, String nomservice) {
		super();
		this.name = name;
		this.docUrl = docUrl;
		this.nomservice = nomservice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDocUrl() {
		return docUrl;
	}
	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}
	public String getNomservice() {
		return nomservice;
	}
	public void setNomservice(String nomservice) {
		this.nomservice = nomservice;
	}

    
}

