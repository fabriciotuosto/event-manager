package org.event.manager.dao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.event.manager.guice.interceptors.TransactionInterceptor;

/**
 * Used by Guice configuration in order to tell Guice that any method that
 * contains this annotation must be intercept calls with
 * {@link TransactionInterceptor}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD })
public @interface Tranactional {

}
