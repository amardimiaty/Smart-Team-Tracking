package com.pervasive;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.io.fs.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.core.GraphDatabase;

import com.pervasive.model.Beacon;
import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.BeaconRepository;
import com.pervasive.repository.GroupRepository;
import com.pervasive.repository.UserRepository;

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
	
		User stefano = new User(null,"Stefano","Conoci","stefano.conoci@gmail.com","badpw");
		stefano.setLatGPS(20.0);
		stefano.setLonGPS(44.2);
		
		User davide = new User(null ,"Davide","Meacci","davide.meacci@gmail.com","reallybadpw");
		Beacon b1 = new Beacon(null, "B2", 41.222d, 45.23d);
		
		Group froganGroup = new Group(null, "Vegan Group",40.2,45.2,2);
		froganGroup.addUser(stefano);
		froganGroup.addUser(davide);
		
		stefano.setBeacon(b1);
		davide.setBeacon(b1);
		
		
		Transaction tx = graphDatabaseService.beginTx();
		try{
			System.out.println("Saving users");
			userRepository.save(stefano);
			userRepository.save(davide);
			
			System.out.println("Saving beacon");
			beaconRepository.save(b1);
			
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
			
			tx.success();
		}
		finally{
			tx.close();
		}
		
		graphDatabaseService.shutdown();
	}
}
