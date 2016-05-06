package com.pervasive.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import com.pervasive.model.Beacon;
import com.pervasive.model.Group;

public interface BeaconRepository extends CrudRepository<Beacon, String>{
	
	Beacon findByName(String name);
	
	Beacon findByBeaconIdentifier(Long beaconIdentifier);
	
	
	@Query("MATCH (beacon:Beacon) RETURN beacon")
	Iterable<Beacon> getBeacons();

}
