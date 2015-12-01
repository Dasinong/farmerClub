package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.Task;

public interface ITaskDao extends IEntityDao<Task> {

	public abstract Task findByTaskName(String taskName);

}