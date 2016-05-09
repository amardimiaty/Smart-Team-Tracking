package com.pervasive.util;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.context.ApplicationContext;

import com.pervasive.model.User;
import com.pervasive.repository.UserRepository;

public class FacebookUtils {
	

	//Returns true if authenticated, else returns false. Creates user if doesn't exist and user parameter is null. 
	public static boolean authUser(ApplicationContext context, User user, String token){
		
		GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
		UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
		
		Transaction tx = graphDatabaseService.beginTx();
		try{
			User userFromNeo = userRepository.findByEmail(user.getEmail());
			if(userFromNeo == null){ // User with this mail doesn't exist!
				tx.success();
				tx.close();
				return false; 
			}
			
			if( !userFromNeo.getAuthToken().equals(token)){ 	// Must validate token 
				//Validate auth token
				boolean valid = true;
				
				if(valid){
					userFromNeo.setAuthToken(token);
					userRepository.save(userFromNeo);
				}
				else {  //Token sent wasn't valid
					tx.success();
					tx.close();
					return false;
				}	
			}
		}
		finally{
			tx.close();
		}
		return true;	
	}
	
	public static boolean signupUser(ApplicationContext context, String token, String name,
									 String surname, String email, String facebookId){
		
		GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
		UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
		
		Transaction tx = graphDatabaseService.beginTx();
		try{
			if(userRepository.findByEmail(email) != null){  //User already exists
				tx.success();
				tx.close();
				return false;
			}
			//Retrieve facebookId and check if emails match! In case it doesn't abort and return false
			User userToAdd = new User(name, surname, email, facebookId);
			userToAdd.setAuthToken(token);
			userRepository.save(userToAdd);
			tx.success();
		}finally {
			tx.close();
		}
		return true;
	}
	
}
