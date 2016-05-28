package com.pervasive.model;


import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class User {
	
	@GraphId
	private Long id; //KEY
	private String name;
	private String surname;
	private String email;	
	private Double latGPS;
	private Double lonGPS;
	private String authToken;
	private String facebookId;
	
	@RelatedTo(type="IN_RANGE", direction=Direction.OUTGOING)
	private @Fetch Beacon beacon;  
	
	@SuppressWarnings("unused")
	private User(){}
	
	public User(String name, String surname, String email,String facebookId) {
		super();
		id = null;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.facebookId = facebookId;
	}
	
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
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
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", latGPS=" + latGPS
				+ ", lonGPS=" + lonGPS + ", authToken=" + authToken + ", facebookId=" + facebookId + ", beacon="
				+ beacon + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
