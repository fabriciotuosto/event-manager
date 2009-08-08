/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.event.manager.entities;

import com.google.common.collect.Ordering;
import org.event.manager.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

/**
 * @author tuosto
 */
public class CommentComparators {

    private Comment first;
    private Comment second;

    @Before
    @SuppressWarnings("deprecation")
    public void initializeTest() {
        User user = new User(10L);
        first = Comment.newComment(user, "Should be first test").build();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        first.setWhen(yesterday);
        second = Comment.newComment(user, "Should be second comment").build();

    }

    @Test
    public void should_order_comment_by_date() {
        TestUtils.test_comparable(first, first, second, Comment.DATE_DESCENDING_COMPARATOR);
    }


    @Test
    public void should_order_comment_by_user_name() {
        first.getCommenter().setName("A");
        User user2 = User.newUser("B", "b@b.com", "1234aA").build();
        second.setCommenter(user2);
        TestUtils.test_comparable(second, second, first, Comment.USER_NAME_ASCENDING_COMPARATOR);
    }


    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_null_comments_() {
        Ordering<Comment> commentComp = Comment.DATE_ASCENDING_COMPARATOR;
        commentComp.compare(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_null_comments() {
        Ordering<Comment> commentComp = Comment.USER_NAME_ASCENDING_COMPARATOR;
        commentComp.compare(null, null);
    }


    @SuppressWarnings("deprecation")
    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_second_comments() {
        Ordering<Comment> commentComp = Comment.USER_NAME_ASCENDING_COMPARATOR;
        commentComp.compare(new Comment(), null);
    }

    @SuppressWarnings("deprecation")
    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_null_users() {
        Ordering<Comment> commentComp = Comment.USER_NAME_ASCENDING_COMPARATOR;
        commentComp.compare(new Comment(), new Comment());
    }
}
