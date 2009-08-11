package org.event.manager.repository.result;

import javax.persistence.Query;

public interface ResultStrategy {
		
	public <T> T getResult(Query query);
}
