package org.event.manager.guice.interceptors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.event.manager.dao.Dao;

/**
 *
 * @author fabricio
 *
 *	This intercepter was build in order to provide transparent
 *	transaction manager for {@link Dao} objects
 */
public class TransactionInterceptor  implements MethodInterceptor
{

	/**
	 *
	 */
	public Object invoke(MethodInvocation arg0) throws Throwable {
		Dao dao = (Dao) arg0.getThis();
		TransactionStrategy strategy = TransactionStrategy.NEW;
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		if ( transaction.isActive()){
			strategy = TransactionStrategy.JOIN;
		}
		return strategy.invoke(arg0, dao.getEntityManager());
	}

	private static enum TransactionStrategy
	{
		JOIN {
			@Override
			public Object invoke(MethodInvocation method, EntityManager em) throws Throwable
			{
				em.joinTransaction();
				return method.proceed();
			}
		},

		NEW {
			@Override
			public Object invoke(MethodInvocation method, EntityManager em)  throws Throwable
			{
				EntityTransaction transaction = em.getTransaction();
				Object result = null;
				try{
					transaction.begin();
					result = method.proceed();
					transaction.commit();
				}catch(Throwable e)
				{
					transaction.rollback();
					throw e;
				}

				return result;
			}
		};

		public abstract Object invoke(MethodInvocation method,EntityManager em) throws Throwable;
	}

}

