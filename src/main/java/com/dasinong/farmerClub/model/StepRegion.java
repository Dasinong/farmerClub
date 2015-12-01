package com.dasinong.farmerClub.model;

import java.io.Serializable;

//This is a mapping table. Forcefully use here to avoid too much loop in data model.
public class StepRegion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2132876417323439533L;

	private Long stepId;
	private String region;

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
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

		StepRegion sr = (StepRegion) obj;
		return (this.getStepId() == sr.getStepId() && this.getRegion().equals(sr.getRegion()));
	}

	public int hashCode() {
		return (this.stepId + this.region).hashCode();
	}
}
