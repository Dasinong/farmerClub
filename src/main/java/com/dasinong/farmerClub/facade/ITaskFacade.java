package com.dasinong.farmerClub.facade;

import java.util.HashMap;

public interface ITaskFacade {

	public abstract Object getAllTask(Long fieldId);

	public abstract Object getCurrentTask(Long fieldId, Long currentStageId);

	public abstract Object updateTasks(Long fieldId, HashMap<Long, Boolean> tasks);

	public abstract Object updateTask(Long fieldId, Long taskid, boolean taskStatus);

}