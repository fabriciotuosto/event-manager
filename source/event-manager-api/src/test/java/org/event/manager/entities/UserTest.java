/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.event.manager.entities;

import org.event.manager.test.TestGroup;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 * @author tuosto
 */
@Test(groups={TestGroup.UNIT})
public class UserTest {

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
}
