package com.dasinong.farmerClub.model;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long taskId;
	private Long taskSpecId;
	private Long fieldId;
	private boolean taskStatus;

	public Task() {
	}

	public Task(boolean taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Task(TaskSpec taskSpec, boolean taskStatus) {
		this.taskSpecId = taskSpec.getTaskSpecId();
		this.taskStatus = taskStatus;
	}

	public Task(Long taskSpecId, boolean taskStatus) {
		this.taskSpecId = taskSpecId;
		this.taskStatus = taskStatus;
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

	public Boolean getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Boolean taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Long getKey() {
		return this.taskSpecId;
	}

	public boolean getValue() {
		return this.taskStatus;
	}
}
