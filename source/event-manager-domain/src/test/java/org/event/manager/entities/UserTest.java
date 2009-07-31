/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.event.manager.entities;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.event.manager.TestUtils;
import org.junit.Test;

/**
 *
 * @author tuosto
 */
public class UserTest {

	private static final Long ID = 10L;
	private static final Long INVALID_ID = -10L;
	
	@Test
	public void create_user_with_id(){
		@SuppressWarnings("deprecation")
		User user = new User(ID);
		assertNotNull(user);
		assertEquals(user.getId(), ID);
		assertNotNull(user.toString());
	}
	
	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)
	public void create_invalid_user_with_id(){
		new User(INVALID_ID);
		
	}
    
    @Test
    public void create_valid_user_with_builder(){
    	String username = "pepa";
    	String mail = "xxx@xxx.com";
    	String password = "1234aA";
        User user = User.newUser(username,mail,password).build();
        assertNotNull(user);
        assertEquals(user.getName(),username);
        assertEquals(user.getEmail(),mail);
        assertEquals(user.getPassword(),password);
    }

    @Test(expected=IllegalArgumentException.class)
    public void create_invalid_user_with_builder(){
    	String username = "pipo";
    	String mail = "xxxxxx.com";
    	String password = "1234aA";
        User.newUser(username,mail,password).build();
    }
    
	@Test
    public void create_user_with_group(){
		@SuppressWarnings("deprecation")
    	Group group = new Group();
    	group.setName("Event Owners");
    	
    	User user = User.newUser("nombre", "valid@email.co", "1234aA")
    	                .inGroup(group)
    	                .build();
    	assertNotNull(user);
    	Group retrieved = user.getGroups().iterator().next();
    	assertSame(retrieved, group);
    }
    
    
	@Test
    public void create_user_with_contacts(){
		@SuppressWarnings("deprecation")
    	User tmpUser = new User(ID);
    	
    	User user = User.newUser("nombre", "valid@email.co", "1234aA")
    	                .withContacts(tmpUser)
    	                .build();
    	assertNotNull(user);
    	User retrieved = user.getContacts().iterator().next();
    	assertSame(retrieved, tmpUser);
    }
	
	@Test
	@SuppressWarnings("deprecation")
	public void user_equals(){
		User first = new User(ID);
		User second = new User(ID);
		User third = new User(ID);
		Object object = new Object();
		TestUtils.equalsTest(first, second, third, object);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void user_equals_null_id(){
		User first = new User();
		User second = new User();
		assertFalse(first.equals(second));
	}
		
	
	@Test
	@SuppressWarnings("deprecation")
	public void user_hashCode(){
		User first = new User(ID);
		User second = new User(ID);
		User different = new User(500L);
		TestUtils.hashCodeTest(first, second, different);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void adding_groups(){
		User user = new User(ID);
		Group group = new Group();
    	group.setName("Event Owners");
    	assertEquals(user.getGroups().size(), 0);
    	user.add(group,group);    	
    	assertEquals(user.getGroups().size(), 1);
    	Group retrieved = user.getGroups().iterator().next();  	
    	assertSame(retrieved, group);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void adding_groups_iterables(){
		User user = new User(ID);
		Group group = new Group();
    	group.setName("Event Owners");
    	assertEquals(user.getGroups().size(), 0);
    	user.add(Arrays.asList(group,group));    	
    	assertEquals(user.getGroups().size(), 1);
    	Group retrieved = user.getGroups().iterator().next();  	
    	assertSame(retrieved, group);
	}	
	
	@Test
	@SuppressWarnings("deprecation")
	public void adding_contacts(){
		User user = new User(ID);
		User contact = new User(ID);
    	contact.setName("Developer");
    	assertEquals(user.getContacts().size(), 0);
    	user.addContact(contact,contact);    	
    	assertEquals(user.getContacts().size(), 1);
    	User retrieved = user.getContacts().iterator().next();  	
    	assertSame(retrieved, contact);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void adding_contacts_iterables(){
		User user = new User(ID);
		User contact = new User(ID);
    	contact.setName("Developer");
    	assertEquals(user.getContacts().size(), 0);
    	List<User> users = Arrays.asList(contact,contact); 
    	user.addContact(users);    	
    	assertEquals(user.getContacts().size(), 1);
    	User retrieved = user.getContacts().iterator().next();  	
    	assertSame(retrieved, contact);
	}	
	
	
	@Test @SuppressWarnings("deprecation")
	public void should_add_pending_invitation(){
		Event event = new Event(ID);
		Invitation invitation = new Invitation(event);
		User user = new User(ID);
		user.invite(invitation);
		assertTrue(user.getPendingResponeInvitations().contains(invitation));
	}
	
	@Test @SuppressWarnings("deprecation")
	public void should_record_response_in_invitation(){
		Event event = new Event(ID);
		Invitation invitation = new Invitation(event);
		User user = new User(ID);
		invitation.invite(user);
		assertTrue(invitation.denied().contains(user));
		assertFalse(invitation.accepted().contains(user));
		user.respondTo(invitation).accept();
		assertTrue(invitation.accepted().contains(user));
	}
}
