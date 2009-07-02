package org.event.manager.guice;

import org.event.manager.UserManager;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;

/**
 * 
 * 
 * @author fabricio
 * 
 * Required module by resteasy framework in order to inject
 * resource handler class with it's dependencies
 *
 */
public class ResourceModule extends AbstractModule {

	/**
	 *  Resources that will be injected with google-guice
	 */
	ImmutableList<Class<?>> resources = ImmutableList.<Class<?>>of(UserManager.class);
	
	@Override
	protected void configure() {
		for(Class<?> clazz : resources){
			bind(clazz);
		}
	}

}
