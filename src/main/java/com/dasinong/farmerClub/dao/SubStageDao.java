package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.SubStage;

public class SubStageDao extends EntityHibernateDao<SubStage>implements ISubStageDao {

	@Override
	public SubStage findBySubStageName(String subStageName) {
		List list = getHibernateTemplate().find("from SubStage where subStageName=?", subStageName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (SubStage) list.get(0);
	}
}
