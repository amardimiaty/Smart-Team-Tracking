package com.pervasive.rest;
import com.pervasive.model.Beacon;
import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.BeaconRepository;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private ApplicationContext context;
    
    @RequestMapping("/user")
    public User findUser(@RequestParam(value="name", defaultValue="null") String name) {
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			User UserFromNeo = userRepository.findByName(name);
			tx.success();
			
			if(UserFromNeo == null) return null;
			return UserFromNeo;
				
        	}
		
		finally{
			tx.close();
		}
    	
    }
    
    @RequestMapping(method = RequestMethod.POST,value="/user/{email}/{beaconIdentifier}")
    public String addInRange(@PathVariable String email, @PathVariable Long beaconIdentifier){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	BeaconRepository beaconRepository = (BeaconRepository) context.getBean(BeaconRepository.class);
    	
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        System.out.println("EMAIL");
        System.out.println(email);
        System.out.println("BEACON IDENTIFIER");
        System.out.println(beaconIdentifier);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			User UserFromNeo = userRepository.findByEmail(email);
			Beacon BeaconFromNeo = beaconRepository.findByBeaconIdentifier(beaconIdentifier);
			
			
			System.out.println("first call done");
			if(beaconIdentifier!=null)
				UserFromNeo.setBeacon(BeaconFromNeo);
			
			userRepository.save(UserFromNeo);
			tx.success();
			
			System.out.println("USER");
	        System.out.println(UserFromNeo);
			if(UserFromNeo.getEmail().equals(email) ) return "Transaction done successfully!";
			return "Transaction failed";
				
        	}
		
		finally{
			tx.close();
		}
    	
    	
    	
    }
    
  
    @RequestMapping("/user/{email}/groups")
    public List<Group> getGroupsOfUsers(@PathVariable String email){
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        List<Group> groupList = new LinkedList<>();

    	Transaction tx = graphDatabaseService.beginTx();
    	try{
			Iterable<Group> iterableGroup = groupRepository.getGroupsforUser(email);
			Iterator<Group> it = iterableGroup.iterator();
			while (it.hasNext()){
				Group g = it.next();
				g.invalidContains();
				g.invalidPending();
				groupList.add(g);
			}
			tx.success();	
        }
		finally{
			tx.close();
		}
		return groupList;
    }
    

    
    @RequestMapping("/user/{email}/pending")
    public List<Group> getPendingGroupsOfUsers(@PathVariable String email){
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        List<Group> groupList = new LinkedList<>();

    	Transaction tx = graphDatabaseService.beginTx();
    	try{
			Iterable<Group> iterableGroup = groupRepository.getPendingGroupsForUser(email);
			Iterator<Group> it = iterableGroup.iterator();
			while (it.hasNext()){
				Group g = it.next();
				g.invalidContains();
				g.invalidPending();
				groupList.add(g);
			}
			tx.success();	
        }
		finally{
			tx.close();
		}
		return groupList;
    }
    
    
    @RequestMapping(method = RequestMethod.POST,value="/user/{email}/")
    public String updateUserGPSCoordinates(@PathVariable String email,
    									   @RequestParam(value="lat", defaultValue="null") Double latitude, 
    									   @RequestParam(value="lon", defaultValue="null") Double longitude){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        Transaction tx = graphDatabaseService.beginTx();
		try{
			
			System.out.println("Email received is:");
			System.out.println(email);
			User userFromNeo = userRepository.findByEmail(email);
			if(userFromNeo == null){
				System.out.println("Result of userFromNeo:");
				System.out.println(userFromNeo);
				return null;
			}
			
			userFromNeo.setLatGPS(latitude);
			userFromNeo.setLonGPS(longitude);
			userRepository.save(userFromNeo);
			
			tx.success();
        	}
		
		finally{
			tx.close();
		}
		return "Updated user coordinates successfully";
    }
    
    
    @RequestMapping(method = RequestMethod.POST,value="/user/{email}/{groupId}/accept")
    public Group addContains(@PathVariable String email, @PathVariable Long groupId){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        System.out.println("EMAIL");
        System.out.println(email);
        System.out.println("GROUP IDENTIFIER");
        System.out.println(groupId);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			User UserFromNeo = userRepository.findByEmail(email);
			Group GroupFromNeo = groupRepository.findById(groupId);
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
    
    
    
    @RequestMapping(method = RequestMethod.POST,value="/user/{email}/{groupId}/refuse")
    public Group removePending(@PathVariable String email, @PathVariable Long groupId){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        System.out.println("EMAIL");
        System.out.println(email);
        System.out.println("GROUP IDENTIFIER");
        System.out.println(groupId);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			User UserFromNeo = userRepository.findByEmail(email);
			Group GroupFromNeo = groupRepository.findById(groupId);
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
    
    
    @RequestMapping(method = RequestMethod.POST,value="/user/{email}/{groupId}/invite")
    public Group addPending(@PathVariable String email, @PathVariable Long groupId){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        System.out.println("EMAIL");
        System.out.println(email);
        System.out.println("GROUP IDENTIFIER");
        System.out.println(groupId);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			User UserFromNeo = userRepository.findByEmail(email);
			Group GroupFromNeo = groupRepository.findById(groupId);
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
