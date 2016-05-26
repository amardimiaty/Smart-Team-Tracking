package com.pervasive.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import com.pervasive.model.Beacon;
import com.pervasive.model.Group;

public interface BeaconRepository extends CrudRepository<Beacon, String>{
	
	Beacon findByName(String name);	
	
	Beacon findById(Long id);
	
	@Query("MATCH (n:Beacon) WHERE n.major={0} AND n.minor={1} RETURN n")
	Beacon findByMajorMinor(Integer major, Integer minor);

}
