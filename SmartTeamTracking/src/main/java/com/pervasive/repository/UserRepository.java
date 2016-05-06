package com.pervasive.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import com.pervasive.model.Group;
import com.pervasive.model.User;

public interface UserRepository extends CrudRepository<User,String>{
	
	User findByName(String name);
	
	User findByEmail(String email);
	

}
