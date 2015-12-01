package com.dasinong.farmerClub.dao;

import java.util.List;

/**
 * 
 * @author xiahonggao
 *
 *         An entity is something that can be saved, updated, deleted and found
 *         by an unique id.
 */
public interface IEntityDao<T> {

	public void save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public T findById(Long id);

	public List<T> findAll();
}
