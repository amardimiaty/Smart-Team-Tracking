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
		
}

