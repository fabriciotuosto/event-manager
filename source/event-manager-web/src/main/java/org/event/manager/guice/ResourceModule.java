package org.event.manager.guice;

import org.event.manager.UserManager;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * 
 * 
 * @author fabricio
 * 
 *         Required module by resteasy framework in order to inject resource
 *         handler class with it's dependencies
 * 
 */
public class ResourceModule extends AbstractModule {

	/**
	 * Resources that will be injected with google-guice
	 */
	private static final ImmutableSet<Class<?>> resources = ImmutableSet
			.<Class<?>> of(UserManager.class);

	/**
	 * provides resteasy to provide restfull services injected with google-guice
	 * framework
	 */
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("persistent-unit")).toInstance("event-manager");
		for (Class<?> clazz : resources) {
			bind(clazz);
		}
	}

}
