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

    @Test
    public void should_be_construct_with_builder(){
        User user = new User(1L);
        user.setName("Pepe Lotudo");
        Comment comment = Comment.newComment(user,"_comment_").build();

        assertSame(user,comment.getCommenter());
        assertEquals("_comment_",comment.getComment());
    }
}
