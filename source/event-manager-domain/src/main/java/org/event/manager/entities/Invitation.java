package org.event.manager.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.Validate;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

@Entity
@XmlRootElement
public class Invitation {

	private SetMultimap<Response, User> responses;
	private Event event;

	@Deprecated
	public Invitation() {
		responses = LinkedHashMultimap.create();
	}

	public Invitation(Event event) {
		this();
		Validate.notNull(event);
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
		responses.put(Response.NO, user);
		return this;
	}
	
	public Set<User> getMaybes() {
		return getByResponse(Response.MAYBE);
	}

	public void setMaybes(Set<User> users) {
		setByResponse(Response.MAYBE, users);
	}


	public void setAccepted(Set<User> users) {
		setByResponse(Response.YES, users);
	}

	public Set<User> getDenied() {
		return getByResponse(Response.NO);
	}

	public Set<User> getAccepted() {
		return getByResponse(Response.YES);
	}

	public void setDenied(Set<User> users) {
		setByResponse(Response.NO, users);
	}

	private void setByResponse(Response response, Set<User> users) {
		responses.putAll(response, users);
	}

	private Set<User> getByResponse(Response response) {
		return responses.get(response);
	}

	public void respond(User user, Response response) {
		responses.values().remove(user);
		responses.put(response, user);
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
