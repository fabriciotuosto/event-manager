package org.event.manager.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.Validate;
import org.event.manager.dao.annotations.PerforamanceLog;
import org.event.manager.dao.annotations.Tranactional;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * 
 * @author fabricio
 * 
 * {@link Dao} provides methods to interact easily with entity manager
 * if this class was not provided by {@link Injector} then transaction
 * and performance logging is in hands of the client class if you use it
 * with Guice we provide a module that configures this class and
 * {@link EntityManager} creation This class should be used and disposed
 * it intends to be ideally in thread local scope, this class is not
 * thread safe and is not intend to be, because {@link EntityManager}
 * that is the sole dependency are very cheap to create so is this class
 */
public class Dao {

	/**
	 * The entity manager that provides orm object
	 */
	private final EntityManager em;

	/**
	 * 
	 * @param em
	 *            the entity manager that will be consulted
	 * @throws IllegalArgumentException
	 *             when @param em is null
	 */
	@Inject
	public Dao(EntityManager em) {
		Validate.notNull(em, "Entity manger must not be null");
		this.em = em;
	}


	/**
	 * Removes the element from the associated {@link EntityManager}
	 * 
	 * @param <E>
	 * @param elements
	 * @return
	 */
	@Tranactional
	@PerforamanceLog
	public <E> E remove(E elements) {
		em.remove(elements);
		return elements;
	}

	/**
	 * Removes all the <E> elements from the associated {@link EntityManager}
	 * 
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	@Tranactional
	@PerforamanceLog
	public <E> List<E> removeAll(Class<E> clazz) {
		List<E> elist = findAllByClass(clazz);
		for (E e : elist) {
			remove(e);
		}
		return elist;
	}

	/**
	 * 
	 * @param <E>
	 * @param <F>
	 * @param clazz
	 * @param id
	 * @return
	 */
	@Tranactional
	@PerforamanceLog
	public <E, F> E removeById(Class<E> clazz, F id) {
		E e = findById(clazz, id);
		em.remove(e);
		return e;
	}

	/**
	 * 
	 * @param <E>
	 * @param e
	 * @return
	 */
	@Tranactional
	@PerforamanceLog
	public <E> E persist(E e) {
		em.persist(e);
		return e;
	}

	/**
	 * 
	 * @param <E>
	 * @param e
	 * @return
	 */
	@Tranactional
	@PerforamanceLog
	public <E> E persistNow(E e) {
		persist(e);
		em.flush();
		return e;
	}

	/**
	 * 
	 * @param <E>
	 * @param e
	 * @return
	 */
	@Tranactional
	@PerforamanceLog
	public <E> E merge(E e) {
		em.merge(e);
		return e;
	}

	/**
	 * 
	 * @param <E>
	 * @param e
	 * @return
	 */
	@Tranactional
	@PerforamanceLog
	public <E> E mergeNow(E e) {
		merge(e);
		em.flush();
		return e;
	}

	/**
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		return em;
	}

	/**
	 * 
	 * @param <E>
	 * @param <P>
	 * @param clazz
	 * @param id
	 * @return
	 */
	@PerforamanceLog
	public <E, P> E findById(Class<E> clazz, P id) {
		return em.find(clazz, id);
	}

	/**
	 * 
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	@PerforamanceLog
	public <E> List<E> findAllByClass(Class<E> clazz) {
		return findByQuery(String.format("select obj from %s obj", clazz
				.getCanonicalName()), null);
	}

	/**
	 * 
	 * @param <E>
	 * @param query
	 * @param params
	 * @return
	 */
	@PerforamanceLog
	public <E> E findUniqueByQuery(String query, Map<String, Object> params) {
		Validate.notNull(query);
		// workaround for bug in javac compiler
		// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954
		return this.<E>findByQuery(em.createQuery(query), params, ResultStrategy.SINGLE);
	}
	
	/**
	 * 
	 * @param <E>
	 * @param query
	 * @param params
	 * @return
	 */
	@PerforamanceLog
	public <E> E findUniqueByQuery(Query query, Map<String, Object> params) {
		Validate.notNull(query);
		// workaround for bug in javac compiler
		// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954
		return this.<E>findByQuery(query, params, ResultStrategy.SINGLE);
	}
	
	/**
	 * 
	 * @param <E>
	 * @param query
	 * @param params
	 * @return
	 */
	@PerforamanceLog
	public <E> E findUniqueByNamedQuery(String query, Map<String, Object> params) {
		Validate.notNull(query);
		// workaround for bug in javac compiler
		// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954
		return this.<E> findByQuery(em.createNamedQuery(query), params,
				ResultStrategy.SINGLE);
	}

   
	/**
	 * 
	 * @param <E>
	 * @param query
	 * @param params
	 * @return
	 */
	@PerforamanceLog
	public <E> List<E> findByNamedQuery(String query, Map<String, Object> params) {
		Validate.notNull(query);
		return findByQuery(em.createNamedQuery(query), params);
	}
	/**
	 * 
	 * @param <E>
	 * @param query
	 * @param params
	 * @return
	 */
	@PerforamanceLog
	public <E> List<E> findByQuery(String query, Map<String, Object> params) {
		Validate.notNull(query);
		return findByQuery(em.createQuery(query), params);
	}

	/**
	 * 
	 * @param <E>
	 * @param query
	 * @param params
	 * @return
	 */
	@PerforamanceLog
	public <E> List<E> findByQuery(Query query, Map<String, Object> params) {
		return findByQuery(query, params, ResultStrategy.LIST);
	}

	/**
	 * 
	 * @param <E>
	 * @param query
	 * @return
	 */
	@PerforamanceLog
	public <E> List<E> findByQuery(Query query) {
		return findByQuery(query, ResultStrategy.LIST);
	}

	/**
	 * 
	 * @param <E>
	 * @param query
	 * @param strategy
	 * @return
	 */
	@PerforamanceLog
	public <E> E findByQuery(Query query, ResultStrategy strategy) {
		// workaround for bug in javac compiler
		// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954
		E result = strategy.<E> getResult(query);
		return result;
	}

	/**
	 * 
	 * @param <E>
	 * @param query
	 * @param params
	 * @param strategy
	 * @return
	 */
	@PerforamanceLog
	public <E> E findByQuery(Query query, Map<String, Object> params,
			ResultStrategy strategy) {
		// validate arguments
		Validate.notNull(query);
		Validate.notNull(strategy);
		// Set parameters if they exists
		if (params != null && !params.isEmpty()) {
			for (Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		// return a list or objects according to the strategy
		// workaround for bug in javac compiler
		// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954
		return strategy.<E> getResult(query);
	}

	/**
	 * Close any open resource
	 */
	public void close() {
		// Close if it's not null and it is open
		if (em != null && em.isOpen()) {
			em.close();
		}
	}

	/**
	 * 
	 * @author fabricio This enum facilities picking the kind of result the user
	 *         wants from making a query to the {@link EntityManager}
	 */
	public static enum ResultStrategy {
		SINGLE {
			@Override
			@SuppressWarnings("unchecked")
			public <T> T performQuery(Query query) {
				return (T) query.getSingleResult();
			}
		},
		LIST {
			@Override
			@SuppressWarnings("unchecked")
			public <T> T performQuery(Query query) {
				return (T) query.getResultList();
			}
		};

		/**
		 * 
		 * @param <T>
		 * @param query
		 * @return
		 */
		public <T> T getResult(Query query) {
			Validate.notNull(query, "El query no puede ser <null>");
			// workaround for bug in javac compiler
			// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954
			return this.<T> performQuery(query);
		}

		/**
		 * Extension point to the enums to provide the different strategy
		 * 
		 * @param <T>
		 * @param query
		 * @return
		 */
		protected abstract <T> T performQuery(Query query);
	}
}
