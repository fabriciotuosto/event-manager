package org.event.manager.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import javax.persistence.Query;

import org.event.manager.repository.Repository.ResultStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResultStrategyTest {
	
	private static Query query;
	
	@BeforeClass
	public static void initialConfiguration(){
		query = mock(Query.class);
	}
	
	@Test
	public void shoul_call_resultList(){
		when(query.getResultList()).thenReturn(Collections.emptyList());
		shoud_call_helper(ResultStrategy.LIST, Collections.emptyList());
	}
	
	@Test
	public void should_call_single_result(){
		when(query.getSingleResult()).thenReturn("one");
		shoud_call_helper(ResultStrategy.SINGLE, "one");
	}
	
	
	private <T> void shoud_call_helper(ResultStrategy strategy,T expected){
		assertEquals(expected, strategy.getResult(query));
	}
}
