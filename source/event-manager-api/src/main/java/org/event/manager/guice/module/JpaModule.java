package org.event.manager.guice.module;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import javax.persistence.EntityManager;

import org.event.manager.dao.annotations.PerforamanceLog;
import org.event.manager.dao.annotations.Tranactional;
import org.event.manager.guice.interceptors.PerformanceInterceptor;
import org.event.manager.guice.interceptors.TransactionInterceptor;

import com.google.inject.AbstractModule;

/**
 *
 * @author fabricio
 *
 */
public class JpaModule extends AbstractModule {

	/**
	 *
	 */
	@Override
	protected void configure() {
		bind(EntityManager.class)
		.toProvider(EntityManagerProvider.class);

		bindInterceptor(any(),
				annotatedWith(Tranactional.class),
				new TransactionInterceptor());

		bindInterceptor(any(),
				annotatedWith(PerforamanceLog.class),
				new PerformanceInterceptor());
	}

}

