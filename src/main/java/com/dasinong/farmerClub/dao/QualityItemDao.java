package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.QualityItem;

public class QualityItemDao extends EntityHibernateDao<QualityItem>implements IQualityItemDao {

	@Override
	public QualityItem findByQualityItemName(String qualityItemName) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from QualityItem where qualityItemName=?", qualityItemName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (QualityItem) list.get(0);
	}

}
