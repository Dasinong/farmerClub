package com.dasinong.farmerClub.model;

import java.io.Serializable;

import com.dasinong.farmerClub.util.StringUtils;

public class Step implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long stepId;
	private String stepName = "";
	private TaskSpec taskSpec;
	private String fitRegion = "";

	private String description = "";
	private String picture = "";
	private int idx = -1;

	public Step() {
	}

	public Step(String stepName, TaskSpec taskSpec) {
		this.stepName = stepName;
		this.taskSpec = taskSpec;
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

	public TaskSpec getTaskSpec() {
		return taskSpec;
	}

	public void setTaskSpec(TaskSpec taskSpec) {
		this.taskSpec = taskSpec;
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
	
	public String getThumbnailPicture() {
		if (picture == null || "".equals(picture)) 
			return "";
		
		String[] parts = picture.split("/");
		parts[parts.length - 1] = "thumb_" + parts[parts.length - 1];
		return StringUtils.join("/", parts);
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

	public String getFitRegion() {
		return fitRegion;
	}

	public void setFitRegion(String fitRegion) {
		this.fitRegion = fitRegion;
	}

}
