package org.event.manager.entities;

import com.google.common.collect.Sets;
import org.event.manager.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

public class EventTest {

    private static final Long ID = 10L;

    @Test
    public void create_event_with_id() {
        Event event = new Event(ID);
        assertNotNull(event);
        assertEquals(ID, event.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_user_id_null() {
        new Event(null);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_user_id_negative() {
        new Event(-10L);
        fail();
    }

    @Test
    public void event_equals() {
        Event first = new Event(ID);
        Event second = new Event(ID);
        Event third = new Event(ID);
        Object object = new Object();
        TestUtils.equalsTest(first, second, third, object);
    }

    @Test
    public void events_hashCode() {
        Event first = new Event(ID);
        Event second = new Event(ID);
        Event different = new Event(500L);
        TestUtils.hashCodeTest(first, second, different);
        assertEquals(31, new Event().hashCode());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void invite_user() {
        User invitee = new User(ID);
        Event event = new Event(ID).invite(invitee);
        assertNotNull(event.getInvitation().getUnresponded());
        assertFalse(event.getInvitation().getUnresponded().isEmpty());
        assertSame(invitee, event.getInvitation().getUnresponded().iterator().next());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void invite_users() {
        User invitee = new User(ID);
        User invitee2 = new User(ID);
        User invitee3 = new User(50L);
        List<User> users = Arrays.asList(invitee, invitee2, invitee3);
        Event event = new Event(ID);
        assertNotNull(event.getInvitation().getUnresponded());
        assertTrue(event.getInvitation().getUnresponded().isEmpty());
        event.invite(users);
        assertFalse(event.getInvitation().getUnresponded().isEmpty());
        assertEquals(2, event.getInvitation().getUnresponded().size());
        assertTrue(event.getInvitation().getUnresponded().contains(invitee));
        assertTrue(event.getInvitation().getUnresponded().contains(invitee2));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void should_be_in_specify_location() {
        Location location = new Location(ID);
        Event event = new Event(ID).on(location);
        assertSame(location, event.getLocation());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void create_with_builder() {
        Location location = new Location(ID);
        Calendar date = Calendar.getInstance();
        Event event = Event.newEvent(location, date).build();
        assertNotNull(event);
        assertSame(location, event.getLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_with_builder_null_location() {
        Event.newEvent(null, null).build();
    }

    @Test
    @SuppressWarnings("deprecation")
    public void create_full_with_builder_user_iterables() {
        Calendar time = Calendar.getInstance();
        Location location = new Location(ID);
        User a = new User(ID);
        User b = new User(50L);
        Event event = Event.newEvent(location, time)
                .invited(Arrays.asList(a, b))
                .build();
        assertEquals(2, event.getInvitation().getUnresponded().size());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void should_add_photos() {
        Photo first = new Photo(ID);
        Photo sec = new Photo(11L);
        Event event = new Event(ID).add(first, sec);
        assertFalse(event.getPhotos().isEmpty());
        assertEquals(2, event.getPhotos().size());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void should_add_photos_iterables() {
        Photo first = new Photo(ID);
        Photo sec = new Photo(11L);
        Event event = new Event(ID).add(Arrays.asList(first, sec));
        assertFalse(event.getPhotos().isEmpty());
        assertEquals(2, event.getPhotos().size());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void should_create_with_photos_iterables_and_builder() {
        Calendar time = Calendar.getInstance();
        Location loc = new Location(ID);
        Photo photo = new Photo(ID);
        Photo photo2 = new Photo(11L);
        Event event = Event.newEvent(loc, time)
                .with(photo, photo2)
                .build();
        assertFalse(event.getPhotos().isEmpty());
        assertEquals(2, event.getPhotos().size());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void should_create_with_photos_and_builder() {
        Calendar time = Calendar.getInstance();
        Location loc = new Location(ID);
        Photo photo = new Photo(ID);
        Photo photo2 = new Photo(11L);
        Event event = Event.newEvent(loc, time)
                .with(Arrays.asList(photo, photo2))
                .build();
        assertFalse(event.getPhotos().isEmpty());
        assertEquals(2, event.getPhotos().size());
    }


    @Test
    @SuppressWarnings("deprecation")
    public void create_full_with_builder() {
        Calendar time = Calendar.getInstance();
        Location loc = new Location(ID);
        User a = new User(ID);
        User b = new User(50L);
        Photo photo = null;
        Event event = Event.newEvent(loc, time)
                .invited(a, b)
                .with(photo)
                .build();
        assertEquals(2, event.getInvitation().getUnresponded().size());
        assertFalse(event.getPhotos().isEmpty());
        assertSame(photo, event.getPhotos().iterator().next());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void should_add_comments_iterables() {
        User user = new User(ID);
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        Calendar today = Calendar.getInstance();
        assertFalse(yesterday.equals(today));

        Comment first = Comment.newComment(user, "").build();
        first.setWhen(yesterday);

        Comment second = Comment.newComment(user, "").build();
        second.setWhen(today);

        Event event = new Event();
        Set<Comment> _result = event.comment(Arrays.asList(first, second)).getComments();

        assertEquals(2, _result.size());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void must_return_ordered_comments() {
        User user = new User(ID);
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        Calendar today = Calendar.getInstance();
        assertFalse(yesterday.equals(today));

        Comment first = Comment.newComment(user, "").build();
        first.setWhen(yesterday);

        Comment second = Comment.newComment(user, "").build();
        second.setWhen(today);

        Set<Comment> orderedResult = Sets.newTreeSet(Comment.DATE_DESCENDING_COMPARATOR);
        orderedResult.add(first);
        orderedResult.add(second);

        Event event = new Event();
        Set<Comment> _result = event.comment(first, second).getComments();

        assertEquals(orderedResult.size(), _result.size());
        Iterator<Comment> expected = orderedResult.iterator();
        Iterator<Comment> result = _result.iterator();
        while (expected.hasNext()) {
            assertSame(expected.next(), result.next());
        }
    }

    @Test
    @SuppressWarnings("deprecation")
    public void should_invite_users() {
        Calendar time = Calendar.getInstance();
        Location loc = new Location(ID);
        User a = mock(User.class);
        User b = mock(User.class);
        Photo photo = null;
        Event event = Event.newEvent(loc, time)
                .invited(a, b)
                .with(photo)
                .build();
        verify(a).invite(event.getInvitation());
        verify(b).invite(event.getInvitation());
    }
}
