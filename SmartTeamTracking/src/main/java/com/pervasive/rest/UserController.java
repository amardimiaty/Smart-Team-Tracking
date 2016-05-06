package com.pervasive.rest;
import com.pervasive.model.Beacon;
import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.BeaconRepository;
import com.pervasive.repository.UserRepository;

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
    
    @RequestMapping("/user/")
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
    
    @RequestMapping(method = RequestMethod.POST,value="/User/{email}/{beaconIdentifier}")
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
    
  
    
    	
    
    
}
