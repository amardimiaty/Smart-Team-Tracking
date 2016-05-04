package com.pervasive.repository;

import org.springframework.data.repository.CrudRepository;
import com.pervasive.model.Group;

public interface GroupRepository extends CrudRepository<Group, String>{

	Group findByName(String name);
}
