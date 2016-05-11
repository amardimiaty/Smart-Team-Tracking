package com.pervasive;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.io.fs.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pervasive.model.Beacon;
import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.BeaconRepository;
import com.pervasive.repository.GroupRepository;
import com.pervasive.repository.UserRepository;
import com.pervasive.util.FacebookAuthData;

@SpringBootApplication
public class SmartTeamTrackingApplication {
	
	static ApplicationContext context;
	

	public static void main(String[] args) {
		
		try {
			FileUtils.deleteRecursively(new File("embeddedNeo4j.db"));
		} catch (IOException e) {
			System.out.println("Can't delete database file");
			e.printStackTrace();
		}
		
		context = SpringApplication.run(SmartTeamTrackingApplication.class, args);
		GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
		UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
		BeaconRepository beaconRepository = (BeaconRepository) context.getBean(BeaconRepository.class);
		GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
		testUser(graphDatabaseService, userRepository, beaconRepository,groupRepository);
	}
	
	
	public static void testUser(GraphDatabaseService graphDatabaseService, UserRepository userRepository,BeaconRepository beaconRepository,GroupRepository groupRepository){
	
		User stefano = new User("Stefano","Conoci","stefano.conoci@gmail.com",null);
		stefano.setLatGPS(20.0);
		stefano.setLonGPS(44.2);
		
		User davide = new User("Davide","Meacci","davide.meacci@gmail.com", null);
		
		User angelo = new User("Angelo","Meacci","angelo.meacci@gmail.com", null);
		User chicco = new User("Chicco","qwerty","chicco@gmail.com", null);
		Beacon b1 = new Beacon(56785l, "B2", 41.222d, 45.23d);
		Beacon a1 = new Beacon(56786l, "A1", 31.222d, 45.23d);
		Beacon a2 = new Beacon(56787l, "A2", 32.222d, 45.23d);
		Beacon a3 = new Beacon(56788l, "A3", 33.222d, 45.23d);
		Beacon a4 = new Beacon(56789l, "A4", 34.222d, 45.23d);
		
		
		Group froganGroup = new Group("Vegan Group",40.2,45.2,2);
		froganGroup.addUser(stefano);
		froganGroup.addUser(davide);
		
		stefano.setBeacon(b1);
		davide.setBeacon(b1);
		
		RestTemplate restTemplate = new RestTemplate();
		String authData = restTemplate.getForObject("https://graph.facebook.com/debug_token?input_token=EApUniJ4a9KcBAIdeyWUShpEvPejfhKJ5kAOhnQrm0sKfwHeY4iICyibaVhGqNMuCt94rSoFj2E4lO7TdXVokPcnugqzqoqZBt4yZBwgoQ9mKqBb8D2ZApntZAPDIS7xQuF67jNZCHZBjTyZA6HMWPmhFSAVbgcTExLCBpmZCIBYWTzj1vaH21hR3&access_token=1450842605155495%7CMFOOGXIcnTkmSGu1uXWGG6oeH_M", String.class);
	
		try {
			
		    ObjectMapper objectMapper = new ObjectMapper();
			JsonNode json = objectMapper.readTree(authData);
			JsonNode jsonData = json.get("data");
			FacebookAuthData facebookAuthData =objectMapper.treeToValue(jsonData, FacebookAuthData.class);
			System.out.println(facebookAuthData);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Transaction tx = graphDatabaseService.beginTx();
		try{
			System.out.println("Saving users");
			userRepository.save(stefano);
			userRepository.save(davide);
			userRepository.save(angelo);
			userRepository.save(chicco);
			
			System.out.println("Saving beacon");
			beaconRepository.save(b1);
			beaconRepository.save(a1);
			beaconRepository.save(a2);
			beaconRepository.save(a3);
			beaconRepository.save(a4);
			
			groupRepository.save(froganGroup);
			
		    System.out.println("Retrieving user");
			User stefanoFromNeo = userRepository.findByName("Stefano");
			System.out.println(stefanoFromNeo.toString());
			User davideFromNeo = userRepository.findByName("Davide");
			System.out.println(davideFromNeo.toString());
			
			System.out.println("Retrieving beacon");
			Beacon beaconFromNeo = beaconRepository.findByName("B2");
			System.out.println(beaconFromNeo.toString());
			
			System.out.println("Retrieving group");
			Group groupFromNeo = groupRepository.findByName("Vegan Group");
			System.out.println(groupFromNeo);
			
			System.out.println("Result from query getGroupsForUser");
			Iterable<Group> iterableFromQuery = groupRepository.getGroupsforUser(Long.valueOf(0));
			Iterator<Group> it  = iterableFromQuery.iterator();
			while( it.hasNext()){
				Group currentGroup = it.next();
				System.out.println(currentGroup);
			}			
			
			User userFromQuery = userRepository.findByEmail("stefano.conoci@gmail.com");
			System.out.println("Testing findByEmail");
			System.out.println(userFromQuery);
			
			System.out.println("Testing findByID");
			userFromQuery = userRepository.findById(0l);
			System.out.println(userFromQuery);
			
			tx.success();
		}
		finally{
			tx.close();
		}
	}
}
