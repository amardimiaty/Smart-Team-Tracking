package com.pervasive.model;


import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class User {
	
	@GraphId
	private Long id;
	private String name;
	private String surname;
	private String email;	//KEY
	private String password;	
	private Double latGPS;
	private Double lonGPS;
	
	@RelatedTo(type="IN_RANGE", direction=Direction.OUTGOING)
	private @Fetch Beacon beacon;  
	
	@SuppressWarnings("unused")
	private User(){}
	
	public User(String name, String surname, String email, String password) {
		super();
		id = null;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	
	public Beacon getBeacon() {
		return this.beacon;
	}

	public void setBeacon(Beacon beacon) {
		this.beacon = beacon;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public Double getLatGPS() {
		return latGPS;
	}
	
	public void setLatGPS(Double latGPS) {
		this.latGPS = latGPS;
	}
	
	public Double getLonGPS() {
		return lonGPS;
	}
	
	public void setLonGPS(Double longGPS) {
		this.lonGPS = longGPS;
	}

	@Override
	public String toString() {
		return "User [ID=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + ", latGPS=" + latGPS + ", lonGPS=" + lonGPS + ", beacon=" + beacon + "]";
	}
	
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof User))return false;
	    User user = (User)other;
	    if(this.email.equals(user.email)) return true;
	    return false;
	}
	
	@Override
	public int hashCode() {
	    return email.hashCode();
	}
	
}
