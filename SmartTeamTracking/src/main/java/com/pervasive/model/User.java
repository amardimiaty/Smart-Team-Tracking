package com.pervasive.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class User {
	
	@GraphId
	private long ID;
	private String name;
	private String surname;
	private String email;
	private String password;
	private double latGPS;
	private double lonGPS;
	
	@RelatedTo(type="IN_RANGE", direction=Direction.OUTGOING)
	private Beacon beacon;
	
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
	
	public Beacon getInRange() {
		return beacon;
	}

	public void setInRange(Beacon inRange) {
		this.beacon = inRange;
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
	
	public double getLonGPS() {
		return lonGPS;
	}
	
	public void setLonGPS(double longGPS) {
		this.lonGPS = longGPS;
	}

	
	
}
