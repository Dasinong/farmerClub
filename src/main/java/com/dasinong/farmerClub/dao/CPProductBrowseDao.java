package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.CPProductBrowse;

public class CPProductBrowseDao extends EntityHibernateDao<CPProductBrowse>implements ICPProductBrowseDao {

	@Override
	public List<CPProductBrowse> findByModel(String model) {
		List list = getHibernateTemplate().find("from CPProductBrowse where model=?", model);
		if (list == null) {
			return new ArrayList<CPProductBrowse>();
		}
		return list;
	}

	@Override
	public List<CPProductBrowse> findByModelAndManufacturer(String model, String manufacturer) {
		List list = getHibernateTemplate().find("from CPProductBrowse where model=? and manufacturer like '%"+manufacturer+"%'", model);
		if (list == null) {
			return new ArrayList<CPProductBrowse>();
		}
		return list;
	}
	
	
}
