package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.StepRegion;

public class StepRegionDao extends EntityHibernateDao<StepRegion>implements IStepRegionDao {

	@Override
	public List<StepRegion> findByStepRegion(String region) {
		List<StepRegion> list = getHibernateTemplate().find("from StepRegion where region=?", region);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list;
	}

}
