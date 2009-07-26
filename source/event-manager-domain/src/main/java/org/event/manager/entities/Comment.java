package org.event.manager.entities;

import java.util.Calendar;

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

@Entity
@XmlRootElement
public class Comment {

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
}
