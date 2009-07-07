package org.event.manager.dao;

import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * Simple class to start building some dynamic query in an object oriented way
 * without needed to use hibernate criteria api, in order to abstract from any
 * implementation of jpa-api
 * 
 * @author tuosto
 * 
 */
public class JpaQueryBuilder {

	private final EntityManager em;
	private final Map<String, Object> params;
	private static final String QUERY_FORMAT = "SELECT o FROM %s o %s";
	private StringBuilder builder;
	private String className;

	@Inject
	public JpaQueryBuilder(EntityManager em, Class<?> klass) {
		this.em = em;
		params = Maps.newHashMap();
		builder = new StringBuilder();
		setClass(klass);
	}

	/**
	 * 
	 * @return {@link Query} with parameters set already prepared to be called
	 *         within an {@link EntityManager}
	 */
	public Query build() {
		String queryString = String.format(QUERY_FORMAT, className, builder
				.toString());
		System.out.println(queryString);
		Query query = em.createQuery(queryString);
		for (Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		return query;
	}

	/**
	 * Set on which kind of class the query will be performed
	 * 
	 * @param clazz
	 * @return
	 */
	private JpaQueryBuilder setClass(Class<?> clazz) {
		Validate.notNull(clazz);
		className = clazz.getSimpleName();
		return this;
	}

	/**
	 * Build like sentence no need to add '%' wild car
	 * 
	 * @param attribute
	 * @param value
	 * @return
	 */
	public JpaQueryBuilder like(String attribute, Object value) {
		and();
		builder.append("o.").append(attribute).append(" LIKE ").append(
				":" + attribute);
		addParams(attribute, "%" + value + "%");
		return this;
	}

	/**
	 * added parameters to the query
	 * 
	 * @param key
	 *            the name of object the attribute
	 * @param value
	 *            the expected value of the attribute
	 */
	private void addParams(String key, Object value) {
		if (!params.containsKey(key)) {
			params.put(key, value);
		}
	}

	/**
	 * adds an AND statement
	 * 
	 * @return the current instance of the builder so client code can use
	 *         chaining methods
	 */
	public JpaQueryBuilder and() {
		where();
		if (!builder.toString().equals("\nWHERE ")) {
			builder.append("\n AND ");
		}
		return this;
	}

	/**
	 * adds a WHERE statement
	 * 
	 * @return the current instance of the builder so client code can use
	 *         chaining methods
	 */
	private JpaQueryBuilder where() {
		if (builder.length() == 0) {
			builder = new StringBuilder("\nWHERE ");
		}
		return this;
	}

	/**
	 * adds a WHERE statement
	 * 
	 * @return the current instance of the builder so client code can use
	 *         chaining methods
	 */
	public JpaQueryBuilder or() {
		where();
		if (!builder.toString().equals("\nWHERE ")) {
			builder.append("\n OR ");
		}
		return this;
	}

	/**
	 * 
	 * @param attribute
	 *            the name of the attribute to be compared
	 * @param value
	 *            the expected value of the attribute
	 * @return the current instance of the builder so client code can use
	 *         chaining methods
	 */
	public JpaQueryBuilder equals(String attribute, Object value) {
		where();
		and();
		builder.append("o." + attribute + " = :" + attribute);
		addParams(attribute, value);
		return this;
	}
}
