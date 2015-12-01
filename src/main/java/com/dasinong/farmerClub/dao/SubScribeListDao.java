package com.dasinong.farmerClub.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.dasinong.farmerClub.model.SubScribeList;

public class SubScribeListDao extends EntityHibernateDao<SubScribeList>implements ISubScribeListDao {

	@Override
	public List<SubScribeList> findByOwnerId(Long oid) {
		List list = this.getSessionFactory().getCurrentSession()
				.createQuery("from SubScribeList where ownerId=:ownerId").setLong("ownerId", oid).list();
		return list;
	}

}
