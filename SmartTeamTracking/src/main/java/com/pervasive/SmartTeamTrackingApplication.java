package com.pervasive;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.io.fs.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.core.GraphDatabase;

import com.pervasive.model.Beacon;
import com.pervasive.model.User;
import com.pervasive.repository.BeaconRepository;
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
		testUser(graphDatabaseService, userRepository, beaconRepository);
	}
	
	public static void testUser(GraphDatabaseService graphDatabaseService, UserRepository userRepository,BeaconRepository beaconRepository){
	
		User stefano = new User(1l,"Stefano","Conoci","stefano.conoci@gmail.com","badpw");
		stefano.setLatGPS(20.0);
		stefano.setLonGPS(40.2);
		
		//User davide = new User(new Long(2),"Davide","Meacci","davide.meacci@gmail.com","reallybadpw");
		//Beacon b1 = new Beacon(1, "B2", 41.222, 45.23);
		
		//stefano.setInRange(b1);
		//davide.setInRange(b1);
		
		Transaction tx = graphDatabaseService.beginTx();
		try{
			System.out.println("Creating random node");
			graphDatabaseService.createNode();
			
			//System.out.println("Saving users");
			//userRepository.save(stefano);
			//userRepository.save(davide);
			
			//System.out.println("Saving beacon");
			//beaconRepository.save(b1);
			
		    //System.out.println("Retrieving user");
			//User stefanoFromNeo = userRepository.findByName("Stefano");
			//System.out.println(stefanoFromNeo.toString());
			
			tx.success();
		}
		finally{
			tx.close();
		}
	}
}
