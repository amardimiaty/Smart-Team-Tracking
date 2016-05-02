package com.pervasive.repository;

import org.springframework.data.repository.CrudRepository;
import com.pervasive.model.Beacon;

public interface BeaconRepository extends CrudRepository<Beacon, String>{
	
	Beacon findByName(String name);

}
