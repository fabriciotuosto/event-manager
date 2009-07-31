package org.event.manager.entities;

import java.net.URI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.event.manager.Builder;

@Entity
@XmlRootElement
public class Location {

	private Long id;
	private String name;
	private String address;
	private URI mapUri;
	
	@Deprecated
	public Location() {
	
	}
	
	@Deprecated
	public Location(Long id) {
		Validate.notNull(id);
		Validate.isTrue(id.longValue() > 0);
		setId(id);
	}

	public static LocationBuilder newLocation(String name,String adress){
		return new LocationBuilder(name,adress);
	}
	
	private Location(LocationBuilder builder){
		setName(builder.name);
		setAddress(builder.address);
		mapUri = builder.mapLocation;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getMapUri() {
		return mapUri.toString();
	}

	public void setMapUri(String mapUri) {
		this.mapUri = URI.create(mapUri);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof Location){
			Location other = (Location) obj;
			equals = this== obj || new EqualsBuilder()
								.append(this.address, other.address)
								.append(this.name, other.name).isEquals();		
		}
		return equals;
	}

	public static class LocationBuilder implements Builder<Location>{

		private final String name;
		private final String address;
		private URI mapLocation;
		
		private LocationBuilder(String name, String address) {
			this.name = name;
			this.address = address;
		}

		@Override
		public Location build() {
			return new Location(this);
		}

		public LocationBuilder mapLocation(String string) {
			mapLocation = URI.create(string);
			return this;
		}
		
	}
}
