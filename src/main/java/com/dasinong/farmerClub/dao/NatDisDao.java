package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.NatDis;

public class NatDisDao extends EntityHibernateDao<NatDis>implements INatDisDao {

	@Override
	public NatDis findByNatDisName(String natDisName) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from NatDis where natDisName=?", natDisName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (NatDis) list.get(0);
	}

}
