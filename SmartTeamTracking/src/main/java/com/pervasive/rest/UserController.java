package com.pervasive.rest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
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

import com.pervasive.model.Beacon;
import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.BeaconRepository;
import com.pervasive.repository.GroupRepository;
import com.pervasive.repository.UserRepository;
import com.pervasive.util.FacebookUtils;
import com.pervasive.util.RestUtils;

@RestController
public class UserController {

	@Autowired
	private ApplicationContext context;
	
	static Logger log = Logger.getLogger(UserController.class.getSimpleName());

    @RequestMapping("/user")
    public User authOrSignupUser(@RequestParam(value="token", defaultValue="null") String token,
    			                 @RequestParam(value="facebookId", defaultValue="null") String facebookId,
    						     @RequestParam(value="name", defaultValue="null") String name,
						         @RequestParam(value="surname", defaultValue="null") String surname,
						         @RequestParam(value="email", defaultValue="null") String email){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);    	
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        FacebookUtils facebookUtils = (FacebookUtils) context.getBean(FacebookUtils.class);
        
        User userFromNeo;
        User result;
        
    	//Check if this user already signed up 
        Transaction tx = graphDatabaseService.beginTx();
		try{
			userFromNeo = userRepository.findByFacebookId(facebookId);
			tx.success();
		}
		finally{
			tx.close();
		}
		
		if( userFromNeo == null){
			 result = facebookUtils.signupUser(token, name, surname, email);
			log.info("Called /user resource. Performed signup, returning user: "+result.toString());
			 return result;
		}
		else{
			result = facebookUtils.authUser(userFromNeo, token);
			log.info("Called /user resource. Performed login, returning user: "+result.toString());
			return result;
		}
    }
    
    
    //Returns true if correctly executed, if can't find either group or beacon returns false 
    @RequestMapping(method = RequestMethod.POST,value="/user/{userId}/beacon", consumes = "application/json")
    public boolean addInRange(@PathVariable Long userId, @RequestBody Beacon beacon){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	BeaconRepository beaconRepository = (BeaconRepository) context.getBean(BeaconRepository.class);
    	
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findById(userId);
			Beacon beaconFromNeo = beaconRepository.findByMajorMinor(beacon.getMajor(),beacon.getMinor());
			
			if(userFromNeo == null){
				tx.success();
				tx.close();
				log.info("Called /user/"+userId+"/beacon resource. Can't find user, returning false");
				return false;
			}
			
			if(beacon.getMajor() == null || beacon.getMinor() == null){ // Major and minor are null beacuse users wants to set beacon to null
				userFromNeo.setBeacon(null);
				userRepository.save(userFromNeo);
				tx.success();
				tx.close();
				log.info("Called /user/"+userId+"/beacon resource. Setting user beacon to null, returning true");
				return true;
			}
			
			if(beaconFromNeo == null){  //Beacon is different from null but I can't find it 
				tx.success();
				tx.close();
				log.info("Called /user/"+userId+"/beacon resource. Beacon received: "+beacon.toString()+" isn't registered, returning false");
				return false;
			}
			
			userFromNeo.setBeacon(beaconFromNeo);
			log.info("Called /user/"+userId+"/beacon resource. Setting user beacon to "+beaconFromNeo.toString()+" , returning true");
			userRepository.save(userFromNeo);
			tx.success();
        }
		finally{
			tx.close();
		}
		return true;
    }
    
  
    @RequestMapping("/user/{userId}/groups")
    public List<Group> getGroupsOfUsers(@PathVariable Long userId){
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        List<Group> groupList = new LinkedList<>();

    	Transaction tx = graphDatabaseService.beginTx();
    	try{
			Iterable<Group> iterableGroup = groupRepository.getGroupsforUser(userId);
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
		log.info("Called /user/"+userId+"/groups resource. Returning "+groupList.toString());
		return RestUtils.clearTokens(groupList);
    }
  

    @RequestMapping("/user/{userId}/pending")
    public List<Group> getPendingGroupsOfUsers(@PathVariable Long userId){
    	
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        List<Group> groupList = new LinkedList<>();

    	Transaction tx = graphDatabaseService.beginTx();
    	try{
			Iterable<Group> iterableGroup = groupRepository.getPendingGroupsForUser(userId);
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
		log.info("Called /user/"+userId+"/pending resource. Returning "+groupList.toString());
		return RestUtils.clearTokens(groupList);
    }
    
    
    @RequestMapping(method = RequestMethod.POST,value="/user",consumes = "application/json")
    public boolean updateUserGPSCoordinates(@RequestBody User user ){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        Transaction tx = graphDatabaseService.beginTx();        
		try{
			User userFromNeo = userRepository.findById(user.getId());
			if(userFromNeo == null){
				tx.success();
				tx.close();
				log.info("Called /user/"+user.getId()+" resource. Returning false");
				return false;
			}
			if( user.getLatGPS() != null && user.getLonGPS()!=null){
				userFromNeo.setLatGPS(user.getLatGPS());
				userFromNeo.setLonGPS(user.getLonGPS());
				userFromNeo.setCurrentGPS(true);
				log.info("Called /user/"+user.getId()+" resource. Returning true, setting latitude to "+user.getLatGPS()+" and longitude to "+user.getLonGPS());
			}
			else {
				userFromNeo.setCurrentGPS(false);
				log.info("Called /user/"+user.getId()+" resource. Returning true, setting isCurrentGPS to false");
			}
			userRepository.save(userFromNeo);
			
			tx.success();
        	}
		
		finally{
			tx.close();
		}
		return true;
    }
    
    
    @RequestMapping(method = RequestMethod.POST,value="/user/{userId}/{groupId}/accept")
    public Group addContains(@PathVariable Long userId, @PathVariable Long groupId){
    	
    	Group groupFromNeo;
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findById(userId);
			groupFromNeo = groupRepository.findById(groupId);
			if( userFromNeo == null || groupFromNeo == null){
				tx.success();
				tx.close();
				log.info("Called /user/"+userId+"/"+groupId+"/accept resource. Can't find neither user nor group, returning null");
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
		log.info("Called /user/"+userId+"/"+groupId+"/accept resource. Returning "+groupFromNeo.toString());
		
		// Set to null tokens of all users in the group before returning
		return RestUtils.clearTokens(groupFromNeo);
    }
    
    
    @RequestMapping(method = RequestMethod.POST,value="/user/{userId}/{groupId}/refuse")
    public boolean removePending(@PathVariable Long userId, @PathVariable Long groupId){
    	
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findById(userId);
			Group groupFromNeo = groupRepository.findById(groupId);
		    if(userFromNeo == null || groupFromNeo == null){
		    	tx.success();
		    	tx.close();
				log.info("Called /user/"+userId+"/"+groupId+"/refuse resource. Can't find neither user nor group, returning false");
		    	return false;
		    	
		    }
	
			groupFromNeo.removeUserPending(userFromNeo);
		    groupRepository.save(groupFromNeo);
		    tx.success();
        }
		finally{
			tx.close();
		}
		log.info("Called POST /user/"+userId+"/"+groupId+"/refuse resource. Returning true, removed pending.");

		return true;
    }
    
    @RequestMapping(method = RequestMethod.DELETE,value="/user/{userId}/{groupId}")
    public boolean removeUserFromGroup(@PathVariable Long userId, @PathVariable Long groupId){
    	UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
    	GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findById(userId);
			Group groupFromNeo = groupRepository.findById(groupId);
		    if(userFromNeo == null || groupFromNeo == null){
		    	tx.success();
		    	tx.close();
				log.info("Called DELETE /user/"+userId+"/"+groupId+". Can't find neither user nor group, returning false");
		    	return false;
		    }
	
			groupFromNeo.removeUser(userFromNeo);
		    groupRepository.save(groupFromNeo);

			//If group is empty and has no pending users, delete it from database. 
			if(groupFromNeo.getPending().size() == 0 && groupFromNeo.getContains().size() == 0){
				groupRepository.delete(groupFromNeo);
				log.info("Called DELETE /user/"+userId+"/"+groupId+" resource. Returning true, deleted user from group. Group was also deleted");
			}
			else log.info("Called DELETE /user/"+userId+"/"+groupId+" resource. Returning true, deleted user from group.");
		    tx.success();
        }
		finally{
			tx.close();
		}
		return true;
    }
    
    
   
  //Returns null if can't find User. This is a demo rest call, to not be used in production. 
 
  	/*@RequestMapping("/user")
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
      }*/ 
   
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
