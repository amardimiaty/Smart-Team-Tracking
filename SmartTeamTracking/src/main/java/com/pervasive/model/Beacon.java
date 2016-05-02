package com.pervasive.model;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Beacon {
	
	@GraphId
	private Long ID;
	private String name;
	private Double latBeacon;
	private Double lonBeacon;
	
	public Beacon(long iD, String name, double latBeacon, double lonBeacon) {
		super();
		ID = iD;
		this.name = name;
		this.latBeacon = latBeacon;
		this.lonBeacon = lonBeacon;
	}
	
	@SuppressWarnings("unused")
	private Beacon(){}

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

	public double getLatBeacon() {
		return latBeacon;
	}

	public void setLatBeacon(double latBeacon) {
		this.latBeacon = latBeacon;
	}

	public double getLonBeacon() {
		return lonBeacon;
	}

	public void setLonBeacon(double lonBeacon) {
		this.lonBeacon = lonBeacon;
	}

	@Override
	public String toString() {
		return "Beacon [ID=" + ID + ", name=" + name + ", latBeacon=" + latBeacon + ", lonBeacon=" + lonBeacon + "]";
	}
	
	
	
}
