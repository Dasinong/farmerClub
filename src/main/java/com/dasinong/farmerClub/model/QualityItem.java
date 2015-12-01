package com.dasinong.farmerClub.model;

import java.io.Serializable;

public class QualityItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long qualityItemId;
	private String qualityItemName;
	private Crop crop;

	public QualityItem() {
	}

	public QualityItem(String qualityItemName, Crop crop) {
		this.qualityItemName = qualityItemName;
		this.crop = crop;
	}

	public Long getQualityItemId() {
		return qualityItemId;
	}

	public void setQualityItemId(Long qualityItemId) {
		this.qualityItemId = qualityItemId;
	}

	public String getQualityItemName() {
		return qualityItemName;
	}

	public void setQualityItemName(String qualityItemName) {
		this.qualityItemName = qualityItemName;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

}
