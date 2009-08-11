package org.event.manager.repository.result;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.Validate;

public class PaginatedResult<T>{
	private final int pageSize;
	private final int count;
	private final Query query;
	
	private PaginatedResult(Query query, int pageSize) {
		this.pageSize = pageSize;
		this.query = query;
		this.count = query.getResultList().size();
		this.query.setMaxResults(this.pageSize);
	}
	
	public static <T> PaginatedResult<List<T>> newResult(Query query,int pageSize){
		return new PaginatedResult<List<T>>(query, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getPage(int page){
		Validate.isTrue(page >= 0);
		final int firstResult = page * pageSize;
		Validate.isTrue(firstResult <= count);		
		query.setFirstResult(firstResult);
		return query.getResultList();
	}
}
