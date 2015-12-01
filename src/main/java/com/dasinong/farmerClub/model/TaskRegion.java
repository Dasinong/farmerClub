package com.dasinong.farmerClub.model;

import java.io.Serializable;

//This is a mapping table. Forcefully use here to avoid too much loop in data model.
public class TaskRegion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2132876417323439533L;
	private Long taskSpecId;
	private String region;

	public Long getTaskSpecId() {
		return taskSpecId;
	}

	public void setTaskSpecId(Long taskSpecId) {
		this.taskSpecId = taskSpecId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof StepRegion)) {
			return false;
		}

		TaskRegion sr = (TaskRegion) obj;
		return (this.getTaskSpecId() == sr.getTaskSpecId() && this.getRegion().equals(sr.getRegion()));
	}

	public int hashCode() {
		return (this.taskSpecId + this.region).hashCode();
	}

}
