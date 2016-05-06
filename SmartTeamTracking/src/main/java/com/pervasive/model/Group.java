package com.pervasive.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Group {
	
	@GraphId
	private Long ID;	//KEY
	private String name;
	private Double latCenter;
	private Double lonCenter;
	private Integer radius;
	
	@RelatedTo(type="CONTAINS", direction=Direction.OUTGOING)
	private @Fetch Set<User> contains;
	
	@RelatedTo(type="PENDING", direction=Direction.OUTGOING)
	private @Fetch Set<User> pending;
	
	
	@SuppressWarnings("unused")
	private Group(){}
	
	public Group(String name, double latCenter, double lonCenter, int radius) {
		super();
		ID = null;
		this.name = name;
		this.latCenter = latCenter;
		this.lonCenter = lonCenter;
		this.radius = radius;
	}

	public void addUser(User user){
		if( this.contains == null)
			this.contains = new HashSet<User>();
		this.contains.add(user);
	}
	
	public void addUserPending(User user){
		if( this.pending == null)
			this.pending = new HashSet<User>();
		this.pending.add(user);
	}
	
	public boolean removeUser( User user){
		return contains.remove(user);
	}
	
	public boolean removeUserPending(User user){
		return pending.remove(user);
	}
	
	public Set<User> getContains(){
		return this.contains;
	}
	
	public Set<User> getPending(){
		return this.pending;
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

	@Override
	public String toString() {
		return "Group [ID=" + ID + ", name=" + name + ", latCenter=" + latCenter + ", lonCenter=" + lonCenter
				+ ", radius=" + radius + ", contains=" + contains + ", pending=" + pending + "]";
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof User))return false;
	    Group g = (Group)other;
	    if(this.ID == g.ID) return true;
	    return false;
	}
	
	@Override
	public int hashCode() {
	    return this.ID.hashCode();
	}

	
	
	
	
}
