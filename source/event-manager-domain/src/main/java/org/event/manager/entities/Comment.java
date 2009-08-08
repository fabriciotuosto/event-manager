package org.event.manager.entities;

import com.google.common.collect.Ordering;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.event.manager.Builder;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;
import java.util.Comparator;

@Entity
@XmlRootElement
public class Comment {

    public static final Ordering<Comment> DATE_ASCENDING_COMPARATOR = Ordering.from(Comparators.DATE_ASC);
    public static final Ordering<Comment> DATE_DESCENDING_COMPARATOR = DATE_ASCENDING_COMPARATOR.reverse();
    public static final Ordering<Comment> USER_NAME_ASCENDING_COMPARATOR = Ordering.from(Comparators.USER_ASC);

    private Long id;
    private Calendar when;
    private User commenter;
    private String comment;

    protected Comment() {
        super();
    }

    @Deprecated
    public Comment(Long id) {
        this();
        Validate.notNull(id);
        Validate.isTrue(id > 0);
        setId(id);
    }

    public static CommentBuilder newComment(User commenter, String comment) {
        return new CommentBuilder(commenter, comment);
    }

    private Comment(CommentBuilder builder) {
        this();
        setWhen(builder.when);
        setCommenter(builder.commenter);
        setComment(builder.comment);
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getWhen() {
        return when;
    }

    public void setWhen(Calendar when) {
        this.when = when;
    }

    @OneToOne(targetEntity = User.class)
    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User user) {
        this.commenter = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (obj instanceof Comment) {
            Comment other = (Comment) obj;
            equals = new EqualsBuilder()
                    .append(this.id, other.id).isEquals();
        }
        return equals;
    }

    public static class CommentBuilder implements Builder<Comment> {

        private final String comment;
        private final User commenter;
        private final Calendar when;

        private CommentBuilder(User commenter, String comment) {
            Validate.notNull(commenter, "A cannot be created without a commenter");
            Validate.notNull(comment, "A cannot be created without a comment");
            this.comment = comment;
            this.commenter = commenter;
            this.when = Calendar.getInstance();
        }

        @Override
        public Comment build() {
            return new Comment(this);
        }
    }

    private static enum Comparators implements Comparator<Comment> {
        DATE_ASC() {
            @Override
            public int compare(Comment o1, Comment o2) {
                Validate.notNull(o1);
                Validate.notNull(o2);
                return o1.getWhen().compareTo(o2.getWhen());
            }},
        USER_ASC() {
            @Override
            public int compare(Comment o1, Comment o2) {
                Validate.notNull(o1);
                Validate.notNull(o2);
                Validate.notNull(o1.getCommenter());
                Validate.notNull(o2.getCommenter());
                return o1.getCommenter().getName().compareTo(o2.getCommenter().getName());
            }
        }


    }
}
