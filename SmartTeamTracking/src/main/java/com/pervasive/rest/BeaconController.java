package com.pervasive.rest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    
    @RequestMapping(method = RequestMethod.POST, value="/beacon")
    public String addBeacon(@RequestBody Beacon beacon){
    	
    	BeaconRepository beaconRepository = (BeaconRepository) context.getBean(BeaconRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        
        Beacon beaconFromNeo;

        
    	//Check if this beacon is already existing
        Transaction tx = graphDatabaseService.beginTx();
		try{
			beaconFromNeo = beaconRepository.findByMajorMinor(beacon.getMajor(), beacon.getMinor());
			tx.success();
		}
		finally{
			tx.close();
		}
		
		if( beaconFromNeo == null){
			
			 beaconRepository.save(beacon);
			log.info("Called /beacon POST resource. Added beacon, returning True");
			 return "True";
		}
		else{
			log.info("Called /beacon POST resource. Beacon was already existing, returning False");
			return "False";
		}
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value="/beacon/delete")
    public String addBeacon(@RequestBody String beaconID){
    	
    	BeaconRepository beaconRepository = (BeaconRepository) context.getBean(BeaconRepository.class);
        GraphDatabaseService graphDatabaseService = (GraphDatabaseService) context.getBean(GraphDatabaseService.class);
        
        
        Beacon beaconFromNeo;

        
    	//Check if this beacon is already existing
        Transaction tx = graphDatabaseService.beginTx();
		try{
			Long remove_id = Long.parseLong(beaconID);
			beaconFromNeo = beaconRepository.findById(remove_id);
			tx.success();
		}
		finally{
			tx.close();
		}
		
		if( beaconFromNeo != null){
			
			beaconRepository.delete(beaconFromNeo);
			log.info("Called /beacon/delete POST resource. Deleted beacon "+ beaconID +", returning True");
			 return "True";
		}
		else{
			log.info("Called /beacon/delete POST resource. Beacon "+ beaconID + " was not existing, returning False");
			return "False";
		}
    }
    
    
    
    

}
