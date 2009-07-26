package org.event.manager.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Event {

	private Long id;
	private Set<User> users;
	private Location location;
	private Set<Photo> photos;
	private Set<Comment> comments;

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
	@OneToMany
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
}
