package org.event.manager.repository.interceptors;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author fabricio
 * 
 *         Measures time performance of {@link Method}s intercepted
 */
public class PerformanceInterceptor implements MethodInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(PerformanceInterceptor.class);

	/**
	 * Measures the time of the {@link MethodInvocation}
	 * 
	 * @param invocation
	 * @throws Throwable
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		StopWatch watch = new StopWatch();
		watch.start();
		Object result = null;
		try {
			result = invocation.proceed();
		} finally {
			watch.stop();
			logger.info("{} -> finished in {}ms", invocation.getMethod().getName(),
					watch.toString());
		}
		return result;
	}

}
