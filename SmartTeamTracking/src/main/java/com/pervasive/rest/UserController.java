package com.pervasive.rest;
import com.pervasive.model.User;
import com.pervasive.repository.UserRepository;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
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
    	
    
    
}
