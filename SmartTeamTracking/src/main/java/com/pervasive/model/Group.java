package com.pervasive.model;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Group {
	
	@GraphId
	private Long id;	//KEY
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
	
	public Group(String name, Double latCenter, Double lonCenter, int radius) {
		super();
		id = null;
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
	
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getLatCenter() {
		return latCenter;
	}
	public void setLatCenter(Double latCenter) {
		this.latCenter = latCenter;
	}
	public Double getLongCenter() {
		return lonCenter;
	}
	public void setLongCenter(Double longCenter) {
		this.lonCenter = longCenter;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	//Used to invalid contains relationship to not send users. Never save the changes, only used in rest API
	public void invalidContains(){
		this.contains = null;
	}
	
	//Used to invalid pending relationship to not send users. Never save the changes, only used in rest API
	public void invalidPending(){
		this.pending = null;
	}

	@Override
	public String toString() {
		return "Group [ID=" + id + ", name=" + name + ", latCenter=" + latCenter + ", lonCenter=" + lonCenter
				+ ", radius=" + radius + ", contains=" + contains + ", pending=" + pending + "]";
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof User))return false;
	    Group g = (Group)other;
	    if(this.id == g.id) return true;
	    return false;
	}
	
	@Override
	public int hashCode() {
	    return this.id.hashCode();
	}

	
	
	
	
}
