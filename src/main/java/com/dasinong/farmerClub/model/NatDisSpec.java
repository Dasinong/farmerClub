package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class NatDisSpec implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long natDisSpecId;
	private String natDisSpecName;
	private Set<SubStage> subStages = new HashSet<SubStage>();
	private Set<Location> locations = new HashSet<Location>();
	private Set<Crop> crops = new HashSet<Crop>();

	private String solution;
	private String alertInfo;

	public NatDisSpec() {
	}

	public NatDisSpec(String natDisSpecName) {
		this.natDisSpecName = natDisSpecName;
	}

	public NatDisSpec(String natDisSpecName, String solution, String alertInfo) {
		super();
		this.natDisSpecName = natDisSpecName;
		this.solution = solution;
		this.alertInfo = alertInfo;
	}

	public Long getNatDisSpecId() {
		return natDisSpecId;
	}

	public void setNatDisSpecId(Long natDisSpecId) {
		this.natDisSpecId = natDisSpecId;
	}

	public String getNatDisSpecName() {
		return natDisSpecName;
	}

	public void setNatDisSpecName(String natDisSpecName) {
		this.natDisSpecName = natDisSpecName;
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

	public Set<Crop> getCrops() {
		return crops;
	}

	public void setCrops(Set<Crop> crops) {
		this.crops = crops;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getAlertInfo() {
		return alertInfo;
	}

	public void setAlertInfo(String alertInfo) {
		this.alertInfo = alertInfo;
	}

}
