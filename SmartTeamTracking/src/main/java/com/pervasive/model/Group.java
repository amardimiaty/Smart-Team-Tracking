package com.pervasive.model;

public class Group {
	
	private long ID;
	private String name;
	private double latCenter;
	private double lonCenter;
	private int radius;
	
	@SuppressWarnings("unused")
	private Group(){}
	
	public Group(long iD, String name, double latCenter, double lonCenter, int radius) {
		super();
		ID = iD;
		this.name = name;
		this.latCenter = latCenter;
		this.lonCenter = lonCenter;
		this.radius = radius;
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
	public double getLatCenter() {
		return latCenter;
	}
	public void setLatCenter(double latCenter) {
		this.latCenter = latCenter;
	}
	public double getLongCenter() {
		return lonCenter;
	}
	public void setLongCenter(double longCenter) {
		this.lonCenter = longCenter;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
}
