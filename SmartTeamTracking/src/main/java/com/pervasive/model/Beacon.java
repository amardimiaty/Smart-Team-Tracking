package com.pervasive.model;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Beacon {
	
	@GraphId
	private Long ID;
	private Long beaconIdentifier; //KEY
	private String name;
	private Double latBeacon;
	private Double lonBeacon;
	
	public Beacon(Long beaconIdentifier, String name, Double latBeacon, Double lonBeacon) {
		super();
		ID = null;
		this.beaconIdentifier = beaconIdentifier;
		this.name = name;
		this.latBeacon = latBeacon;
		this.lonBeacon = lonBeacon;
	}
	
	@SuppressWarnings("unused")
	private Beacon(){}

	
	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Long getBeaconIdentifier() {
		return beaconIdentifier;
	}

	public void setBeaconIdentifier(Long beaconIdentifier) {
		this.beaconIdentifier = beaconIdentifier;
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

	@Override
	public String toString() {
		return "Beacon [ID=" + ID +", beaconIdentifier=" + beaconIdentifier+ ", name=" + name + ", latBeacon=" + latBeacon + ", lonBeacon=" + lonBeacon + "]";
	}
	
	
	
}
