package com.dasinong.farmerClub.model;

import java.io.Serializable;

public class VarietyBrowse implements Serializable {

	private static final long serialVersionUID = 1L;
	private long cropId;
	private long varietyId;
	private String varietyName;
	private String varietyNamePY;

	public long getCropId() {
		return cropId;
	}

	public void setCropId(long cropId) {
		this.cropId = cropId;
	}

	public long getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(long varietyId) {
		this.varietyId = varietyId;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}

	public String getVarietyNamePY() {
		return varietyNamePY;
	}

	public void setVarietyNamePY(String varietyNamePY) {
		this.varietyNamePY = varietyNamePY;
	}

}
