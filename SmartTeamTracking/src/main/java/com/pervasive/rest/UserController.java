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
    
	//Returns null if can't find User 
    @RequestMapping("/user")
    public User findUser(@RequestParam(value="name", defaultValue="null") String name) {
    	
    	User userFromNeo;
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			userFromNeo = userRepository.findByName(name);
			tx.success();
		
			if(userFromNeo == null) {
				tx.close();
				return null;
			}				
        }
		finally{
			tx.close();
		}
		return userFromNeo;
    }
    
    
    //Returns true if correctly executed, if can find either group or beacon returns false 
    @RequestMapping(method = RequestMethod.POST,value="/user/{email}/{beaconIdentifier}")
    public boolean addInRange(@PathVariable String email, @PathVariable Long beaconIdentifier){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	BeaconRepository beaconRepository = (BeaconRepository) context.getBean(BeaconRepository.class);
    	
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findByEmail(email);
			Beacon beaconFromNeo = beaconRepository.findByBeaconIdentifier(beaconIdentifier);
			
			if(userFromNeo == null){
				tx.success();
				tx.close();
				return false;
			}
			
			//This also covers the case in which the beacon is null. 
			userFromNeo.setBeacon(beaconFromNeo);
			userRepository.save(userFromNeo);
			tx.success();
        }
		finally{
			tx.close();
		}
		return true;
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
    public boolean updateUserGPSCoordinates(@PathVariable String email,
    									   @RequestParam(value="lat", defaultValue="null") Double latitude, 
    									   @RequestParam(value="lon", defaultValue="null") Double longitude){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findByEmail(email);
			if(userFromNeo == null){
				tx.success();
				tx.close();
				return false;
			}
			
			userFromNeo.setLatGPS(latitude);
			userFromNeo.setLonGPS(longitude);
			userRepository.save(userFromNeo);
			
			tx.success();
        	}
		
		finally{
			tx.close();
		}
		return true;
    }
    
    
    @RequestMapping(method = RequestMethod.POST,value="/user/{email}/{groupId}/accept")
    public Group addContains(@PathVariable String email, @PathVariable Long groupId){
    	
    	Group groupFromNeo;
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findByEmail(email);
			groupFromNeo = groupRepository.findById(groupId);
			if( userFromNeo == null || groupFromNeo == null){
				tx.success();
				tx.close();
				return null;
			}
			
			groupFromNeo.removeUserPending(userFromNeo);
			groupFromNeo.addUser(userFromNeo);
			groupRepository.save(groupFromNeo);
			tx.success();				
        }
		finally{
			tx.close();
		}
		return groupFromNeo;
    }
    
    
    @RequestMapping(method = RequestMethod.POST,value="/user/{email}/{groupId}/refuse")
    public boolean removePending(@PathVariable String email, @PathVariable Long groupId){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findByEmail(email);
			Group groupFromNeo = groupRepository.findById(groupId);
		    if(userFromNeo == null || groupFromNeo == null){
		    	tx.success();
		    	tx.close();
		    	return false;
		    }
	
			groupFromNeo.removeUserPending(userFromNeo);
		    groupRepository.save(groupFromNeo);
		    tx.success();
        }
		finally{
			tx.close();
		}
		return true;
    }
   
    
   
    /* This API shouldn't be needed! Can invite from group! 
    @RequestMapping(method = RequestMethod.POST,value="/user/{email}/{groupId}/invite")
    public Group addPending(@PathVariable String email, @PathVariable Long groupId){
    	
    	Group groupFromNeo;
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			User UserFromNeo = userRepository.findByEmail(email);
			groupFromNeo = groupRepository.findById(groupId);
			groupFromNeo.addUserPending(UserFromNeo);
			groupRepository.save(groupFromNeo);
			tx.success();
        }
		finally{
			tx.close();
		}
		return groupFromNeo;
    }
    */
}
