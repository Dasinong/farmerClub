package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.PetDisSpecBrowse;

public class PetDisSpecBrowseDao extends EntityHibernateDao<PetDisSpecBrowse>implements IPetDisSpecBrowseDao {

	@Override
	public List<PetDisSpecBrowse> findByType(String type) {
		// List list = getHibernateTemplate().find("from PetDisSpecBrowse where
		// type=?",type);
		List list = null;
		if (type.contains("病")) {
			list = getHibernateTemplate().find("from PetDisSpecBrowse where type like '%病%'");
		} else if (type.contains("虫")) {
			list = getHibernateTemplate().find("from PetDisSpecBrowse where type like '%虫%'");
		} else if (type.contains("草")) {
			list = getHibernateTemplate().find("from PetDisSpecBrowse where type like '%草%'");
		}
		if (list == null) {
			return new ArrayList<PetDisSpecBrowse>();
		}
		return list;
	}

	@Override
	public List<PetDisSpecBrowse> findByCropIdAndType(Long cropId, String type) {
		List list = getHibernateTemplate().find("from PetDisSpecBrowse where cropId = ? and type like '%" + type + "%'",
				cropId);
		if (list == null) {
			return new ArrayList<PetDisSpecBrowse>();
		}

		return list;
	}
}
