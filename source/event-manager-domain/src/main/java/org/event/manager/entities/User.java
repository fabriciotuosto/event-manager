package org.event.manager.entities;

import com.google.common.collect.Sets;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.event.manager.Builder;
import org.event.manager.utils.Utils;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

/**
 * @author tuosto
 */
@Entity
@XmlRootElement
@Table(name = "Users")
@NamedQueries({@NamedQuery(name = User.FIND_BY_CREDENTIALS, query = "SELECT o FROM User o where o.name = :name and o.password = :password")})
public class User implements Serializable {

    private static final long serialVersionUID = 4547695106746968938L;
    public static final String FIND_BY_CREDENTIALS = "org.event.manager.entities.User.findByCredentials";
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<Group> groups;
    private Set<User> contacts;
    private Set<Invitation> respondedInvitations;
    private Set<Invitation> pendingResponeInvitations;

    public static class UserBuilder implements Builder<User> {
        private final String name;
        private final String email;
        private final String password;
        private Set<Group> groups;
        private Set<User> contacts;

        private UserBuilder(String name, String email, String password) {
            Validate.notNull(name, "The name of the user cannot be null");
            Validate.notNull(password,
                    "The password of the user cannot be null");
            Validate.notNull(email, "The email of the user cannot be null");
            Validate.isTrue(Utils.isEmailAdressValid(email),
                    "The email must be a valid mail address");
            this.name = name;
            this.email = email;
            this.password = password;
            this.groups = Sets.newHashSet();
            this.contacts = Sets.newHashSet();
        }

        /**
         *
         */
        @Override
        public User build() {
            return new User(this);
        }

        /**
         * @param groups
         * @return
         */
        public UserBuilder inGroup(Group... groups) {
            inGroup(Arrays.asList(groups));
            return this;
        }

        /**
         * @param groups
         * @return
         */
        public UserBuilder inGroup(Iterable<Group> groups) {
            for (Group group : groups) {
                this.groups.add(group);
            }
            return this;
        }

        /**
         * @param contacts
         * @return
         */
        public UserBuilder withContacts(User... contacts) {
            withContacts(Arrays.asList(contacts));
            return this;
        }

        /**
         * @param contacts
         * @return
         */
        public UserBuilder withContacts(Iterable<User> contacts) {
            for (User contact : contacts) {
                this.contacts.add(contact);
            }
            return this;
        }
    }

    /**
     * @param name
     * @param email
     * @param password
     * @return
     */
    public static UserBuilder newUser(String name, String email, String password) {
        return new UserBuilder(name, email, password);
    }

    /**
     * Default constructor for JavaBeans Specification needed for frameworks
     * <p/>
     * use {@code User.createUser()} instead it's garanties consistency of user
     * created, this class implements javabean specification in order to work
     * with jpa framework
     */
    @Deprecated
    public User() {
        super();
        setGroups(Sets.<Group>newHashSet());
        setContacts(Sets.<User>newHashSet());
        setPendingResponeInvitations(Sets.<Invitation>newHashSet());
        setRespondedInvitations(Sets.<Invitation>newHashSet());
    }

    /**
     * Easy creates a user with some id in order to provide an easy example to
     * search in jpa
     * <p/>
     * use {@code User.createUser()} instead it's garanties consistency of user
     * created, this class implements javabean specification in order to work
     * with jpa framework
     *
     * @param id
     */
    @Deprecated
    public User(Long id) {
        this();
        Validate.notNull(id);
        Validate.isTrue(id > 0, "Id must be positive");
        setId(id);
    }

    /**
     * Build a new user from the user builder
     *
     * @param builder
     */
    private User(UserBuilder builder) {
        this();
        setName(builder.name);
        setEmail(builder.email);
        setPassword(builder.password);
        setGroups(Sets.newHashSet(builder.groups));
        setContacts(Sets.newHashSet(builder.contacts));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the groups
     */
    @OneToMany
    public Set<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    /**
     * @param groups
     * @return
     */
    public User add(Group... groups) {
        add(Arrays.asList(groups));
        return this;
    }

    /**
     * @param groups
     * @return
     */
    public User add(Iterable<Group> groups) {
        Validate.notNull(groups);
        for (Group group : groups) {
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
     * @param contacts
     * @return
     */
    public User addContact(User... contacts) {
        addContact(Arrays.asList(contacts));
        return this;
    }

    /**
     * @param contacts
     * @return
     */
    public User addContact(Iterable<User> contacts) {
        Validate.notNull(contacts);
        for (User contact : contacts) {
            this.contacts.add(contact);
        }
        return this;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(Set<User> contacts) {
        this.contacts = contacts;
    }

    @OneToMany
    public Set<Invitation> getPendingResponeInvitations() {
        return pendingResponeInvitations;
    }

    public void setPendingResponeInvitations(
            Set<Invitation> pendingResponeInvitations) {
        this.pendingResponeInvitations = pendingResponeInvitations;
    }

    @OneToMany
    public Set<Invitation> getRespondedInvitations() {
        return respondedInvitations;
    }

    public void setRespondedInvitations(Set<Invitation> respondedInvitations) {
        this.respondedInvitations = respondedInvitations;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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
        if (obj instanceof User) {
            User other = (User) obj;
            equals = this == obj
                    || new EqualsBuilder().append(this.id, other.id).isEquals();
        }
        return equals;
    }

    public InvitationResponse invite(Invitation invitation) {
        pendingResponeInvitations.add(invitation);
        return new InvitationResponse(this, invitation);
    }

    public InvitationResponse respondTo(Invitation invitation) {
        return new InvitationResponse(this, invitation);
    }

    public static class InvitationResponse {
        private final User user;
        private final Invitation invitation;

        private InvitationResponse(User user, Invitation invitation) {
            this.user = user;
            this.invitation = invitation;
        }

        public InvitationResponse yes() {
            return with(Response.YES);
        }

        public InvitationResponse no() {
            return with(Response.NO);
        }

        public InvitationResponse maybe() {
            return with(Response.MAYBE);
        }

        public InvitationResponse with(Response response) {
			user.pendingResponeInvitations.remove(invitation);
			user.respondedInvitations.add(invitation);
			invitation.respond(user, response);
			return this;
		}
	}
}
