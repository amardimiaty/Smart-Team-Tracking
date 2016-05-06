package com.pervasive.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import com.pervasive.model.Group;
import com.pervasive.model.User;

public interface UserRepository extends CrudRepository<User,String>{
	
	User findByName(String name);
	
	@Query("MATCH (u:User {email:{0}}), (b1:Beacon {beaconIdentifier:{1}}) CREATE (u)-[:IN_RANGE]->(b1) RETURN u")
	User addInRange(String email, Long beaconIdentifier);
	
	@Query("MATCH (u:User {email:{0}})-[r:IN_RANGE]->(b1:Beacon) DELETE r RETURN u")
	User removeInRange(String email);
	
	@Query("MATCH (g:Group)-[r:PENDING]->(u:User {email:{0}}) where ID(g)={1} DELETE r RETURN u")
	User removePending(String email, Long GroupID);
	
	@Query("MATCH (u:User {email:{0}}), (g:Group) where ID(g)={1} CREATE (g)-[:CONTAINS]->(u) RETURN g")
	Group addContains(String email, Long GroupID);
	
	@Query("MATCH (u:User {email:{0}}), (g:Group) where ID(g)={1} CREATE (g)-[:PENDING]->(u) RETURN u")
	User addPending(String email, Long GroupID);
	

}
