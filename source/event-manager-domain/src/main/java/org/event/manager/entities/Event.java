package org.event.manager.entities;

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
import org.event.manager.Builder;

import com.google.common.collect.Sets;

@Entity
@XmlRootElement
public class Event {

	private Long id;
	private Set<User> users;
	private Location location;
	private Set<Photo> photos;
	private Set<Comment> comments;
	
	@Deprecated
	public Event(Long id) {
		this();
		Validate.notNull(id,"Must not be a null Id");
		Validate.isTrue(id.longValue() > 0);
		setId(id);
	}

	@Deprecated
	public Event() {
		users = Sets.newHashSet();
		photos = Sets.newHashSet();
		comments = Sets.newTreeSet(Comment.DATE_DESCENDING_COMPARATOR);
	}

	private Event(EventBuilder eventBuilder) {
		setUsers(eventBuilder.users);
		setLocation(eventBuilder.location);
		setPhotos(eventBuilder.photos);
		setComments(eventBuilder.comments);
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (!id.equals(other.id))
			return false;
		return true;
	}

	public Event invite(User... invitees) {
		for(User invitee : invitees){
			this.users.add(invitee);
		}
		return this;
	}

	public Event on(Location location) {
		this.location = location;
		return this;
	}

	public static EventBuilder newEvent(Location location) {
		return new EventBuilder(location);
	}
	
	public static class EventBuilder implements Builder<Event> {
		private Set<User> users;
		private Location location;
		private Set<Photo> photos;
		private Set<Comment> comments;
		
		private EventBuilder(Location location){
			Validate.notNull(location);
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

		public EventBuilder with(Photo... photos) {
			for(Photo photo : photos){
				this.photos.add(photo);
			}
			return this;
		}

	}

	public Event comment(Comment... comments) {
		for(Comment coment: comments){
			this.comments.add(coment);
		}
		return this;
	}
}
