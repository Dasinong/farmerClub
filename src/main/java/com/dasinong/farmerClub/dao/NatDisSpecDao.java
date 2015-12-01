package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.NatDisSpec;
import com.dasinong.farmerClub.model.Task;

public class NatDisSpecDao extends EntityHibernateDao<NatDisSpec>implements INatDisSpecDao {

	@Override
	public NatDisSpec findByNatDisSpecName(String natDisSpecName) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from NatDisSpec where natDisSpecName=?", natDisSpecName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (NatDisSpec) list.get(0);
	}

}
