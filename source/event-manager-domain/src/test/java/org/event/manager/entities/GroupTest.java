package org.event.manager.entities;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class GroupTest {

	@Test
	@SuppressWarnings("deprecation")
	public void should_create_group_with_builder_varargs(){
		User first = new User(10L);
		User second = new User(11L);
		Group group = Group.newGroup("group_name")
						   .with(first,second)
						   .build();
		assertEquals("group_name",group.getName());
		assertEquals(2,group.getUsers().size());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void should_create_group_with_builder_iterables(){
		User first = new User(10L);
		User second = new User(11L);
		Group group = Group.newGroup("group_name")
						   .with(Arrays.asList(first,second))
						   .build();
		assertEquals("group_name",group.getName());
		assertEquals(2,group.getUsers().size());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void should_create_with_argument_id(){
		assertEquals(10L,new Group(10L).getId().longValue());
		assertNotNull(new Group());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void should_thrown_exception_invalid_id(){
		createWithID(-10L);
	}

	@Test(expected=IllegalArgumentException.class)
	public void should_thrown_exception_invalid_null_id(){
		createWithID(null);
	}
	
	@SuppressWarnings("deprecation")
	private void createWithID(Long id) {
		new Group(id);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void should_contains_added_user_vargarg(){
		Group group = new Group();
		User first = new User(10L);
		User second = new User(11L);
		assertNotNull(group);
		assertNotNull(group.getUsers());
		assertTrue(group.getUsers().isEmpty());
		group.add(first,second);
		assertEquals(2,group.getUsers().size());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void should_contains_added_user_iterables(){
		Group group = new Group();
		User first = new User(10L);
		User second = new User(11L);
		assertNotNull(group);
		assertNotNull(group.getUsers());
		assertTrue(group.getUsers().isEmpty());
		group.add(Arrays.asList(first,second));
		assertEquals(2,group.getUsers().size());
	}	
}
