package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.VarietyBrowse;

public class VarietyBrowseDao extends EntityHibernateDao<VarietyBrowse>implements IVarietyBrowseDao {

	@Override
	public VarietyBrowse findByVarietyBrowseName(String varietyBrowseName) {
		List list = getHibernateTemplate().find("from VarietyBrowse where varietyBrowseName=?", varietyBrowseName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (VarietyBrowse) list.get(0);
	}

	@Override
	public List<VarietyBrowse> findByCropId(Long cropId) {
		List list = getHibernateTemplate().find("from VarietyBrowse where cropId=?", cropId);
		if (list == null) {
			return new ArrayList<VarietyBrowse>();
		}
		return list;
	}
}
