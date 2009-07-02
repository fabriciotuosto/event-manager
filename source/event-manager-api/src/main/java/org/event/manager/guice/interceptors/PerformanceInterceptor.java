package org.event.manager.guice.interceptors;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.time.StopWatch;

/**
 *
 * @author fabricio
 *
 * Measures time performance of {@link Method}s intercepted
 */
public class PerformanceInterceptor implements MethodInterceptor
{

	/**
	 * Measures the time of the {@link MethodInvocation}
	 */
	public Object invoke(MethodInvocation arg0) throws Throwable {
		StopWatch watch = new StopWatch();
		watch.start();
		Object result = null;
		try {
			result = arg0.proceed();
		} finally{
			watch.stop();
			System.out.println(String.format("%s() -> %s",arg0.getMethod().getName(),watch.toString()));
		}
		return result;
	}

}

