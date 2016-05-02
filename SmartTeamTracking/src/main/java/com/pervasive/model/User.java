package com.pervasive.model;

public class User {
	
	private long ID;
	private String name;
	private String surname;
	private String email;
	private String password;
	private double latGPS;
	private double lonGPS;
	
	@SuppressWarnings("unused")
	private User(){}
	
	public User(long iD, String name, String surname, String email, String password) {
		super();
		ID = iD;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
	}
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getLatGPS() {
		return latGPS;
	}
	public void setLatGPS(double latGPS) {
		this.latGPS = latGPS;
	}
	public double getLongGPS() {
		return lonGPS;
	}
	public void setLongGPS(double longGPS) {
		this.lonGPS = longGPS;
	}

	
	
}
