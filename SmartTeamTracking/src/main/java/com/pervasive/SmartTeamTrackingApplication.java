package com.pervasive;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.io.fs.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.pervasive.model.Beacon;
import com.pervasive.model.Group;
import com.pervasive.model.User;
import com.pervasive.repository.BeaconRepository;
import com.pervasive.repository.GroupRepository;
import com.pervasive.repository.UserRepository;

@SpringBootApplication
public class SmartTeamTrackingApplication {
	
	static ApplicationContext context;
	static Logger log = Logger.getLogger(SmartTeamTrackingApplication.class.getSimpleName());
	

	public static void main(String[] args) {
		
		// Clear Database at server startup. Comment if not needed
		try {
			FileUtils.deleteRecursively(new File("embeddedNeo4j.db"));
		} catch (IOException e) {
			System.out.println("Can't delete database file");
			e.printStackTrace();
		}
		log.info("Cleared data in Neo4j Database");
	
		// Spring init 
		context = SpringApplication.run(SmartTeamTrackingApplication.class, args);
		log.info("Server started correctly");
		
		// Populate the DB with test data for debugging. Comment if not needed 
		// /*
		GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
		UserRepository userRepository = (UserRepository) context.getBean(UserRepository.class);
		BeaconRepository beaconRepository = (BeaconRepository) context.getBean(BeaconRepository.class);
		GroupRepository groupRepository = (GroupRepository) context.getBean(GroupRepository.class);
		testUser(graphDatabaseService, userRepository, beaconRepository,groupRepository);
		// */
		
	}
	
	
	// Test method to populate the database, used for debugging the server locally 
	public static void testUser(GraphDatabaseService graphDatabaseService, UserRepository userRepository,BeaconRepository beaconRepository,GroupRepository groupRepository){
	
		User stefano = new User("Stefano","Conoci","stefano.conoci@gmail.com",null);
		stefano.setLatGPS(20.0);
		stefano.setLonGPS(44.2);
		stefano.setAuthToken("AuthToken123");
		
		User davide = new User("Davide","Meacci","davide.meacci@gmail.com", null);
		
		User angelo = new User("Angelo","Meacci","angelo.meacci@gmail.com", null);
		User chicco = new User("Chicco","qwerty","chicco@gmail.com", null);
		
		Beacon b2 = new Beacon(30781,5475, "DIAG-A4", 41.890715, 12.503897);   //GREEN BEACON -> A4
		Beacon a1 = new Beacon(23082,20505, "DIAG-Study room", 41.890594, 12.503956); //PURPLE BEACON -> Student's Room
		Beacon a2 = new Beacon(49546,41588, "DIAG-A1", 41.890867, 12.503806);   //BLUE BEACON -> A1 
		Beacon a3 = new Beacon(777,4, "A3", 33.222d, 45.23d);
		Beacon a4 = new Beacon(777,5, "A4", 34.222d, 45.23d);
		
		
		Group froganGroup = new Group("Vegan Group",40.2,45.2,2);
		
		stefano.setBeacon(b2);
		davide.setBeacon(b2);
		
		
		Transaction tx = graphDatabaseService.beginTx();
		try{
			System.out.println("Saving users");
			stefano = userRepository.save(stefano);
			davide = userRepository.save(davide);
			userRepository.save(angelo);
			userRepository.save(chicco);
			
			System.out.println("Saving beacon");
			beaconRepository.save(b2);
			beaconRepository.save(a1);
			beaconRepository.save(a2);
			beaconRepository.save(a3);
			beaconRepository.save(a4);
			
			froganGroup.addUser(stefano);
			froganGroup.addUser(davide);
			
			groupRepository.save(froganGroup);
			
		    System.out.println("Retrieving user");
			User stefanoFromNeo = userRepository.findByName("Stefano");
			System.out.println(stefanoFromNeo.toString());
			User davideFromNeo = userRepository.findByName("Davide");
			System.out.println(davideFromNeo.toString());
			
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
	
	
	
	
	/*
	 * OLD METHOD TO TEST LOCAL USERS
	 * 
	 public static void testUser(GraphDatabaseService graphDatabaseService, UserRepository userRepository,BeaconRepository beaconRepository,GroupRepository groupRepository){
	
		User stefano = new User("Stefano","Conoci","stefano.conoci@gmail.com",null);
		stefano.setLatGPS(20.0);
		stefano.setLonGPS(44.2);
		
		User davide = new User("Davide","Meacci","davide.meacci@gmail.com", null);
		
		User angelo = new User("Angelo","Meacci","angelo.meacci@gmail.com", null);
		User chicco = new User("Chicco","qwerty","chicco@gmail.com", null);
		User a = new User("Francesco","Tacos","a@gmail.com", null);
		User b = new User("Giuseppe","Bellini","b@gmail.com", null);
		User c = new User("Carlo","Cardo","c@gmail.com", null);
		User d = new User("Andrea","Dattimo","d@gmail.com", null);
		User e = new User("Giustino","Eletto","e@gmail.com", null);
		User f = new User("Saverio","Forte","f@gmail.com", null);
		User g = new User("Mattia","Ganede","go@gmail.com", null);
		User h = new User("Manuele","Hots","h@gmail.com", null);
		User i = new User("Giorgio","Ilerte","i@gmail.com", null);
		User l = new User("Maurizio","Lorde","l@gmail.com", null);
		User m = new User("Claudio","Magnus","m@gmail.com", null);
		User n = new User("Paolo","Neri","n@gmail.com", null);
		User o = new User("Tony","Olmi","o@gmail.com", null);
		User p = new User("Mary","Pertici","p@gmail.com", null);
		User q = new User("Ultron","Quercia","q@gmail.com", null);
		User r = new User("Magneto","Raven","r@gmail.com", null);
		User s = new User("Aragorn","Sarti","s@gmail.com", null);
		User t = new User("Thor","Terziani","t@gmail.com", null);
		User u = new User("Thanos","Ulres","u@gmail.com", null);
		
		Beacon b2 = new Beacon(771,1, "B2", 41.222d, 45.23d);
		Beacon a1 = new Beacon(772,2, "A1", 31.222d, 45.23d);
		Beacon a2 = new Beacon(773,3, "A2", 32.222d, 45.23d);
		Beacon a3 = new Beacon(774,4, "A3", 33.222d, 45.23d);
		Beacon a4 = new Beacon(775,5, "A4", 34.222d, 45.23d);
		Beacon b1 = new Beacon(776,1, "B1", 41.222d, 45.23d);
		Beacon a5 = new Beacon(777,1, "A5", 31.222d, 45.23d);
		Beacon a6 = new Beacon(778,3, "A6", 32.222d, 45.23d);
		Beacon a7 = new Beacon(779,4, "A7", 33.222d, 45.23d);
		
		
		
		Group froganGroup = new Group("Vegan Group",40.1,45.2,5);
		Group aa = new Group("Facebook Group",23.3,15.4,2);
		Group bb = new Group("Milky Way Group",14.5,67.6,4);
		Group cc = new Group("Students Group",21.6,54.5,3);
		Group dd = new Group("Soccer Group",13.4,71.3,7);
		Group ee = new Group("Swimming Group",42.2,58.1,9);
		Group ff = new Group("Poker Group",60.1,36.7,11);
		Group gg = new Group("Big Money Group",98.0,19.8,1);
		
		
		stefano.setBeacon(b2);
		davide.setBeacon(b2);
		a.setBeacon(a1);
		b.setBeacon(a1);
		c.setBeacon(a1);
		d.setBeacon(b1);
		e.setBeacon(b1);
		f.setBeacon(a2);
		g.setBeacon(a2);
		h.setBeacon(a3);
		i.setBeacon(a4);
		l.setBeacon(a4);
		m.setBeacon(a4);
		n.setBeacon(a4);
		o.setBeacon(a5);
		p.setBeacon(a5);
		q.setBeacon(a6);
		r.setBeacon(a6);
		s.setBeacon(a6);
		t.setBeacon(a7);
		u.setBeacon(a7);
		
		
		
		Transaction tx = graphDatabaseService.beginTx();
		try{
			System.out.println("Saving users");
			stefano = userRepository.save(stefano);
			davide = userRepository.save(davide);
			userRepository.save(a);
			userRepository.save(b);
			userRepository.save(c);
			userRepository.save(d);
			userRepository.save(e);
			userRepository.save(f);
			userRepository.save(g);
			userRepository.save(h);
			userRepository.save(i);
			userRepository.save(l);
			userRepository.save(m);
			userRepository.save(n);
			userRepository.save(o);
			userRepository.save(p);
			userRepository.save(q);
			userRepository.save(r);
			userRepository.save(s);
			userRepository.save(t);
			userRepository.save(u);


			
			
			System.out.println("Saving beacon");
			beaconRepository.save(b1);
			beaconRepository.save(b2);
			beaconRepository.save(a1);
			beaconRepository.save(a2);
			beaconRepository.save(a3);
			beaconRepository.save(a4);
			beaconRepository.save(a5);
			beaconRepository.save(a6);
			beaconRepository.save(a7);
			
			
			froganGroup.addUser(stefano);
			froganGroup.addUser(davide);
			aa.addUser(a);
			bb.addUser(b);
			cc.addUser(c);
			cc.addUser(d);
			dd.addUser(e);
			ee.addUser(f);
			ff.addUser(g);
			gg.addUser(h);
			gg.addUser(i);
			gg.addUser(l);
			aa.addUser(m);
			aa.addUser(n);
			bb.addUser(o);
			ee.addUser(p);
			ff.addUser(q);
			ff.addUser(s);
			ff.addUser(t);
			ff.addUser(u);
			
			
			groupRepository.save(froganGroup);
			groupRepository.save(aa);
			groupRepository.save(bb);
			groupRepository.save(cc);
			groupRepository.save(dd);
			groupRepository.save(ee);
			groupRepository.save(ff);
			groupRepository.save(gg);
			
			
			
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
			
			System.out.println("Testing findByMajorMinor");
			beaconFromNeo = beaconRepository.findByMajorMinor(777, 1);
			System.out.println(beaconFromNeo);
			
			tx.success();
		}
		finally{
			tx.close();
		}
	} 
	  
	  
	  
	  
	  
	 */
	
	
	
	
}
