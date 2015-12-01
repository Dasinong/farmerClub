package com.dasinong.farmerClub.outputWrapper;

import com.dasinong.farmerClub.model.Step;

public class StepWrapper {
	private Long stepId;
	private String stepName = "";
	private String fitRegion = "";

	private String description = "";
	private String picture = "";
	private int idx = -1;

	public StepWrapper(Step s) {
		this.stepId = s.getStepId();
		this.stepName = s.getStepName();
		this.fitRegion = s.getFitRegion();
		this.description = s.getDescription();
		this.picture = s.getPicture();
		this.idx = s.getIdx();
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getFitRegion() {
		return fitRegion;
	}

	public void setFitRegion(String fitRegion) {
		this.fitRegion = fitRegion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

}
