package com.pervasive;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SmartTeamTrackingApplication {
	
	static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(SmartTeamTrackingApplication.class, args);
		GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
		System.out.println(graphDatabaseService.toString());
	}
}
