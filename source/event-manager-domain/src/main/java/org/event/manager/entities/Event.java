package org.event.manager.entities;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.event.manager.Builder;

import com.google.common.collect.Sets;

@Entity
@XmlRootElement
public class Event {

	private Long id;
	private Set<User> users;
	private Location location;
	private Invitation invitation;
	private Set<Photo> photos;
	private Set<Comment> comments;
	private Calendar time;
	
	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public static EventBuilder newEvent(Location location,Calendar time) {
		return new EventBuilder(location,time);
	}
	@Deprecated
	public Event(Long id) {
		this();
		Validate.notNull(id,"Must not be a null Id");
		Validate.isTrue(id.longValue() > 0);
		setId(id);
	}

	@Deprecated
	public Event() {
		this.invitation = new Invitation();
		users = Sets.newHashSet();
		photos = Sets.newHashSet();
		comments = Sets.newTreeSet(Comment.DATE_DESCENDING_COMPARATOR);
	}

	private Event(EventBuilder eventBuilder) {
		setUsers(eventBuilder.users);
		setLocation(eventBuilder.location);
		setPhotos(eventBuilder.photos);
		setComments(eventBuilder.comments);
		setTime(eventBuilder.time);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(targetEntity = User.class)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToOne(targetEntity = Location.class)
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the photos
	 */
	@OneToMany
	public Set<Photo> getPhotos() {
		return photos;
	}

	/**
	 * @param photos
	 *            the photos to set
	 */
	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	/**
	 * @return the comments
	 */
	@OneToMany @OrderBy(value="when DESC")
	public Set<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof Event){
			Event other = (Event) obj;
			equals = this== obj || new EqualsBuilder()
								.append(this.id, other.getId()).isEquals();		
		}
		return equals;
	}

	public Event invite(User... invitees) {
		for(User invitee : invitees){
			this.users.add(invitee);
		}
		return this;
	}

	public Event invite(Iterable<User> invitees) {
		Validate.notNull(invitees);
		for(User invitee : invitees){
			this.users.add(invitee);
		}
		return this;
	}	

	public Event add(Iterable<Photo> photos) {
		Validate.notNull(photos);
		for(Photo photo : photos){
			this.photos.add(photo);
		}
		return this;
	}

	public Event add(Photo... photos) {
		for(Photo photo : photos){
			this.photos.add(photo);
		}
		return this;
	}

	public Event on(Location location) {
		this.location = location;
		return this;
	}
	
	
	public Invitation sendInvitation() {
		invitation = new Invitation(this);
		for(User user : users){
			invitation.invite(user);
		}
		return invitation;
	}
	
	
	public static class EventBuilder implements Builder<Event> {
		private Set<User> users;
		private Location location;
		private Set<Photo> photos;
		private Set<Comment> comments;
		private Calendar time;
		
		private EventBuilder(Location location,Calendar time){
			Validate.notNull(location);
			Validate.notNull(time);
			this.time = time;
			this.users = Sets.newHashSet();
			this.location = location;
			this.photos = Sets.newHashSet();
			this.comments = Sets.newTreeSet(Comment.DATE_DESCENDING_COMPARATOR);
		}
		@Override
		public Event build() {
			return new Event(this);
		}

		public EventBuilder invited(User... invited) {
			for(User u : invited){
				users.add(u);
			}
			return this;
		}

		public EventBuilder invited(Iterable<User> invited) {
			Validate.notNull(invited);
			for(User u : invited){
				users.add(u);
			}
			return this;
		}
		
		public EventBuilder with(Iterable<Photo> photos) {
			Validate.notNull(photos);
			for(Photo photo : photos){
				this.photos.add(photo);
			}
			return this;
		}		
		
		public EventBuilder with(Photo... photos) {
			for(Photo photo : photos){
				this.photos.add(photo);
			}
			return this;
		}

	}
	
    public Event comment(Iterable<Comment> comments) {
    	Validate.notNull(comments);
		for(Comment coment: comments){
			this.comments.add(coment);
		}
		return this;
	}

	public Event comment(Comment... comments) {
		for(Comment coment: comments){
			this.comments.add(coment);
		}
		return this;
	}


}