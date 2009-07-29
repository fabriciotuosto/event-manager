package org.event.manager.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.Validate;
import org.event.manager.Builder;

@Entity
@XmlRootElement
public class Location {

	private Long id;
	private String name;
	private String address;
	
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
	
	public static class LocationBuilder implements Builder<Location>{

		private final String name;
		private final String address;
		
		private LocationBuilder(String name, String address) {
			this.name = name;
			this.address = address;
		}

		@Override
		public Location build() {
			return new Location(this);
		}
		
	}
}
