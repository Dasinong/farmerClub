package com.dasinong.farmerClub.dao;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * @author xiahonggao
 *
 *         Hibernate implementation of IEntityDao
 */
public class EntityHibernateDao<T> extends HibernateDaoSupport implements IEntityDao<T> {

	protected Class<T> entityType;

	public EntityHibernateDao() {
		this.entityType = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]);
	}

	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public T findById(Long id) {
		return this.getHibernateTemplate().get(this.entityType, id);
	}

	@Override
	public List<T> findAll() {
		return this.getHibernateTemplate().find("from " + this.entityType.getName());
	}


}
