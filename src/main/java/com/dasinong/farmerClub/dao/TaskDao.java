package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.Task;

public class TaskDao extends EntityHibernateDao<Task>implements ITaskDao {

	@Override
	public Task findByTaskName(String taskName) {
		List list = getHibernateTemplate().find("from Task where taskName=?", taskName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (Task) list.get(0);
	}

}
