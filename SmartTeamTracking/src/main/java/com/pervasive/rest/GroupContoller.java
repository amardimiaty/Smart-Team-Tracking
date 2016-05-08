package com.pervasive.rest;
import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.GroupRepository;
import com.pervasive.repository.UserRepository;

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
    
 
    //Creates a new group and returns group identifier. In case of error returns 0. 
    @RequestMapping(method = RequestMethod.POST,value="/group")
    public long createGroup(@RequestParam(value="name", defaultValue="null") String name,
    						@RequestParam(value="lat", defaultValue="0.0") double latitude,
    						@RequestParam(value="lon", defaultValue="0.0") double longitude,
    						@RequestParam(value="radius", defaultValue="30") int radius){
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        long result = 0; 
        
        //Should also directly add itself to GROUP
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			Group newGroup = new Group(name, latitude, longitude, radius);
			newGroup = groupRepository.save(newGroup);
			
			if(newGroup.getId() == null)
				return 0;
			result = newGroup.getId();
			tx.success();
				
        	}
		
		finally{
			tx.close();
		}
		return result;
    }
    
    // Invite users in a group identified by id. List of users is sent as a list of strings of email in the body. 
    // Returns null if can't find user, else returns the list of users email successfully invited. 
    @RequestMapping(method = RequestMethod.POST,value="/group/{groupId}/invite", consumes = "application/json")
    public List<String> inviteUserToGroup(@PathVariable Long groupId, @RequestBody List<String> emails){
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
    	
    	List<String> invitedUsers = new LinkedList<String>();
    	
        Transaction tx = graphDatabaseService.beginTx();
		try{
			Group groupFromNeo = groupRepository.findById(groupId);
			if(groupFromNeo == null)
				return null;
			
			for(String email: emails){
				User userToAdd = userRepository.findByEmail(email);
				if(userToAdd != null) {
					invitedUsers.add(userToAdd.getEmail());
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
