package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Variety implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long varietyId;
	private String varietyName;
	private String subId;

	private Set<Field> fields = new HashSet<Field>();
	private Set<SubStage> subStages = new HashSet<SubStage>();
	private String cropName;
	private Crop crop;
	private Map<Long, QualityItemValue> qualityItemValues;
	// Data from source file, core variables lossless information
	private String registerationId = "";
	// private String yearOfRegisteration = "";
	private int yearofReg = 0;
	private String issuedBy = "";
	// private String createdBy = "";
	private String owner = "";
	private String varietySource = ""; // 品种来源
	private boolean isTransgenic = false; // 是否转基因
	private String suitableArea = "";
	private String characteristics = ""; // 特征特性
	private String yieldPerformance = ""; // 产量表现
	// Derived entries
	private String type = "";
	private String genoType = "";
	private String maturityType = "";
	private int totalAccumulatedTempNeeded = 0;
	private double fullCycleDuration = 0;
	private int typicalYield = 0;
	private String nationalStandard = "";

	public Variety() {
	}

	public Variety(String varietyName, Crop crop) {
		this.varietyName = varietyName;
		this.crop = crop;
	}

	public String toString() {
		String output = "";
		output += "cropName: " + cropName + "\n";
		output += "varietyName: " + varietyName + "\n";
		output += "registerationId: " + registerationId + "\n";
		output += "yearOfRegisteration: " + yearofReg + "\n";
		output += "issuedBy: " + issuedBy + "\n";
		output += "owner: " + owner + "\n";
		output += "varietySource: " + varietySource + "\n";
		output += "isTransgenic: " + isTransgenic + "\n";
		output += "characteristics: " + characteristics + "\n";
		output += "yieldPerformance: " + yieldPerformance + "\n";
		output += "suitableArea: " + suitableArea + "\n";
		output += "==========================================================================";

		return output;
	}

	public Long getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Long varietyId) {
		this.varietyId = varietyId;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}

	public Set<Field> getFields() {
		return fields;
	}

	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public Set<SubStage> getSubStages() {
		return subStages;
	}

	public void setSubStages(Set<SubStage> subStages) {
		this.subStages = subStages;
	}

	public Map<Long, QualityItemValue> getQualityItemValues() {
		return qualityItemValues;
	}

	public void setQualityItemValues(Map<Long, QualityItemValue> qualityItemValues) {
		this.qualityItemValues = qualityItemValues;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGenoType() {
		return genoType;
	}

	public void setGenoType(String genoType) {
		this.genoType = genoType;
	}

	public String getMaturityType() {
		return maturityType;
	}

	public void setMaturityType(String maturityType) {
		this.maturityType = maturityType;
	}

	public String getSuitableArea() {
		return suitableArea;
	}

	public void setSuitableArea(String suitableArea) {
		this.suitableArea = suitableArea;
	}

	public int getTotalAccumulatedTempNeeded() {
		return totalAccumulatedTempNeeded;
	}

	public void setTotalAccumulatedTempNeeded(int totalAccumulatedTempNeeded) {
		this.totalAccumulatedTempNeeded = totalAccumulatedTempNeeded;
	}

	public double getFullCycleDuration() {
		return fullCycleDuration;
	}

	public void setFullCycleDuration(double fullCycleDuration) {
		this.fullCycleDuration = fullCycleDuration;
	}

	public int getTypicalYield() {
		return typicalYield;
	}

	public void setTypicalYield(int typicalYield) {
		this.typicalYield = typicalYield;
	}

	public String getNationalStandard() {
		return nationalStandard;
	}

	public void setNationalStandard(String nationalStandard) {
		this.nationalStandard = nationalStandard;
	}

	public int getYearofReg() {
		return yearofReg;
	}

	public void setYearofReg(int yearofReg) {
		this.yearofReg = yearofReg;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getRegisterationId() {
		return registerationId;
	}

	public void setRegisterationId(String registerationId) {
		this.registerationId = registerationId;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getVarietySource() {
		return varietySource;
	}

	public void setVarietySource(String varietySource) {
		this.varietySource = varietySource;
	}

	public boolean getIsTransgenic() {
		return isTransgenic;
	}

	public void setIsTransgenic(boolean isTransgenic) {
		this.isTransgenic = isTransgenic;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public String getYieldPerformance() {
		return yieldPerformance;
	}

	public void setYieldPerformance(String yieldPerformance) {
		this.yieldPerformance = yieldPerformance;
	}

}
