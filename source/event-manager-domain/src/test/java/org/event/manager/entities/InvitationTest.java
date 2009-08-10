package org.event.manager.entities;

import static org.junit.Assert.*;

import java.util.Set;

import org.event.manager.TestUtils;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class InvitationTest {

	
	
	@Test
	public void invitation_hashCode(){
		Event event = new Event(10L);
		Event _event = new Event(20L);
		Invitation first = new Invitation(event);
		Invitation second = new Invitation(event);
		Invitation different = new Invitation(_event);
		TestUtils.hashCodeTest(first, second, different);
		Invitation nullID = new Invitation();
		assertEquals(31,nullID.hashCode());
	}
	
	@Test
	public void invitation_equals(){
		Event event = new Event(10L);
		Invitation first = new Invitation(event);
		Invitation second = new Invitation(event);
		Invitation third = new Invitation(event);
		Object object = new Object();
		TestUtils.equalsTest(first, second, third, object);
	}
	
	@Test
	public void should_keep_track_of_users_accepted(){
		Set<User> users = getUsers();
		Invitation invitation = new Invitation();
		invitation.setAccepted(users);
		assertCollections(invitation.getAccepted(),users);
	}
	
	@Test
	public void should_keep_track_of_users_denied(){
		Set<User> users = getUsers();
		Invitation invitation = new Invitation();
		invitation.setDenied(users);
		assertCollections(invitation.getDenied(),users);
	}
	
	@Test
	public void should_keep_track_of_users_maybe(){
		Set<User> users = getUsers();
		Invitation invitation = new Invitation();
		invitation.setMaybes(users);
		assertCollections(invitation.getMaybes(),users);
	}
	@SuppressWarnings("deprecation")
	private Set<User> getUsers(){
		User one = new User(10L);
		User two = new User(11L);
		User oneone = new User(10L);
		return ImmutableSet.of(one,two,oneone);
	}
	private void assertCollections( Set<User> expected,Set<User> actual) {
		assertEquals(expected.size(),actual.size());
		for(User u : expected){
			assertTrue(actual.contains(u));
		}
	}
	
}
