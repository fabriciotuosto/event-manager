package org.event.manager.entities;

import java.util.Map;

import com.google.common.collect.Maps;

public class Invitation {
	
	private Map<User,Response> responses;
	
	public Invitation() {
		responses = Maps.newHashMap();
	}
	
	public void invite(User user) {
		user.invite(this);
		responses.put(user,Response.NO);
	}

}
