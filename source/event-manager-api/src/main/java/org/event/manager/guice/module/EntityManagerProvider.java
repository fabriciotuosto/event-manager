package org.event.manager.guice.module;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Provides {@link EntityManager} instances
 * based on "event-manager" persistent unit
 * it only creates one {@link EntityManagerFactory}
 * because it is expensive to create
 * @author fabricio
 *
 */
@Singleton
public class EntityManagerProvider implements Provider<EntityManager>
{
	/**
	 *
	 */
	private final EntityManagerFactory factory;

	public EntityManagerProvider() {
		// Instantiate jpa EntityManagerFactory
		factory = Persistence.createEntityManagerFactory("event-manager");
	}

	/**
	 *
	 */
	public EntityManager get() {
		return factory.createEntityManager();
	}

}

