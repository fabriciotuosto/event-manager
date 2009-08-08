package org.event.manager.entities;

import com.google.common.collect.Sets;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.event.manager.Builder;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.Set;

@Entity
@Table(name = "Groups")
@XmlRootElement
public class Group {

    private Long id;
    private String name;
    private Set<User> users;

    protected Group() {
        users = Sets.newHashSet();
    }

    @Deprecated
    public Group(Long id) {
        this();
        Validate.notNull(id);
        Validate.isTrue(id > 0);
        setId(id);
    }

    private Group(GroupBuilder groupBuilder) {
        this();
        setName(groupBuilder.name);
        setUsers(groupBuilder.users);
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

    public Group add(User... users) {
        add(Arrays.asList(users));
        return this;
    }

    public Group add(Iterable<User> users) {
        Validate.notNull(users);
        for (User user : users) {
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

    public static GroupBuilder newGroup(String groupName) {
        return new GroupBuilder(groupName);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj instanceof Group) {
            Group other = (Group) obj;
            equals = this == obj || new EqualsBuilder()
                    .append(this.name, other.name).isEquals();
        }
        return equals;
    }

    public static class GroupBuilder implements Builder<Group> {

        private String name;
        private Set<User> users;

        private GroupBuilder(String groupName) {
            Validate.notNull(groupName);
            this.name = groupName;
            this.users = Sets.newHashSet();
        }

        @Override
        public Group build() {
            return new Group(this);
        }

        public GroupBuilder with(User... users) {
            with(Arrays.asList(users));
            return this;
        }

        public GroupBuilder with(Iterable<User> users) {
            Validate.notNull(users);
            for (User user : users) {
                this.users.add(user);
            }
            return this;
        }
    }
}
