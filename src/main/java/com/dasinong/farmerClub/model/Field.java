package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Field implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long fieldId;
	private String fieldName;
	private boolean isActive;
	private Variety variety;
	private User user;
	private Location location;
	private long monitorLocationId;
	private Map<Long, Task> tasks;
	private Map<Long, PetDis> petDiss;
	private Map<Long, NatDis> natDiss;

	private long currentStageID;
	private Date lastForceSet;
	private boolean isJustSet;
	private double dayOffset;
	private Date startDate;
	private Date endDate;
	private long yield;

	private double area; // in mu
	private boolean seedortrans;

	private boolean workable;
	private boolean sprayable;
	private int dayToHarvest;

	// MonitorLocationID
	// SoilType SoilN SoilK SoilP SoilOrganic SoilPH SoilS SoilMg SoilCa SoilFe
	// SoilMo SoilB SoilMn Soilzn SoilCu SoilCI
	private Set<SoilTestReport> soilTestReports = new TreeSet<SoilTestReport>();

	public Field() {
	};

	public Field(String fieldName, Variety variety, User user, Location location) {
		this.fieldName = fieldName;
		this.variety = variety;
		this.user = user;
		this.location = location;
	}

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

	public Variety getVariety() {
		return variety;
	}

	public void setVariety(Variety variety) {
		this.variety = variety;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Map<Long, Task> getTasks() {
		return tasks;
	}

	public void setTasks(Map<Long, Task> tasks) {
		this.tasks = tasks;
	}

	public Map<Long, PetDis> getPetDiss() {
		return petDiss;
	}

	public void setPetDiss(Map<Long, PetDis> petDiss) {
		this.petDiss = petDiss;
	}

	public Map<Long, NatDis> getNatDiss() {
		return natDiss;
	}

	public void setNatDiss(Map<Long, NatDis> natDiss) {
		this.natDiss = natDiss;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getCurrentStageID() {
		return currentStageID;
	}

	public void setCurrentStageID(long currentStageID) {
		this.currentStageID = currentStageID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getYield() {
		return yield;
	}

	public void setYield(long yield) {
		this.yield = yield;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public boolean isSeedortrans() {
		return seedortrans;
	}

	public void setSeedortrans(boolean seedortrans) {
		this.seedortrans = seedortrans;
	}

	public Set<SoilTestReport> getSoilTestReports() {
		return soilTestReports;
	}

	public void setSoilTestReports(Set<SoilTestReport> soilTestReports) {
		this.soilTestReports = soilTestReports;
	}

	public long getMonitorLocationId() {
		return monitorLocationId;
	}

	public void setMonitorLocationId(long monitorLocationId) {
		this.monitorLocationId = monitorLocationId;
	}

	public boolean isWorkable() {
		return workable;
	}

	public void setWorkable(boolean workable) {
		this.workable = workable;
	}

	public boolean isSprayable() {
		return sprayable;
	}

	public void setSprayable(boolean sprayable) {
		this.sprayable = sprayable;
	}

	public int getDayToHarvest() {
		return dayToHarvest;
	}

	public void setDayToHarvest(int dayToHarvest) {
		this.dayToHarvest = dayToHarvest;
	}

	public Date getLastForceSet() {
		return lastForceSet;
	}

	public void setLastForceSet(Date lastForceSet) {
		this.lastForceSet = lastForceSet;
	}

	public boolean getIsJustSet() {
		return isJustSet;
	}

	public void setIsJustSet(boolean isJustSet) {
		this.isJustSet = isJustSet;
	}

	public double getDayOffset() {
		return dayOffset;
	}

	public void setDayOffset(double dayOffset) {
		this.dayOffset = dayOffset;
	}

}
