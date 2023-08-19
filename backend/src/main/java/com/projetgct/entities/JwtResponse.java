package com.projetgct.entities;

public class JwtResponse {
	
	private User user;
	private String jwtToken;
	public User getUserauth() {
		return user;
	}
	public void setUserauth(User user) {
		this.user = user;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public JwtResponse(User user, String jwtToken) {
		super();
		this.user = user;
		this.jwtToken = jwtToken;
	}
	public JwtResponse() {
		super();
	}
	
	
	

}
