package com.commafeed.backend.dao;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.SpecHints;

import com.commafeed.backend.model.AbstractModel;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import io.dropwizard.hibernate.AbstractDAO;

public abstract class GenericDAO<T extends AbstractModel> extends AbstractDAO<T> {

	protected GenericDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	protected JPAQueryFactory query() {
		return new JPAQueryFactory(currentSession());
	}

	protected JPAUpdateClause updateQuery(EntityPath<T> entityPath) {
		return new JPAUpdateClause(currentSession(), entityPath);
	}

	protected JPADeleteClause deleteQuery(EntityPath<T> entityPath) {
		return new JPADeleteClause(currentSession(), entityPath);
	}

	public void saveOrUpdate(T model) {
		persist(model);
	}

	public void saveOrUpdate(Collection<T> models) {
		models.forEach(this::persist);
	}

	public void update(T model) {
		currentSession().merge(model);
	}

	public T findById(Long id) {
		return get(id);
	}

	public void delete(T object) {
		if (object != null) {
			currentSession().remove(object);
		}
	}

	public int delete(Collection<T> objects) {
		objects.forEach(this::delete);
		return objects.size();
	}

	protected void setTimeout(JPAQuery<?> query, int timeoutMs) {
		if (timeoutMs > 0) {
			query.setHint(SpecHints.HINT_SPEC_QUERY_TIMEOUT, timeoutMs);
		}
	}

}
