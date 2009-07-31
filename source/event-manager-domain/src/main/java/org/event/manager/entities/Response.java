package org.event.manager.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public enum Response {
	YES(1L),
	NO(2L),
	MAYBE(3L);
	
	Response(Long id){
		this.id = id;
	}
	
	private final Long id;

	@Id
	public Long getId() {
		return id;
	}

}
