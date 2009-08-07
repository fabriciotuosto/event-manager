package org.event.manager.entities;

import java.net.URI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.event.manager.Builder;
import org.event.manager.utils.Utils;

@Entity
@XmlRootElement
@UniqueConstraint(columnNames = "uri")
public class Photo {
	private Long id;
	private String name;
	private String tooltip;
	private URI uri;

	/**
	 * 
	 */
	@Deprecated
	public Photo(){
		super();
	}
	
	/**
	 * 
	 * @param name
	 * @param uri
	 * @return
	 */
	public static PhotoBuilder newPhoto(String name,String uri){
		Validate.notNull(uri,"URI cannot be null");
		return new PhotoBuilder(name,URI.create(uri));
	}
	
	/**
	 * 
	 * @param name
	 * @param uri
	 * @return
	 */
	public static PhotoBuilder newPhoto(String name,URI uri){
		return new PhotoBuilder(name,uri);
	}
	/**
	 * 
	 * @param id
	 */
	@Deprecated
	public Photo(Long id){
		Validate.notNull(id, "Id must not be null");
		Validate.isTrue(id > 0,"Id must be positive");
		setId(id);
	}
	
	/**
	 * 
	 * @param photoBuilder
	 */
	private Photo(PhotoBuilder photoBuilder) {
		setName(photoBuilder.name);
		setTooltip(photoBuilder.tooltip);
		setUri(photoBuilder.uri);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof Photo){
			Photo other = (Photo) obj;
			equals = this== obj || new EqualsBuilder()
								.append(this.id, other.id).isEquals();		
		}
		return equals;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(31).append(id).toHashCode();
	}
	
	public static class PhotoBuilder implements Builder<Photo>{
		private String name;
		private String tooltip;
		private URI uri;

		private PhotoBuilder(String name, URI uri) {
			Validate.notNull(name, "Cannot create photo without name");
			Validate.notNull(uri, "Cannot create photo without uri");
			Validate.isTrue(Utils.isImageLinkValid(uri.toString()));
			this.name = name;
			this.uri = uri;
		}
		
		@Override
		public Photo build() {
			return new Photo(this);
		}

		/**
		 * 
		 * @param tooltip
		 * @return
		 */
		public PhotoBuilder withToolTip(String tooltip) {
			this.tooltip = tooltip;
			return this;
		}
		
	} 
}
