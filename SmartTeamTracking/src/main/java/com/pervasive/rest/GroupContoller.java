package com.pervasive.rest;
import com.pervasive.model.Beacon;
import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.GroupRepository;
import com.pervasive.repository.UserRepository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GroupContoller {
	
	
	
	@Autowired
	private ApplicationContext context;
    
    @RequestMapping("/group/{groupID}")
    public List<User> getUsers(@PathVariable Long groupID) {
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			Iterable<User> users = groupRepository.getUsers(groupID);
			Iterator<User> it = users.iterator();
			tx.success();
			List<User> result = new LinkedList<User>();
			
			while(it.hasNext()){
				result.add(it.next());
				
				}
			
			return result;
				
        	}
		
		finally{
			tx.close();
		}
    	
    }
	
	
	

}
