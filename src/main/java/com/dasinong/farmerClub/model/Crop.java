package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Crop implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long cropId;
	private String cropName;
	private String iconUrl;
	private Set<Variety> varieties = new HashSet<Variety>();
	private Set<SubStage> subStages = new HashSet<SubStage>();
	private Set<QualityItem> qualityItems = new HashSet<QualityItem>();
	private Set<PetDisSpec> petDisSpecs = new HashSet<PetDisSpec>();

	private String type = "";

	public Crop() {
	}

	public Crop(String cropName) {
		this.cropName = cropName;
	}

	public Long getCropId() {
		return cropId;
	}

	public void setCropId(Long cropId) {
		this.cropId = cropId;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	
	public String getIconUrl() {
		return this.iconUrl;
	}
	
	public void setIconUrl(String url) {
		this.iconUrl = url;
	}

	public Set<Variety> getVarieties() {
		return varieties;
	}

	public void setVarieties(Set<Variety> varieties) {
		this.varieties = varieties;
	}

	public Set<QualityItem> getQualityItems() {
		return qualityItems;
	}

	public void setQualityItems(Set<QualityItem> qualityItems) {
		this.qualityItems = qualityItems;
	}
	
	public void setSubStages(Set<SubStage> subStages) {
		this.subStages = subStages;
	}

	public Set<SubStage> getSubStages() {
		return subStages;
	}

	public Set<PetDisSpec> getPetDisSpecs() {
		return petDisSpecs;
	}

	public void setPetDisSpecs(Set<PetDisSpec> petDisSpecs) {
		this.petDisSpecs = petDisSpecs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
