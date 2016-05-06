package com.pervasive.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import com.pervasive.model.Group;
import com.pervasive.model.User;

public interface GroupRepository extends CrudRepository<Group, String>{

	Group findByName(String name);

	Group findOne(Long ID);
	
	@Query("MATCH (g:Group)-[r:CONTAINS]->(u:User) WHERE u.email={0} RETURN g")
	Iterable<Group> getGroupsforUser(String userMail);
	
	@Query("MATCH (g:Group)-[r:PENDING]->(u:User) WHERE u.email={0} RETURN g")
	Iterable<Group> getPendingGroupsForUser(String userMail);
	
	
	
}
