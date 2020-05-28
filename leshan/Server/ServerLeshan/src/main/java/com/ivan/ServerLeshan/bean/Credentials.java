package com.ivan.ServerLeshan.bean;

public class Credentials {
	
	private String username;
	private String email;
	private int role;
	private String token;
	
	public Credentials(String username, String email, int role, String token) {
		this.username = username;
		this.email = email;
		this.role = role;
		this.token = token;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}