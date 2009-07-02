package org.event.manager.dao;

import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

public class JpaQueryBuilder
{

	private final EntityManager em;
	private Map<String,Object> params;
	private static final String QUERY_FORMAT = "SELECT o FROM %s o %s";
	private StringBuilder builder;
	private String className;

	@Inject
	public JpaQueryBuilder(EntityManager em,Class<?> klass) {
		this.em = em;
		params = Maps.newHashMap();
		builder = new StringBuilder();
		setClass(klass);
	}

	public Query build()
	{
		String queryString = String.format(QUERY_FORMAT,className,builder.toString());
		System.out.println(queryString);
		Query query = em.createQuery(queryString);
		for(Entry<String,Object> param : params.entrySet())
		{
			query.setParameter(param.getKey(), param.getValue());
		}
		return query;
	}

	private JpaQueryBuilder setClass(Class<?> clazz)
	{
		Validate.notNull(clazz);
		className = clazz.getSimpleName();
		return this;
	}

	public JpaQueryBuilder like(String attribute, Object value) {
		and();
		builder.append("o.")
			.append(attribute)
			.append(" LIKE ")
		    .append(":"+attribute);
		addParams(attribute,"%"+value+"%");
		return this;
	}

	private void addParams(String key, Object value) {
		if (! params.containsKey(key))
		{
			params.put(key, value);
		}
	}

	public JpaQueryBuilder and()
	{
		where();
		if (! builder.toString().equals("\nWHERE "))
			builder.append("\n AND ");
		return this;
	}

	private JpaQueryBuilder where()
	{
		if (builder.length() == 0)
		{
			builder = new StringBuilder("\nWHERE ");
		}
		return this;
	}

	public JpaQueryBuilder or()
	{
		where();
		if (! builder.toString().equals("\nWHERE "))
			builder.append("\n OR ");
		return this;
	}

	public JpaQueryBuilder equals(String attribute, Object value) {
		where();
		and();
		builder.append("o."+attribute+" = :"+attribute);
		addParams(attribute, value);
		return this;
	}
}

