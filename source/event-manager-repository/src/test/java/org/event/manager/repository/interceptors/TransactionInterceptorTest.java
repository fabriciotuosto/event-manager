package org.event.manager.repository.interceptors;

import org.aopalliance.intercept.MethodInvocation;
import org.event.manager.repository.Repository;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransactionInterceptorTest {

    private static TransactionInterceptor interceptor;
    private EntityManager em;
    private EntityTransaction trx;
    private Repository repository;
    private MethodInvocation mockInvokation;

    @BeforeClass
    public static void createInterceptor() {
        interceptor = new TransactionInterceptor();
    }

    @Before
    public void prepareMocks() {
        em = mock(EntityManager.class);
        trx = mock(EntityTransaction.class);
        repository = mock(Repository.class);
        mockInvokation = mock(MethodInvocation.class);

        when(mockInvokation.getThis()).thenReturn(repository);
        when(repository.getEntityManager()).thenReturn(em);
        when(em.getTransaction()).thenReturn(trx);

    }

    public void verified_no_further_interactions() {
        verifyNoMoreInteractions(mockInvokation, em, repository, trx);
    }

    @Test
    public void new_transaction_no_exceptions() throws Throwable {
        when(trx.isActive()).thenReturn(false);
        interceptor.invoke(mockInvokation);

        InOrder inOrder = inOrder(mockInvokation, repository, em, trx);
        inOrder.verify(mockInvokation).getThis();
        inOrder.verify(repository).getEntityManager();
        inOrder.verify(em).getTransaction();
        inOrder.verify(trx).isActive();
        inOrder.verify(repository).getEntityManager();
        inOrder.verify(em).getTransaction();
        inOrder.verify(trx).begin();
        inOrder.verify(mockInvokation).proceed();
        inOrder.verify(trx).commit();
        verified_no_further_interactions();
    }

    @Test
    public void join_transaction_no_exceptions() throws Throwable {
        when(trx.isActive()).thenReturn(true);
        interceptor.invoke(mockInvokation);

        InOrder inOrder = inOrder(mockInvokation, repository, em, trx);
        inOrder.verify(mockInvokation).getThis();
        inOrder.verify(repository).getEntityManager();
        inOrder.verify(em).getTransaction();
        inOrder.verify(trx).isActive();
        inOrder.verify(repository).getEntityManager();
        inOrder.verify(em).joinTransaction();
        inOrder.verify(mockInvokation).proceed();
        verified_no_further_interactions();
    }

    @Test(expected = Throwable.class)
    public void new_transaction_throw_exceptions() throws Throwable {
        when(trx.isActive()).thenReturn(false);
        when(mockInvokation.proceed()).thenThrow(new Throwable());
        try {
            interceptor.invoke(mockInvokation);
            fail();
        } finally {
            verify(mockInvokation).getThis();
            verify(repository, times(2)).getEntityManager();
            verify(em, times(2)).getTransaction();
            verify(trx).isActive();
            verify(trx).begin();
            verify(mockInvokation).proceed();

            verify(trx).rollback();
            verified_no_further_interactions();
        }
    }
}
