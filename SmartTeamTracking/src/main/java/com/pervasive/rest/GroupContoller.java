package com.pervasive.rest;
import com.pervasive.model.Beacon;
import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.GroupRepository;
import com.pervasive.repository.UserRepository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GroupContoller {
	
	
	
	@Autowired
	private ApplicationContext context;
    
    @RequestMapping("/group/{groupID}")
    public Set<User> getUsers(@PathVariable Long groupID) {
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			Group GroupFromNeo = groupRepository.findOne(groupID);
			
			return GroupFromNeo.getContains();
				
        	}
		
		finally{
			tx.close();
		}
    	
    }
    
    
    
    @RequestMapping("/group/{groupID}/count")
    public int getCountUsers(@PathVariable Long groupID) {
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			Group GroupFromNeo = groupRepository.findOne(groupID);
			
			return GroupFromNeo.getContains().size();
				
        	}
		
		finally{
			tx.close();
		}
    	
    }
    
    
    
    
    

    
    @RequestMapping(method = RequestMethod.POST,value="/User/{email}/{groupID}/accept")
    public Group addContains(@PathVariable String email, @PathVariable Long groupID){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        System.out.println("EMAIL");
        System.out.println(email);
        System.out.println("GROUP IDENTIFIER");
        System.out.println(groupID);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			User UserFromNeo = userRepository.findByEmail(email);
			Group GroupFromNeo = groupRepository.findOne(groupID);
			GroupFromNeo.removeUserPending(UserFromNeo);
			GroupFromNeo.addUser(UserFromNeo);
			groupRepository.save(GroupFromNeo);
			
			
			tx.success();
			
			System.out.println("USER");
	        System.out.println(UserFromNeo);
			return GroupFromNeo;
				
        	}
		
		finally{
			tx.close();
		}
    	
    	
    	
    }
    
    
    
    @RequestMapping(method = RequestMethod.POST,value="/User/{email}/{groupID}/refuse")
    public Group removePending(@PathVariable String email, @PathVariable Long groupID){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        System.out.println("EMAIL");
        System.out.println(email);
        System.out.println("GROUP IDENTIFIER");
        System.out.println(groupID);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			User UserFromNeo = userRepository.findByEmail(email);
			Group GroupFromNeo = groupRepository.findOne(groupID);
			System.out.println(GroupFromNeo);
			System.out.println(UserFromNeo);
			System.out.println(GroupFromNeo.removeUserPending(UserFromNeo));

			groupRepository.save(GroupFromNeo);
			
			return GroupFromNeo;
				
        	}
		
		finally{
			tx.close();
		}
    	
    	
    	
    }
    
    
    
    
    @RequestMapping(method = RequestMethod.POST,value="/User/{email}/{groupID}/invite")
    public Group addPending(@PathVariable String email, @PathVariable Long groupID){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        System.out.println("EMAIL");
        System.out.println(email);
        System.out.println("GROUP IDENTIFIER");
        System.out.println(groupID);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			User UserFromNeo = userRepository.findByEmail(email);
			Group GroupFromNeo = groupRepository.findOne(groupID);
			GroupFromNeo.addUserPending(UserFromNeo);
			groupRepository.save(GroupFromNeo);
			
			tx.success();
			
			return GroupFromNeo;
				
        	}
		
		finally{
			tx.close();
		}
    	
    	
    	
    }
	
	
	

}
