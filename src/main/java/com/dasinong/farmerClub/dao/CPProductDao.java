package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.CPProduct;

public class CPProductDao extends EntityHibernateDao<CPProduct>implements ICPProductDao {

	@Override
	public CPProduct findByRegisterationId(String registerationId) {
		List list = getHibernateTemplate().find("from CPProduct where registerationId=?", registerationId);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (CPProduct) list.get(0);
	}

	@Override
	public List<CPProduct> findByIngredient(String ingredient) {
		List list = getHibernateTemplate().find("from CPProduct where activeIngredient=?", ingredient);
		if (list == null) {
			return new ArrayList<CPProduct>();
		}
		return list;
	}
	
	@Override
	public List<CPProduct> findByModelAndManufacturer(String model, String manufacturer) {
		List list = getHibernateTemplate().find("from CPProduct where model=? and manufacturer like '%"+manufacturer+"%'", model);
		if (list == null) {
			return new ArrayList<CPProduct>();
		}
		return list;
	}

}