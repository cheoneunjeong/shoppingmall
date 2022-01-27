package com.lcomputerstudy.example.response;

import java.util.List;

public class JwtResponse {

	private String token;
	private String username;
	private String name;
	private List<String> roles;
	private String oauth;
	private String type = "Bearer";
	
	public JwtResponse(String jwt, String id, String name, List<String> u_roles, String oauth) {
		this.token = jwt;
		this.username = id;
		this.name = name;
		this.roles = u_roles;
		this.oauth = oauth;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getOauth() {
		return oauth;
	}

	public void setOauth(String oauth) {
		this.oauth = oauth;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
