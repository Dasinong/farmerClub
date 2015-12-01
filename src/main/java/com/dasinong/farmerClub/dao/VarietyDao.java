package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.Variety;

public class VarietyDao extends EntityHibernateDao<Variety>implements IVarietyDao {

	@Override
	public Variety findByVarietyName(String varietyName) {
		List list = getHibernateTemplate().find("from Variety where varietyName=?", varietyName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (Variety) list.get(0);
	}

	@Override
	public List<Variety> findVarietysByName(String varietyName) {
		List list = getHibernateTemplate().find("from Variety where varietyName=?", varietyName);
		if (list == null) {
			return new ArrayList<Variety>();
		}
		return list;
	}

	@Override
	public List<Variety> findByCropRegion(long cropId, String suitableArea) {
		List list = getHibernateTemplate()
				.find("from Variety where cropId=? and suitableArea like '%" + suitableArea + "%'", cropId);
		return list;
	}

	@Override
	public List<Variety> findByCrop(long cropId) {
		List list = getHibernateTemplate().find("from Variety where cropId=?", cropId);
		return list;
	}

	@Override
	public List<Variety> findGenericVariety(long cropId) {
		List list = getHibernateTemplate().find("from Variety where cropId=? and varietyId>=26147 and varietyId<=26452",
				cropId);
		return list;
	}
}
