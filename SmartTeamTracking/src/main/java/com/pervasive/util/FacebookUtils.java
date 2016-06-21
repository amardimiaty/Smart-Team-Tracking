package com.pervasive.util;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pervasive.model.User;
import com.pervasive.repository.UserRepository;



public class FacebookUtils {
	
	@Autowired
    private ApplicationContext context;
	private ObjectMapper objectMapper;
	private RestTemplate restTemplate;
	private static final String appToken = "1579642739001091|H_IXG8bVndM7kohNdHHD6P8XO9o";
	
	
	@PostConstruct
	public void init() {
		this.objectMapper = new ObjectMapper();
		this.restTemplate = new RestTemplate();

	}

	//Calls Facebook API to verify the debug token. Returns facebookAuthData in case of validated token else returns null 
	public FacebookAuthData debugToken(String token) throws IOException{
		String authData = restTemplate.getForObject("https://graph.facebook.com/debug_token?input_token="+token+"&access_token="+ FacebookUtils.appToken, String.class);
		JsonNode jsonOuter = objectMapper.readTree(authData);
		JsonNode jsonData = jsonOuter.get("data");
		FacebookAuthData facebookAuthData = objectMapper.treeToValue(jsonData, FacebookAuthData.class);	
		
		if(facebookAuthData.getIs_valid().equals("true"))
			return facebookAuthData;
		else return null;
	}
	
	
	//Returns user if authenticated, else returns null. Creates user if doesn't exist and user parameter is null. 
	public User authUser(User user, String token){
		
		GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
		UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
		User userFromNeo;
	
		Transaction tx = graphDatabaseService.beginTx();
		try{
			userFromNeo = userRepository.findById(user.getId());
			if(userFromNeo == null){ // User with this id doesn't exist!
				tx.success();
				tx.close();
				return null; 
			}
			
			if( !userFromNeo.getAuthToken().equals(token)){ 	// Must validate token . In theory should check validation time 
				FacebookAuthData authData = debugToken(token);
				if(authData == null || !authData.getUser_id().equals(user.getFacebookId())){ //Token received not valid
					tx.success();
					tx.close();
					return null;
				}
				userFromNeo.setAuthToken(token);
				userFromNeo = userRepository.save(userFromNeo);
			}
		} catch (IOException e) {
			System.out.println("Couldn't recieve rest response or parse JSON.");
			tx.success();
			tx.close();
			return null;
		}
		finally{
			tx.close();
		}
		return userFromNeo;	
	}
	
	
	//Returns null if User with that facebookId already exists or if debugToken returns null;
	public User signupUser(String token, String name,
							  String surname, String email){
		
		GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
		UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
		FacebookAuthData authData;
		User userFromNeo;
		
		try {
			authData = debugToken(token);
		} catch (IOException e1) {
			System.out.println("Couldn't recieve rest response or parse JSON.");
			return null;
		}
		
		if(authData == null) // token isn't valid. User isn't authorized
			return null;
		
		Transaction tx = graphDatabaseService.beginTx();
		try{
			if(userRepository.findByFacebookId(authData.getUser_id()) != null){  //User already exists
				tx.success();
				tx.close();
				return null;
			}
			
			User userToAdd = new User(name, surname, email, authData.getUser_id(), false);
			userToAdd.setAuthToken(token);
			userFromNeo = userRepository.save(userToAdd);
			tx.success();
		} finally {
			tx.close();
		}
		return userFromNeo;
	}
	
}
