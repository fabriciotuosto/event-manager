package org.event.manager.repository.guice;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import javax.persistence.EntityManager;

import org.event.annotations.repository.PerforamanceLog;
import org.event.annotations.repository.Tranactional;
import org.event.manager.repository.interceptors.PerformanceInterceptor;
import org.event.manager.repository.interceptors.TransactionInterceptor;

import com.google.inject.AbstractModule;

/**
 * 
 * @author fabricio
 * 
 */
public class InterceptorModule extends AbstractModule {

	/**
	 * Configures the JPA configuration for this project
	 */
	@Override
	protected void configure() {

		bindInterceptor(any(), annotatedWith(Tranactional.class),
				new TransactionInterceptor());

		bindInterceptor(any(), annotatedWith(PerforamanceLog.class),
				new PerformanceInterceptor());
		
		bind(EntityManager.class).toProvider(EntityManagerProvider.class);
	}

}
