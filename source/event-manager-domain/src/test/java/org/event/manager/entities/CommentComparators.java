/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.event.manager.entities;

import com.google.common.collect.Ordering;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tuosto
 */
public class CommentComparators {

	private Comment first;
	private Comment second;
	private User user;
	
	@Before @SuppressWarnings("deprecation")
	public void initializeTest(){
		user = new User(10L);
		first = Comment.newComment(user, "Should be first test").build();
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		first.setWhen(yesterday);
		second = Comment.newComment(user, "Should be second comment").build();
		
	}
	
	@Test
    public void should_order_comment_by_date(){
        Ordering<Comment> commentComp = Comment.DATE_DESCENDING_COMPARATOR;
        assertSame(first, commentComp.max(first, second));
        assertSame(first, commentComp.max(second,first));
        assertSame(second,commentComp.min(second,first));
        assertSame(second,commentComp.min(first,second));
    }
	

	@Test
    public void should_order_comment_by_user_name(){
		first.getCommenter().setName("A");
		
		User user2 = User.newUser("B", "b@b.com", "1234aA").build();
		second.setCommenter(user2);
		
        Ordering<Comment> commentComp = Comment.USER_NAME_ASCENDING_COMPARATOR;
        assertSame(second, commentComp.max(first, second));
        assertSame(second, commentComp.max(second,first));
        assertSame(first,commentComp.min(second,first));
        assertSame(first,commentComp.min(first,second));
    }
}
