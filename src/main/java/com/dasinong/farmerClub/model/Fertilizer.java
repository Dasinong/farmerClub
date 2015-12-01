package com.dasinong.farmerClub.model;

public class Fertilizer {
	private int fertilizerId;
	private String manufacturer;
	private String generalName;
	public String merchandiseName;
	public String suitableCrops;
	public String registerationId;
	public String technicalSpecs;
	public String type;

	public int getFertilizerId() {
		return fertilizerId;
	}

	public void setFertilizerId(int fertilizerId) {
		this.fertilizerId = fertilizerId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getGeneralName() {
		return generalName;
	}

	public void setGeneralName(String generalName) {
		this.generalName = generalName;
	}

	public String getMerchandiseName() {
		return merchandiseName;
	}

	public void setMerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
	}

	public String getSuitableCrops() {
		return suitableCrops;
	}

	public void setSuitableCrops(String suitableCrops) {
		this.suitableCrops = suitableCrops;
	}

	public String getRegisterationId() {
		return registerationId;
	}

	public void setRegisterationId(String registerationId) {
		this.registerationId = registerationId;
	}

	public String getTechnicalSpecs() {
		return technicalSpecs;
	}

	public void setTechnicalSpecs(String technicalSpecs) {
		this.technicalSpecs = technicalSpecs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
