package org.event.manager.guice.interceptors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;


public class PerformanceInterceptorTest {

	private static PerformanceInterceptor interceptor;
	private static Method method;
	
	@BeforeClass
	public static void createInterceptor() throws SecurityException, NoSuchMethodException{
		interceptor = new PerformanceInterceptor();
		method = Object.class.getDeclaredMethod("toString");
	}
	
	@Test
	public void performance_interceptor() throws Throwable{
		MethodInvocation mockInvokation = mock(MethodInvocation.class);
		final String expected = "some valued string";
		when(mockInvokation.proceed()).thenReturn(expected);
		when(mockInvokation.getMethod()).thenReturn(method);
		assertSame(expected,interceptor.invoke(mockInvokation));
		InOrder inOrder = inOrder(mockInvokation);
		inOrder.verify(mockInvokation).proceed();
		inOrder.verify(mockInvokation).getMethod();
		verifyNoMoreInteractions(mockInvokation);
	}
	
	@Test(expected=Throwable.class)
	public void performance_interceptor_fail() throws Throwable{
		MethodInvocation mockInvokation = mock(MethodInvocation.class);
		try{
			when(mockInvokation.proceed()).thenThrow(new Throwable());
			when(mockInvokation.getMethod()).thenReturn(method);
			interceptor.invoke(mockInvokation);			
		}finally{
			InOrder inOrder = inOrder(mockInvokation);
			inOrder.verify(mockInvokation).proceed();
			inOrder.verify(mockInvokation).getMethod();
			verifyNoMoreInteractions(mockInvokation);
		}
	}
}
