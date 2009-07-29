/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.event.manager.entities;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author tuosto
 */
public class CommentTest {

	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)	
	public void should_not_create_user_id_negative(){
		new Comment(-10L);
		fail();
	}
	
	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)	
	public void should_not_create_user_id_null(){
		new Comment(null);
		fail();
	}	
	
	@Test
	@SuppressWarnings("deprecation")
	public void should_create_with_id(){
		Comment comment = new Comment(10L);
		assertEquals(10L, comment.getId().longValue());
	}
	
	@Test
	@SuppressWarnings("deprecation")
    public void should_be_construct_with_builder(){
        User user = new User(1L);
        user.setName("Pepe Lotudo");
        Comment comment = Comment.newComment(user,"_comment_").build();

        assertSame(user,comment.getCommenter());
        assertEquals("_comment_",comment.getComment());
        assertNotNull(new Comment());
    }
}
