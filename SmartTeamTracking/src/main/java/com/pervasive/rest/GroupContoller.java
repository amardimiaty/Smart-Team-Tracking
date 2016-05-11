package com.pervasive.rest;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.GroupRepository;
import com.pervasive.repository.UserRepository;


@RestController
public class GroupContoller {
	
	@Autowired
	private ApplicationContext context;
    
	//Return null if group doesn't exist. Else return list of user of group identified by id. 
    @RequestMapping("/group/{groupId}")
    public Set<User> getUsers(@PathVariable Long groupId) {
    	
    	Group groupFromNeo;
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			groupFromNeo = groupRepository.findById(groupId);
			if( groupFromNeo == null){
				tx.success();
				tx.close();
				return null;
			}
		    tx.success();				
        	}
		
		finally{
			tx.close();
		}
		return groupFromNeo.getContains();    	
    }
    
    //Returns -1 if can't find group, else returns number of users of a Group. 
    @RequestMapping("/group/{groupId}/count")
    public int getCountUsers(@PathVariable Long groupId) {
    	
    	Group groupFromNeo;
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			groupFromNeo = groupRepository.findById(groupId);
			if( groupFromNeo == null){
				tx.success();
				tx.close();
				return -1;
			}
        }
		finally{
			tx.close();
		}
		return groupFromNeo.getContains().size();
    	
    }
    
 
    //Creates a new group, adds group creator and returns group identifier. In case of error returns -1. 
    @RequestMapping(method = RequestMethod.POST,value="/group")
    public long createGroup(@RequestParam(value ="userId", defaultValue="null") Long userId,
    						@RequestParam(value="name", defaultValue="null") String name,
    						@RequestParam(value="lat", defaultValue="0.0") double latitude,
    						@RequestParam(value="lon", defaultValue="0.0") double longitude,
    						@RequestParam(value="radius", defaultValue="30") int radius){
       
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
       
        long result = 0; 
        
        //Should also directly add itself to GROUP
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findById(userId);
			if(userFromNeo == null){
				tx.success();
				tx.close();
				return -1;			
			}
	
			Group newGroup = new Group(name, latitude, longitude, radius);
			newGroup.addUser(userFromNeo);
			newGroup = groupRepository.save(newGroup);
			
			if(newGroup.getId() == null){
				tx.success();
				tx.close();
				return -1;
			}
			
			result = newGroup.getId();
			tx.success();
        }
		finally{
			tx.close();
		}
		return result;
    }
    
    // Invite users in a group identified by id. List of users is sent as a list of userId in the body. 
    // Returns null if can't find group, else returns the list of users id's successfully invited. 
    @RequestMapping(method = RequestMethod.POST,value="/group/{groupId}/invite", consumes = "application/json")
    public List<Long> inviteUserToGroup(@PathVariable Long groupId, @RequestBody List<Long> users){
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
    	
    	List<Long> invitedUsers = new LinkedList<Long>();
    	
        Transaction tx = graphDatabaseService.beginTx();
		try{
			Group groupFromNeo = groupRepository.findById(groupId);
			if(groupFromNeo == null)
				return null;
			
			for(Long userId: users){
				User userToAdd = userRepository.findById(userId);
				if(userToAdd != null) {
					invitedUsers.add(userToAdd.getId());
					groupFromNeo.addUserPending(userToAdd);
				}
			}
			groupRepository.save(groupFromNeo);
			
			tx.success();
        	}
		
		finally{
			tx.close();
		}
			
		return invitedUsers;
    }
    
    
	
	
	

}
