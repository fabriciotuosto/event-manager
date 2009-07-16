/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.event.manager.entities;

import java.util.Set;

import org.event.manager.test.TestGroup;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Sets;

import static org.testng.Assert.*;

/**
 *
 * @author tuosto
 */
@Test(groups={TestGroup.UNIT})
public class UserTest {

	private static final Long ID = 10L;
	
	@Test()
	public void create_user_with_id(){
		@SuppressWarnings("deprecation")
		User user = new User(ID);
		assertNotNull(user);
		assertEquals(user.getId(), ID);
		assertNotNull(user.toString());
	}
	
	@Test(expectedExceptions={IllegalArgumentException.class})
	public void create_invalid_user_with_id(){
		@SuppressWarnings({ "deprecation", "unused" })
		User user = new User(null);
	}
	
    @Test(dataProvider="valid_user_creation")
    public void create_valid_user_with_builder(String username,String mail,String password){
        User user = User.createUser(username,mail,password).build();
        assertNotNull(user);
        assertEquals(user.getName(),username);
        assertEquals(user.getEmail(),mail);
        assertEquals(user.getPassword(),password);
    }

    @Test(dataProvider="invalid_user_creation",expectedExceptions={IllegalArgumentException.class})
    public void create_invalid_user_with_builder(String username,String mail,String password){
        User.createUser(username,mail,password).build();
    }
    
    @DataProvider(name="valid_user_creation")
    public Object[][] user_creation_data(){
        return new Object[][]{
            new Object[] {"pepa","xxx@xxx.com","1234aA"}
        };
    }

    @DataProvider(name="invalid_user_creation")
    public Object[][] user_creation_data_invalid(){
        return new Object[][]{
            new Object[] {"pipo","invalidemail","1234aA"},
            new Object[] {"pipo","invalidemail",null},
            new Object[] {"pipo",null,"1234aA"},
            new Object[] {null,"invalidemail","1234aA"}
        };
    }
    
    @Test
    public void create_user_with_group(){
    	Group group = new Group();
    	group.setName("Event Owners");
    	
    	User user = User.createUser("nombre", "valid@email.co", "1234aA")
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
    	
    	User user = User.createUser("nombre", "valid@email.co", "1234aA")
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
		assertTrue(first.equals(second));
		assertTrue(second.equals(third));
		assertTrue(first.equals(third));
		assertTrue(first.equals(first));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void adding_groups(){
		User user = new User(ID);
		Group group = new Group();
    	group.setName("Event Owners");
    	assertEquals(user.getGroups().size(), 0);
    	user.addGroup(group,group);    	
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
	public void setter_getters(){
		User user = new User(ID);
		Long id = 1L;
		String name = "Kaoru";
		String email = "kaoru@pepe.net";
		String password = "12345";
		Set<Group> groups = Sets.newHashSet();
		Set<User> contacts = Sets.newHashSet();
		
		user.setId(id);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setGroups(groups);
		user.setContacts(contacts);
		
		assertSame(user.getId(), id);
		assertSame(user.getEmail(), email);
		assertSame(user.getPassword(), password);
		assertSame(user.getName(), name);
		assertSame(user.getGroups(), groups);
		assertSame(user.getContacts(), contacts);
	}
}
