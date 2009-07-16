package org.event.manager.guice.interceptors;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.aopalliance.intercept.MethodInvocation;
import org.event.manager.dao.Dao;
import org.event.manager.test.TestGroup;
import org.mockito.InOrder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TransactionInterceptorTest {

	private TransactionInterceptor interceptor;
	private Method method;
	private EntityManager em;
	private EntityTransaction trx;
	private Dao dao;
	private MethodInvocation mockInvokation;
	@BeforeClass
	public void createInterceptor() throws SecurityException, NoSuchMethodException{
		interceptor = new TransactionInterceptor();
	}
	
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
		prepareMocks();

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
		prepareMocks();
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
	
//	@Test(expectedExceptions={Throwable.class})
//	public void performance_interceptor_fail() throws Throwable{
//		prepareMocks();
//		when(mockInvokation.proceed()).thenThrow(new Throwable());
//		when(mockInvokation.getMethod()).thenReturn(method);
//		
//		InOrder inOrder = inOrder(mockInvokation);
//		inOrder.verify(mockInvokation).proceed();
//		inOrder.verify(mockInvokation).getMethod();
//		verifyNoMoreInteractions(mockInvokation);
//	}
}
