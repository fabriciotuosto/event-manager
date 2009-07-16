package org.event.manager.guice.interceptors;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.event.manager.test.TestGroup;
import org.mockito.InOrder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups={TestGroup.UNIT})
public class PerformanceInterceptorTest {

	private PerformanceInterceptor interceptor;
	private Method method;
	
	@BeforeClass
	public void createInterceptor() throws SecurityException, NoSuchMethodException{
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
	
	@Test(expectedExceptions={Throwable.class})
	public void performance_interceptor_fail() throws Throwable{
		MethodInvocation mockInvokation = mock(MethodInvocation.class);
		when(mockInvokation.proceed()).thenThrow(new Throwable());
		when(mockInvokation.getMethod()).thenReturn(method);
		
		InOrder inOrder = inOrder(mockInvokation);
		inOrder.verify(mockInvokation).proceed();
		inOrder.verify(mockInvokation).getMethod();
		verifyNoMoreInteractions(mockInvokation);
	}
}
