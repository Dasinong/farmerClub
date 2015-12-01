package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class WeatherSubscription implements Serializable {

	// TODO (xiahonggao): figure out what the fuck is this
	private static final long serialVersionUID = 1L;

	private Long weatherSubscriptionId;
	private Long locationId;
	private Long userId;
	private Long monitorLocationId;
	private String locationName;
	private WeatherSubscriptionType type;
	private Timestamp createdAt;
	private Long ordering;

	public Long getWeatherSubscriptionId() {
		return this.weatherSubscriptionId;
	}

	public Long getLocationId() {
		return this.locationId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public Long getMonitorLocationId() {
		return this.monitorLocationId;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public WeatherSubscriptionType getType() {
		return this.type;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public Long getOrdering() {
		return this.ordering;
	}

	public void setWeatherSubscriptionId(long id) {
		this.weatherSubscriptionId = id;
	}

	public void setLocationId(long id) {
		this.locationId = id;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public void setMonitorLocationId(long id) {
		this.monitorLocationId = id;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setType(WeatherSubscriptionType type) {
		this.type = type;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setOrdering(Long ordering) {
		this.ordering = ordering;
	}
}
