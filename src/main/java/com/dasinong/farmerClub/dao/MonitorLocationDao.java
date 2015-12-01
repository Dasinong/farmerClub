package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.MonitorLocation;

public class MonitorLocationDao extends EntityHibernateDao<MonitorLocation>implements IMonitorLocationDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.dao.ILocationDao#findByLocationName(java.lang.
	 * String)
	 */
	@Override
	public MonitorLocation findByCode(long code) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from MonitorLocation where code=?", code);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (MonitorLocation) list.get(0);
	}

}
