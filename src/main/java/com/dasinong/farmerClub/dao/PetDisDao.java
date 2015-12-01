package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.PetDis;

public class PetDisDao extends EntityHibernateDao<PetDis>implements IPetDisDao {

	@Override
	public PetDis findByPetDisName(String petDisName) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from PetDis where petDisName=?", petDisName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (PetDis) list.get(0);
	}

}
