package org.event.manager.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import javax.persistence.Query;

import org.event.manager.repository.Repository.ResultStrategyEnum;
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
		shoud_call_helper(ResultStrategyEnum.LIST, Collections.emptyList());
	}
	
	@Test
	public void should_call_single_result(){
		when(query.getSingleResult()).thenReturn("one");
		shoud_call_helper(ResultStrategyEnum.SINGLE, "one");
	}
	
	
	private <T> void shoud_call_helper(ResultStrategyEnum strategy,T expected){
		assertEquals(expected, strategy.getResult(query));
	}
}
