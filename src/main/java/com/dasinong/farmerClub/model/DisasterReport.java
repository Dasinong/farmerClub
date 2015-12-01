package com.dasinong.farmerClub.model;

import java.io.Serializable;

public class DisasterReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long disasterReportId;
	private String cropName;
	private Long fieldId;
	private String disasterType;
	private String disasterName;
	private String affectedArea;
	private String eruptionTime;
	private String disasterDist; // disaster distribution 灾害分布
	private String fieldOperations;
	private String imageFilenames; // 存放图片文件名，多个文件名用逗号分隔，最多六张图片

	public DisasterReport() {
	}

	public DisasterReport(String cropName, Long fieldId, String disasterType, String disasterName, String affectedArea,
			String eruptionTime, String disasterDist, String fieldOperations, String imageFilenames) {
		this.cropName = cropName;
		this.fieldId = fieldId;
		this.disasterType = disasterType;
		this.disasterName = disasterName;
		this.affectedArea = affectedArea;
		this.eruptionTime = eruptionTime;
		this.disasterDist = disasterDist;
		this.fieldOperations = fieldOperations;
		this.imageFilenames = imageFilenames;
	}

	public Long getDisasterReportId() {
		return this.disasterReportId;
	}

	public void setDisasterReportId(Long disasterReportId) {
		this.disasterReportId = disasterReportId;
	}

	public String getCropName() {
		return this.cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public Long getfieldId() {
		return this.fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getDisasterType() {
		return this.disasterType;
	}

	public void setDisasterType(String disasterType) {
		this.disasterType = disasterType;
	}

	public String getDisasterName() {
		return this.disasterName;
	}

	public void setDisasterName(String disasterName) {
		this.disasterName = disasterName;
	}

	public String getAffectedArea() {
		return this.affectedArea;
	}

	public void setAffectedArea(String affectedArea) {
		this.affectedArea = affectedArea;
	}

	public String getEruptionTime() {
		return this.eruptionTime;
	}

	public void setEruptionTime(String eruptionTime) {
		this.eruptionTime = eruptionTime;
	}

	public String getDisasterDist() {
		return this.disasterDist;
	}

	public void setDisasterDist(String disasterDist) {
		this.disasterDist = disasterDist;
	}

	public String getFieldOperations() {
		return this.fieldOperations;
	}

	public void setFieldOperations(String fieldOperations) {
		this.fieldOperations = fieldOperations;
	}

	public String getImageFilenames() {
		return this.imageFilenames;
	}

	public void setImageFilenames(String imageFilenames) {
		this.imageFilenames = imageFilenames;
	}

}