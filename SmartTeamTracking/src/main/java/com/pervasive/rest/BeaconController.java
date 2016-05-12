package com.pervasive.rest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pervasive.model.Beacon;
import com.pervasive.repository.BeaconRepository;

@RestController
public class BeaconController {
	
	@Autowired
	private ApplicationContext context;
	
	static Logger log = Logger.getLogger(BeaconController.class.getSimpleName());
    
    @RequestMapping("/beacon")
    public List<Beacon> getBeacon() {
    	List<Beacon> result = new LinkedList<Beacon>();
    	BeaconRepository beaconRepository = (BeaconRepository) context.getBean(BeaconRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
    	Transaction tx = graphDatabaseService.beginTx();
		try{
			Iterable<Beacon> beacons = beaconRepository.findAll();
			Iterator<Beacon> it = beacons.iterator();
			tx.success();
			
			while(it.hasNext())
				result.add(it.next());				
        }
		
		finally{
			tx.close();
		}
    	log.info("Called /beacon resource. Returning "+result.toString());
		return result;	
    }

}
