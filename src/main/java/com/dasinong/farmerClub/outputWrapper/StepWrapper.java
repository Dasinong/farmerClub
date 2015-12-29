package com.dasinong.farmerClub.outputWrapper;

import com.dasinong.farmerClub.model.Step;

public class StepWrapper {
	public Long stepId;
	public String stepName = "";
	public String fitRegion = "";

	public String description = "";
	public String picture = "";
	public String thumbnailPicture = "";
	public int idx = -1;

	public StepWrapper(Step s) {
		this.stepId = s.getStepId();
		this.stepName = s.getStepName();
		this.fitRegion = s.getFitRegion();
		this.description = s.getDescription();
		this.picture = s.getPicture();
		this.thumbnailPicture = s.getThumbnailPicture();
		this.idx = s.getIdx();
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
