package com.pervasive.model;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Beacon {
	
	@GraphId
	private Long id; //KEY
	private Integer major;
	private Integer minor;
	private String name;
	private Double latBeacon;
	private Double lonBeacon;
	
	public Beacon(Integer major, Integer minor, String name, Double latBeacon, Double lonBeacon) {
		super();
		this.major = major;
		this.minor = minor;
		this.name = name;
		this.latBeacon = latBeacon;
		this.lonBeacon = lonBeacon;
	}


	@SuppressWarnings("unused")
	private Beacon(){}

	
	public Long getId() {
		return id;
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

	public double getLatBeacon() {
		return latBeacon;
	}

	public void setLatBeacon(Double latBeacon) {
		this.latBeacon = latBeacon;
	}

	public double getLonBeacon() {
		return lonBeacon;
	}

	public void setLonBeacon(Double lonBeacon) {
		this.lonBeacon = lonBeacon;
	}
	
	

	public Integer getMajor() {
		return major;
	}


	public void setMajor(Integer major) {
		this.major = major;
	}


	public Integer getMinor() {
		return minor;
	}


	public void setMinor(Integer minor) {
		this.minor = minor;
	}


	@Override
	public String toString() {
		return "Beacon [id=" + id + ", major=" + major + ", minor=" + minor + ", name=" + name + ", latBeacon="
				+ latBeacon + ", lonBeacon=" + lonBeacon + "]";
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
		Beacon other = (Beacon) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
	
}
