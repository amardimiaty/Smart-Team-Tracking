package com.pervasive.rest;
import com.pervasive.model.Beacon;
import com.pervasive.repository.BeaconRepository;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeaconController {
	
	
	@Autowired
	private ApplicationContext context;
    
    @RequestMapping("/Beacon/")
    public List<Beacon> getBeacon() {
    	
    	BeaconRepository beaconRepository = (BeaconRepository) context.getBean(BeaconRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);

    	Transaction tx = graphDatabaseService.beginTx();
		try{
			
			Iterable<Beacon> beacons = beaconRepository.findAll();
			Iterator<Beacon> it = beacons.iterator();
			tx.success();
			List<Beacon> result = new LinkedList<Beacon>();
			
			while(it.hasNext()){
				result.add(it.next());
				
				}
			
			return result;
				
        	}
		
		finally{
			tx.close();
		}
    	
    }

}
