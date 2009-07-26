package org.event.manager.guice.interceptors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.aopalliance.intercept.MethodInvocation;
import org.event.manager.dao.Dao;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;

public class TransactionInterceptorTest {

	private static TransactionInterceptor interceptor;
	private EntityManager em;
	private EntityTransaction trx;
	private Dao dao;
	private MethodInvocation mockInvokation;
	
	@BeforeClass
	public static void createInterceptor() throws SecurityException, NoSuchMethodException{
		interceptor = new TransactionInterceptor();
	}
	
	@Before
	public void prepareMocks(){		
		em = mock(EntityManager.class);
		trx = mock(EntityTransaction.class);
		dao = mock(Dao.class);
		mockInvokation = mock(MethodInvocation.class);
		
		when(mockInvokation.getThis()).thenReturn(dao);
		when(dao.getEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(trx);
		
	}
	
	public void verified_no_further_interactions(){
		verifyNoMoreInteractions(mockInvokation,em,dao,trx);
	}
	@Test
	public void new_transaction_no_exceptions() throws Throwable{
		when(trx.isActive()).thenReturn(false);
		interceptor.invoke(mockInvokation);
		
		InOrder inOrder = inOrder(mockInvokation,dao,em,trx);
		inOrder.verify(mockInvokation).getThis();
		inOrder.verify(dao).getEntityManager();
		inOrder.verify(em).getTransaction();
		inOrder.verify(trx).isActive();
		inOrder.verify(dao).getEntityManager();
		inOrder.verify(em).getTransaction();
		inOrder.verify(trx).begin();
		inOrder.verify(mockInvokation).proceed();
		inOrder.verify(trx).commit();
		verified_no_further_interactions();
	}
	
	@Test
	public void join_transaction_no_exceptions() throws Throwable{
		when(trx.isActive()).thenReturn(true);
		interceptor.invoke(mockInvokation);
		
		InOrder inOrder = inOrder(mockInvokation,dao,em,trx);
		inOrder.verify(mockInvokation).getThis();
		inOrder.verify(dao).getEntityManager();
		inOrder.verify(em).getTransaction();
		inOrder.verify(trx).isActive();
		inOrder.verify(dao).getEntityManager();
		inOrder.verify(em).joinTransaction();
		inOrder.verify(mockInvokation).proceed();
		verified_no_further_interactions();
	}
	
	@Test(expected=Throwable.class)
	public void new_transaction_throw_exceptions() throws Throwable{
		when(trx.isActive()).thenReturn(false);
		when(mockInvokation.proceed()).thenThrow(new Throwable());
		try{			
			interceptor.invoke(mockInvokation);
			fail();
		}finally {
			verify(mockInvokation).getThis();
			verify(dao,times(2)).getEntityManager();
			verify(em,times(2)).getTransaction();
			verify(trx).isActive();
			verify(trx).begin();
			verify(mockInvokation).proceed();
			
			verify(trx).rollback();
			verified_no_further_interactions();
		}
	}
}
