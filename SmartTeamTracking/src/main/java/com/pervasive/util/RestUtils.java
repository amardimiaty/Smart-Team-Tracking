package com.pervasive.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.pervasive.model.Group;
import com.pervasive.model.User;

public class RestUtils {


	// Clears token fields of a list of Users and return it. Used before sending data with Rest 
	public static Set<User> clearTokens(Set<User> users){
		if(users == null){
			return null;
		}
		
		for (User user: users){
			user.setAuthToken(null);
		}
		return users; 
	}
	
	// Clears token fields of Users in a Group and return it. Used before sending data with Rest 
	public static Group clearTokens(Group group){
		group.setContains(clearTokens(group.getContains()));
		group.setPending(clearTokens(group.getPending()));
		return group; 
	}
	
	// Clears token fields of Users in a List of Group and return it. Used before sending data with Rest 
	public static List<Group> clearTokens(List<Group> groupList){
		List<Group> result = new LinkedList<Group>();
		for(Group group : groupList){
			result.add(clearTokens(group));
		}
		return result;
	}
	
	
	// Calculate distance between two points in latitude and longitude taking into account height difference. 
	// Based on Haversine method. Returns distance in Meters
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    Double latDistance = Math.toRadians(lat2 - lat1);
	    Double lonDistance = Math.toRadians(lon2 - lon1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
		
}

