package org.event.manager.repository.interceptors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.event.manager.repository.Repository;
/**
 * 
 * @author fabricio
 * 
 *         This intercepter was build in order to provide transparent
 *         transaction manager for {@link Repository} objects
 */
public class TransactionInterceptor implements MethodInterceptor {

	/**
	 * Provides transaction to and only to {@link Repository} object or a subclass of
	 * {@link Repository} object. This {@link MethodInterceptor} builds a new transaction
	 * if there isn't one active or joins to an existing one
     * @param  arg0
     * @throws Throwable
	 */
    @Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		Repository dao = (Repository) arg0.getThis();
		TransactionStrategy strategy = TransactionStrategy.NEW;
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		if (transaction.isActive()) {
			strategy = TransactionStrategy.JOIN;
		}
		return strategy.invoke(arg0, dao.getEntityManager());
	}

	private static enum TransactionStrategy {
		/**
		 * Strategy join to the current transaction of the {@link EntityManager}
		 */		
		JOIN {
			@Override
			public Object invoke(MethodInvocation method, EntityManager em)
					throws Throwable {
				em.joinTransaction();
				return method.proceed();
			}
		},
		/**
		 * Strategy that builds a new transaction on the {@link EntityManager}
		 */
		NEW {
			@Override
			public Object invoke(MethodInvocation method, EntityManager em)
					throws Throwable {
				EntityTransaction transaction = em.getTransaction();
				Object result = null;
				try {
					transaction.begin();
					result = method.proceed();
					transaction.commit();
				} catch (Throwable e) {
					transaction.rollback();
					throw e;
				}

				return result;
			}
		};
		/**
		 * @see See different implementations in order to understand what they do
		 * @param method
		 * @param em
		 * @return
		 * @throws Throwable
		 */
		public abstract Object invoke(MethodInvocation method, EntityManager em)
				throws Throwable;
	}

}
