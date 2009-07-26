package org.event.manager.entities;

import static org.junit.Assert.*;

import org.event.manager.TestUtils;
import org.junit.Test;

public class EventTest {

	private static final Long ID = 10L;
	
	@Test
	@SuppressWarnings("deprecation")
	public void create_event_with_id(){
		Event event = new Event(ID);
		assertNotNull(event);
		assertEquals(ID, event.getId());
	}
	
	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)
	public void should_not_create_user_id_null(){
		new Event(null);
		fail();
	}
	
	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)	
	public void should_not_create_user_id_negative(){
		new Event(-10L);
		fail();
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void event_equals(){
		Event first = new Event(ID);
		Event second = new Event(ID);
		Event third = new Event(ID);
		Object object = new Object();
		TestUtils.equalsTest(first, second, third, object,new Event());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void events_hashCode(){
		Event first = new Event(ID);
		Event second = new Event(ID);
		Event different = new Event(500L);	
		TestUtils.hashCodeTest(first, second, different);
	}

	@Test
	@SuppressWarnings("deprecation")
	public void invite_user(){
		User invitee = new User(ID);
		Event event = new Event(ID).invite(invitee);
		assertNotNull(event.getUsers());
		assertFalse(event.getUsers().isEmpty());
		assertSame(invitee, event.getUsers().iterator().next());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void should_be_in_specify_location(){
		Location loc = new Location(ID);
		Event event = new Event(ID).on(loc);		
		assertSame(loc, event.getLocation());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void create_with_builder(){
		Location loc = new Location(ID);
		Event event = Event.newEvent(loc).build();
		assertNotNull(event);
		assertSame(loc,event.getLocation());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void create_with_builder_null_location(){
		Event.newEvent(null).build();
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void create_full_with_builder(){
		Location loc = new Location(ID);
		User a = new User(ID);
		User b = new User(50L);
		Photo photo = null;
		Event event = Event.newEvent(loc)
						.invited(a,b)
						.with(photo)
						.build();		
		assertTrue(event.getUsers().size() == 2);
		assertFalse(event.getPhotos().isEmpty());
		assertSame(photo, event.getPhotos().iterator().next());
	}
}
