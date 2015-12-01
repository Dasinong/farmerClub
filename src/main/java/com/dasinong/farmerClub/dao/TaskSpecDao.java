package com.dasinong.farmerClub.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dasinong.farmerClub.model.TaskSpec;

public class TaskSpecDao extends EntityHibernateDao<TaskSpec>implements ITaskSpecDao {

	@Override
	public TaskSpec findByTaskSpecName(String taskSpecName) {
		List list = this.getSessionFactory().getCurrentSession()
				.createQuery("from TaskSpec where taskSpecName=:specName").setString("specName", taskSpecName).list();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (TaskSpec) list.get(0);
	}

	@Override
	public List<TaskSpec> findBySubstage(Long subStageId) {
		List list = this.getSessionFactory().getCurrentSession()
				.createQuery("from TaskSpec where subStageId=:subStageId").setLong("subStageId", subStageId).list();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list;
	}

}
