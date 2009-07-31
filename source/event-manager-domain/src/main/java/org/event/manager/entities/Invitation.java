package org.event.manager.entities;

import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;

@Entity
@XmlRootElement
public class Invitation {
	
	private Map<User,Response> responses;
	private SetMultimap< Response, User> _responses;
	private Event event;
	
	@Deprecated
	public Invitation() {
		responses = Maps.newHashMap();
		_responses = LinkedHashMultimap.create();
	}
	
	
	
	public Invitation(Event event) {
		this();
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}



	public void setEvent(Event event) {
		this.event = event;
	}



	public Invitation invite(User user) {
		user.invite(this);
		responses.put(user,Response.NO);
		_responses.put(Response.NO, user);
        return this;
	}


	public Set<User> accepted() {
		return getByResponse(Response.YES);
	}
	
	public Set<User> denied() {
		return getByResponse(Response.NO);
	}

	private Set<User> getByResponse(Response response){
		return _responses.get(response);
	}
	
	
	public void respond(User user, Response response) {
		_responses.values().remove(user);
		_responses.put(response, user);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invitation other = (Invitation) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		return true;
	}

}
