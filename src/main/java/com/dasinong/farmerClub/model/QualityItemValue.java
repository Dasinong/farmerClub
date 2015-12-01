package com.dasinong.farmerClub.model;

//Issue with topology of this class/table, requires manual load on system start.
import java.io.Serializable;

public class QualityItemValue implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long qualityItemValueId;
	private Long varietyId;
	private Long qualityItemId;
	private String itemValue;

	public QualityItemValue() {
	}

	public QualityItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public QualityItemValue(Long qualityItemId, String itemValue) {
		this.qualityItemId = qualityItemId;
		this.itemValue = itemValue;
	}

	public Long getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Long varietyId) {
		this.varietyId = varietyId;
	}

	public Long getQualityItemId() {
		return qualityItemId;
	}

	public void setQualityItemId(Long qualityItemId) {
		this.qualityItemId = qualityItemId;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public Long getQualityItemValueId() {
		return qualityItemValueId;
	}

	public void setQualityItemValueId(Long qualityItemValueId) {
		this.qualityItemValueId = qualityItemValueId;
	}

	public Long getKey() {
		return this.qualityItemId;
	}

	public String getValue() {
		return this.itemValue;
	}

}
