package com.dasinong.farmerClub.exceptions;

public class UnexpectedLatAndLonException extends Exception {

	private double lat;
	private double lon;

	public UnexpectedLatAndLonException(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return this.lat;
	}

	public double getLon() {
		return this.lon;
	}
}
