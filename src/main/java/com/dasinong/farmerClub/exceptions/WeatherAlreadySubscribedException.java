package com.dasinong.farmerClub.exceptions;

public class WeatherAlreadySubscribedException extends Exception {

	private Long locationId;

	public WeatherAlreadySubscribedException(Long id) {
		this.locationId = id;
	}

	public Long getLocationId() {
		return this.locationId;
	}
}
