package org.event.manager.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

@Entity
@Table(name = "Groups")
@XmlRootElement
public class Group {

	private Long id;
	private String name;
	private Set<User> users;

	public Group() {
		users = Sets.newHashSet();
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

	public Group addUser(User... users){
		for(User user: users){
			this.users.add(user);
		}
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
