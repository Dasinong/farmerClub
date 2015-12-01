package com.dasinong.farmerClub.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.Step;

public class StepDao extends EntityHibernateDao<Step>implements IStepDao {

	@Override
	public Step findByStepName(String stepName) {
		List list = getHibernateTemplate().find("from Step where stepName=?", stepName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (Step) list.get(0);
	}

	@Override
	public List<Step> findByTaskSpecId(Long taskSpecId) {
		List list = getHibernateTemplate().find("from Step where taskSpecId=?", taskSpecId);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (List<Step>) list;
	}

}
