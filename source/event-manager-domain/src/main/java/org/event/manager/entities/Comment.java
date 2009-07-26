package org.event.manager.entities;

import java.util.Calendar;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.Validate;
import org.event.manager.Builder;

import com.google.common.collect.Ordering;

@Entity
@XmlRootElement
public class Comment {
	
	public static final Comparator<Comment> DATE_ASCENDING_COMPARATOR = Comparators.DATE_ASC;
	public static final Comparator<Comment> DATE_DESCENDING_COMPARATOR = Ordering.from(DATE_ASCENDING_COMPARATOR).reverse();
	public static final Comparator<Comment> USER_NAME_ASCENDING_COMPARATOR = Comparators.DATE_ASC;
	
	private Long id;
	private Calendar when;
	private User commenter;
	private String comment;

	@Deprecated
	public Comment() {
		super();
	}
	
	public static CommentBuilder newComment(User commenter,String comment){
		return new CommentBuilder(commenter,comment);
	}
	
	private Comment(CommentBuilder builder) {
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

	public static class CommentBuilder implements Builder<Comment>{

		private final String comment;
		private final User commenter;
		private final Calendar when;

		private CommentBuilder(User commenter, String comment) {
			Validate.notNull(commenter,"A cannot be created without a commenter");
			Validate.notNull(comment,"A cannot be created without a comment");
			this.comment = comment;
			this.commenter = commenter;
			this.when = Calendar.getInstance();
		}

		@Override
		public Comment build() {
			return new Comment(this);
		}		
	}
	
	private static enum Comparators implements Comparator<Comment>{
		DATE_ASC(){
			@Override
			public int compare(Comment o1, Comment o2) {
				Validate.notNull(o1);
				Validate.notNull(o2);
				return o1.getWhen().compareTo(o2.getWhen());
			}},
		USER_ASC(){
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
