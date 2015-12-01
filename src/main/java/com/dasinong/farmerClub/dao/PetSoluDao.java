package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.PetSolu;

public class PetSoluDao extends EntityHibernateDao<PetSolu>implements IPetSoluDao {

	@Override
	public PetSolu findByPetSoluName(String petSoluName) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from PetSolu where petSoluName=?", petSoluName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (PetSolu) list.get(0);
	}

}
