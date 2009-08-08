package org.event.manager.repository.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class EntityManagerProvider implements Provider<EntityManager> {

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
