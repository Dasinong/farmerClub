package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PetDisSpec implements Serializable, Comparable<PetDisSpec> {
	private static final long serialVersionUID = 1L;

	private Long petDisSpecId;
	private String petDisSpecName = "";
	private Set<SubStage> subStages = new HashSet<SubStage>();
	private Set<Location> locations = new HashSet<Location>();
	private Set<PetSolu> petSolus = new HashSet<PetSolu>();
	private Set<Crop> crops = new HashSet<Crop>();

	private String type = "";
	private String alias = "";
	private String cropName = "";
	private int severity = 0;
	private String commonImpactonYield = "";
	private String maxImpactonYield = "";
	private String preventionorRemedy = "";
	private String indvidualorGroup = "";
	private String impactedArea = "";
	private String color = "";
	private String shape = "";
	private String description = "";

	private String sympthon = "";
	private String forms = "";
	private String habits = "";
	private String rules = "";
	private String pictureIds = "";
	private String region = "";

	public PetDisSpec() {
	}

	public PetDisSpec(String petDisSpecName) {
		this.petDisSpecName = petDisSpecName;
	}

	public PetDisSpec(String petDisSpecName, String type, String alias, int severity, String commonImpactonYield,
			String maxImpactonYield, String preventionorRemedy, String indvidualorGroup, String impactedArea,
			String color, String shape, String description) {
		super();
		this.petDisSpecName = petDisSpecName;
		this.type = type;
		this.alias = alias;
		this.severity = severity;
		this.commonImpactonYield = commonImpactonYield;
		this.maxImpactonYield = maxImpactonYield;
		this.preventionorRemedy = preventionorRemedy;
		this.indvidualorGroup = indvidualorGroup;
		this.impactedArea = impactedArea;
		this.color = color;
		this.shape = shape;
		this.description = description;
	}

	public Long getPetDisSpecId() {
		return petDisSpecId;
	}

	public void setPetDisSpecId(Long petDisSpecId) {
		this.petDisSpecId = petDisSpecId;
	}

	public String getPetDisSpecName() {
		return petDisSpecName;
	}

	public void setPetDisSpecName(String petDisSpecName) {
		this.petDisSpecName = petDisSpecName;
	}

	public Set<SubStage> getSubStages() {
		return subStages;
	}

	public void setSubStages(Set<SubStage> subStages) {
		this.subStages = subStages;
	}

	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	public Set<PetSolu> getPetSolus() {
		return petSolus;
	}

	public void setPetSolus(Set<PetSolu> petSolus) {
		this.petSolus = petSolus;
	}

	public Set<Crop> getCrops() {
		return crops;
	}

	public void setCrops(Set<Crop> crops) {
		this.crops = crops;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public String getCommonImpactonYield() {
		return commonImpactonYield;
	}

	public void setCommonImpactonYield(String commonImpactonYield) {
		this.commonImpactonYield = commonImpactonYield;
	}

	public String getMaxImpactonYield() {
		return maxImpactonYield;
	}

	public void setMaxImpactonYield(String maxImpactonYield) {
		this.maxImpactonYield = maxImpactonYield;
	}

	public String getPreventionorRemedy() {
		return preventionorRemedy;
	}

	public void setPreventionorRemedy(String preventionorRemedy) {
		this.preventionorRemedy = preventionorRemedy;
	}

	public String getIndvidualorGroup() {
		return indvidualorGroup;
	}

	public void setIndvidualorGroup(String indvidualorGroup) {
		this.indvidualorGroup = indvidualorGroup;
	}

	public String getImpactedArea() {
		return impactedArea;
	}

	public void setImpactedArea(String impactedArea) {
		this.impactedArea = impactedArea;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSympthon() {
		return sympthon;
	}

	public void setSympthon(String sympthon) {
		this.sympthon = sympthon;
	}

	public String getForms() {
		return forms;
	}

	public void setForms(String forms) {
		this.forms = forms;
	}

	public String getHabits() {
		return habits;
	}

	public void setHabits(String habits) {
		this.habits = habits;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getPictureIds() {
		return pictureIds;
	}

	public String[] getPictureIdsArray() {
		return pictureIds.split("\n");
	}

	public void setPictureIds(String pictureIds) {
		this.pictureIds = pictureIds;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public int compareTo(PetDisSpec o) {
		if (this.severity > o.severity)
			return -1;
		else if (this.severity < o.severity)
			return 1;
		else
			return 0;
	}

}
