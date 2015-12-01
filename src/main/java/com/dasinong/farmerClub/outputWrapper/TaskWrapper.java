package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ITaskSpecDao;
import com.dasinong.farmerClub.model.Task;
import com.dasinong.farmerClub.model.TaskSpec;

//This class wraps what supposed to be seen by the view
public class TaskWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	private ITaskSpecDao taskSpecDao;

	private Long taskId;
	private Long taskSpecId;
	private Long fieldId;
	private String taskSpecName;
	private boolean taskStatus;
	private Long subStageId;
	private String subStageName;
	private String stageName;

	public TaskWrapper(Task task, ITaskSpecDao taskSpecDao) {
		this.setTaskId(task.getTaskId());
		this.setTaskSpecId(task.getTaskSpecId());
		this.setFieldId(task.getFieldId());
		this.setTaskStatus(task.getTaskStatus());
		this.taskSpecDao = taskSpecDao;
		TaskSpec taskspec = taskSpecDao.findById(task.getTaskSpecId());
		this.setTaskSpecName(taskspec.getTaskSpecName());
		this.setSubStageId(taskspec.getSubStage().getSubStageId());
		this.setSubStageName(taskspec.getSubStage().getSubStageName());
		this.setStageName(taskspec.getSubStage().getStageName());
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskSpecId() {
		return taskSpecId;
	}

	public void setTaskSpecId(Long taskSpecId) {
		this.taskSpecId = taskSpecId;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getTaskSpecName() {
		return taskSpecName;
	}

	public void setTaskSpecName(String taskSpecName) {
		this.taskSpecName = taskSpecName;
	}

	public boolean isTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(boolean taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Long getSubStageId() {
		return subStageId;
	}

	public void setSubStageId(Long subStageId) {
		this.subStageId = subStageId;
	}

	public String getSubStageName() {
		return subStageName;
	}

	public void setSubStageName(String subStageName) {
		this.subStageName = subStageName;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

}
