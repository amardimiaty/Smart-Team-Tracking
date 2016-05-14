package com.pervasive.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import com.pervasive.model.Beacon;

public interface BeaconRepository extends CrudRepository<Beacon, String>{
	
	Beacon findByName(String name);	
	
	@Query("MATCH (n:Beacon) WHERE n.major={0} AND n.minor={1} RETURN n")
	Beacon findByMajorMinor(Integer major, Integer minor);

}
