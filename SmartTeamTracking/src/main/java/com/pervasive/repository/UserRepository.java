package com.pervasive.repository;

import org.springframework.data.repository.CrudRepository;

import com.pervasive.model.User;

public interface UserRepository extends CrudRepository<User,String>{
	
	User findByName(String name);
	
	User findByEmail(String email);
	

}
