package org.event.manager.repository.result;

import javax.persistence.Query;

import org.apache.commons.lang.Validate;

public class PageResultStrategy implements ResultStrategy{

	private final int pageSize;
	
	public PageResultStrategy(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getResult(Query query) {
		Validate.notNull(query);
		return (T) PaginatedResult.newResult(query, pageSize);
	}
	

}
