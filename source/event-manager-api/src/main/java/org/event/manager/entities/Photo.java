package org.event.manager.entities;

import java.net.URI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.event.manager.Builder;
import org.event.manager.utils.InternetUtils;

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
	public static PhotoBuilder createPhoto(String name,String uri){
		Validate.notNull(uri,"URI cannot be null");
		return new PhotoBuilder(name,URI.create(uri));
	}
	
	/**
	 * 
	 * @param name
	 * @param uri
	 * @return
	 */
	public static PhotoBuilder createPhoto(String name,URI uri){
		return new PhotoBuilder(name,uri);
	}
	/**
	 * 
	 * @param id
	 */
	@Deprecated
	public Photo(Long id){
		Validate.notNull(id, "Id must not be null");
		Validate.isTrue(id.longValue() > 0,"Id must be positive");
		this.id = id;
	}
	
	/**
	 * 
	 * @param photoBuilder
	 */
	private Photo(PhotoBuilder photoBuilder) {
		this.id = photoBuilder.id;
		this.name = photoBuilder.name;
		this.tooltip = photoBuilder.tooltip;
		this.uri = photoBuilder.uri;
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
		boolean retval = false;
		if (this == obj){
			retval = true;
		}
		if (obj instanceof Photo) {
			Photo other = (Photo) obj;
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
	
	public static class PhotoBuilder implements Builder<Photo>{

		private Long id;
		private String name;
		private String tooltip;
		private URI uri;

		private PhotoBuilder(String name, URI uri) {
			Validate.notNull(name, "Cannot create photo without name");
			Validate.notNull(uri, "Cannot create photo without uri");
			Validate.isTrue(InternetUtils.isImageLinkValid(uri.toString()));
			this.name = name;
			this.uri = uri;
		}
		
		@Override
		public Photo build() {
			return new Photo(this);
		}
		
	} 
}
