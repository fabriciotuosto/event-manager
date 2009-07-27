package org.event.manager.repository.guice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class EntityManagerProvider implements Provider<EntityManager>{

	private final EntityManagerFactory factory;
	
	@Inject
	public EntityManagerProvider(@Named("persistent-unit") String unit) {
		factory = Persistence.createEntityManagerFactory(unit);
	}

	@Override
	public EntityManager get() {
		return factory.createEntityManager();
	}
	
	
	
}
