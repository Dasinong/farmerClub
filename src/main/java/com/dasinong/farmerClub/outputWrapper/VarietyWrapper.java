package com.dasinong.farmerClub.outputWrapper;

import com.dasinong.farmerClub.model.Variety;

public class VarietyWrapper {
	Long id;
	String varietyName = "";
	String subId = "";
	String registrationId = "";
	String owner = "";
	String suitableArea = "";
	String characteristics = "";
	String yieldPerformance = "";

	public VarietyWrapper(Variety v) {
		this.id = v.getVarietyId();
		this.varietyName = v.getVarietyName();
		this.subId = v.getSubId();
		this.registrationId = v.getRegisterationId();
		this.owner = v.getOwner();
		this.suitableArea = v.getSuitableArea();
		this.characteristics = v.getCharacteristics();
		this.yieldPerformance = v.getYieldPerformance();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSuitableArea() {
		return suitableArea;
	}

	public void setSuitableArea(String suitableArea) {
		this.suitableArea = suitableArea;
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

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

}
