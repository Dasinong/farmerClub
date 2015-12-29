package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SubStage implements Serializable, Comparable<SubStage> {
	private static final long serialVersionUID = 1L;

	private Long subStageId;
	private String subStageName;
	private String stageName;
	private Set<Crop> crops = new HashSet<Crop>();
	private Set<PetDisSpec> petDisSpecs = new HashSet<PetDisSpec>();
	private Set<TaskSpec> taskSpecs = new HashSet<TaskSpec>();

	private int triggerAccumulatedTemp = -100;
	private int reqMinTemp = -100;
	private int reqAvgTemp = -100;
	private int maxFieldHumidity = -1;
	private int minFieldHumidity = -1;
	private double durationLow = 0;
	private double durationMid = 0;
	private double durationHigh = 0;

	public SubStage() {
	}

	public SubStage(String subStageName, String stageName) {
		this.subStageName = subStageName;
		this.stageName = stageName;
	}

	public double getDurationLow() {
		return durationLow;
	}

	public void setDurationLow(double durationLow) {
		this.durationLow = durationLow;
	}

	public double getDurationMid() {
		return durationMid;
	}

	public void setDurationMid(double durationMid) {
		this.durationMid = durationMid;
	}

	public double getDurationHigh() {
		return durationHigh;
	}

	public void setDurationHigh(double durationHigh) {
		this.durationHigh = durationHigh;
	}

	public Long getSubStageId() {
		return subStageId;
	}

	public void setSubStageId(Long subStageId) {
		this.subStageId = subStageId;
	}

	public String getSubStageName() {
		return subStageName;
	}

	public void setSubStageName(String subStageName) {
		this.subStageName = subStageName;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Set<Crop> getCrops() {
		return crops;
	}

	public void setCrops(Set<Crop> crops) {
		this.crops = crops;
	}

	public Set<TaskSpec> getTaskSpecs() {
		return taskSpecs;
	}

	public void setTaskSpecs(Set<TaskSpec> taskSpecs) {
		this.taskSpecs = taskSpecs;
	}

	public Set<PetDisSpec> getPetDisSpecs() {
		return petDisSpecs;
	}

	public void setPetDisSpecs(Set<PetDisSpec> petDisSpecs) {
		this.petDisSpecs = petDisSpecs;
	}

	public int getTriggerAccumulatedTemp() {
		return triggerAccumulatedTemp;
	}

	public void setTriggerAccumulatedTemp(int triggerAccumulatedTemp) {
		this.triggerAccumulatedTemp = triggerAccumulatedTemp;
	}

	public int getReqMinTemp() {
		return reqMinTemp;
	}

	public void setReqMinTemp(int reqMinTemp) {
		this.reqMinTemp = reqMinTemp;
	}

	public int getReqAvgTemp() {
		return reqAvgTemp;
	}

	public void setReqAvgTemp(int reqAvgTemp) {
		this.reqAvgTemp = reqAvgTemp;
	}

	public int getMaxFieldHumidity() {
		return maxFieldHumidity;
	}

	public void setMaxFieldHumidity(int maxFieldHumidity) {
		this.maxFieldHumidity = maxFieldHumidity;
	}

	public int getMinFieldHumidity() {
		return minFieldHumidity;
	}

	public void setMinFieldHumidity(int minFieldHumidity) {
		this.minFieldHumidity = minFieldHumidity;
	}

	@Override
	public int compareTo(SubStage target) {
		if (this.subStageId > target.subStageId)
			return 1;
		else
			return -1;
	}

}
