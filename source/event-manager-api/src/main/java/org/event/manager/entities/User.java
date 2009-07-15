package org.event.manager.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.event.manager.Builder;
import org.event.manager.utils.EmailUtils;

/**
 * 
 * @author tuosto
 */
@Entity
@XmlRootElement
@Table(name = "t_user")
@UniqueConstraint(columnNames = "name")
@NamedQueries( { @NamedQuery(name = User.FIND_BY_CREDENTIALS, query = "SELECT o FROM User o where o.name = :name and o.password = :password") })
public class User implements Serializable {

	private static final long serialVersionUID = 4547695106746968938L;
	public static final String FIND_BY_CREDENTIALS = "org.event.manager.entities.User.findByCredentials";
	private Long id;
	private String name;
	private String email;
	private String password;
	private Set<Group> groups;
	private Set<User> contacts;

	/**
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * @return
	 */
	public static UserBuilder createUser(String name, String email,
			String password) {
		return new UserBuilder(name, email, password);
	}

	/**
	 * Default constructor for JavaBeans Specification needed for frameworks
	 */
	public User() {
		super();
	}

	/**
	 * Easy creates a user with some id in order to provide an easy example to
	 * search in jpa
	 * 
	 * @param id
	 */
	public User(Long id) {
		this();
		this.id = id;
	}

	/**
	 * Build a new user from the user builder
	 * 
	 * @param builder
	 */
	private User(UserBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.email = builder.email;
		this.password = builder.password;
		this.groups = builder.groups;
		this.contacts = builder.contacts;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * @return the groups
	 */
	@OneToMany
	public Set<Group> getGroups() {
		return groups;
	}

	/**
	 * @param groups
	 *            the groups to set
	 */
	public User setGroups(Set<Group> groups) {
		this.groups = groups;
		return this;
	}
	
	/**
	 * 
	 * @param groups
	 * @return
	 */
	public User addGroup(Group... groups){
		for(Group group: groups){
			this.groups.add(group);
		}
		return this;
	}
	
	/**
	 * @return the contacts
	 */
	@OneToMany
	public Set<User> getContacts() {
		return contacts;
	}
	
	/**
	 * 
	 * @param contacs
	 * @return
	 */
	public User addContact(User... contacs){
		for(User contact: contacs){
			this.contacts.add(contact);
		}
		return this;
	}
	/**
	 * @param contacts
	 *            the contacts to set
	 */
	public void setContacts(Set<User> contacts) {
		this.contacts = contacts;
	}	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object obj) {
		boolean retval = false;
		if (obj instanceof User) {
			User other = (User) obj;
			if (this.id != null) {
				retval = this.id.equals(other.id);
			}
		}
		return retval;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}



	public static class UserBuilder implements Builder<User> {

		private Long id;
		private String name;
		private String email;
		private String password;
		private Set<Group> groups;
		private Set<User> contacts;

		private UserBuilder(String name, String email, String password) {
			Validate.notNull(name, "The name of the user cannot be null");
			Validate.notNull(password,
					"The password of the user cannot be null");
			Validate.notNull(email, "The email of the user cannot be null");
			Validate.isTrue(EmailUtils.isEmailAdressValid(email),
					"The email must be a valid mail address");
			this.name = name;
			this.email = email;
			this.password = password;
		}

		@Override
		public User build() {
			return new User(this);
		}
	}
}
