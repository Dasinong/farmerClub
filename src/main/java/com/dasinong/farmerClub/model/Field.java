package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Field implements Serializable {

	private static final Long serialVersionUID = 1L;

	private Long fieldId;
	private String fieldName;
	private Crop crop;
	private User user;
	private Location location;
	private Long monitorLocationId;

	private Long currentStageID;
	private Double area;

	// MonitorLocationID
	// SoilType SoilN SoilK SoilP SoilOrganic SoilPH SoilS SoilMg SoilCa SoilFe
	// SoilMo SoilB SoilMn Soilzn SoilCu SoilCI
	private Set<SoilTestReport> soilTestReports = new TreeSet<SoilTestReport>();

	public Field() {
	};

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getCurrentStageID() {
		return currentStageID;
	}

	public void setCurrentStageID(Long currentStageID) {
		this.currentStageID = currentStageID;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Set<SoilTestReport> getSoilTestReports() {
		return soilTestReports;
	}

	public void setSoilTestReports(Set<SoilTestReport> soilTestReports) {
		this.soilTestReports = soilTestReports;
	}

	public Long getMonitorLocationId() {
		return monitorLocationId;
	}

	public void setMonitorLocationId(Long monitorLocationId) {
		this.monitorLocationId = monitorLocationId;
	}

}
