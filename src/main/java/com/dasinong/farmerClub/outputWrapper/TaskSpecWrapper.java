package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dasinong.farmerClub.model.Step;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.TaskSpec;

public class TaskSpecWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long taskSpecId;
	private String taskSpecName;
	private SubStage subStage;
	private List<Long> stepIds = new ArrayList<Long>();
	private String type;
	// private String fitRegion;

	public TaskSpecWrapper(TaskSpec ts) {

		this.setTaskSpecId(ts.getTaskSpecId());
		this.setTaskSpecName(ts.getTaskSpecName());
		this.setSubStage(ts.getSubStage());
		if (ts.getSteps() != null) {
			for (Step s : ts.getSteps()) {
				getStepIds().add(s.getStepId());
			}
		}
		this.setType(ts.getType());
		// this.setFitRegion(ts.getFitRegion());
	}

	public Long getTaskSpecId() {
		return taskSpecId;
	}

	public void setTaskSpecId(Long taskSpecId) {
		this.taskSpecId = taskSpecId;
	}

	public String getTaskSpecName() {
		return taskSpecName;
	}

	public void setTaskSpecName(String taskSpecName) {
		this.taskSpecName = taskSpecName;
	}

	public SubStage getSubStage() {
		return subStage;
	}

	public void setSubStage(SubStage subStage) {
		this.subStage = subStage;
	}

	public List<Long> getStepIds() {
		return stepIds;
	}

	public void setStepIds(List<Long> stepIds) {
		this.stepIds = stepIds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// public String getFitRegion() {
	// return fitRegion;
	// }
	//
	// public void setFitRegion(String fitRegion) {
	// this.fitRegion = fitRegion;
	// }
}
