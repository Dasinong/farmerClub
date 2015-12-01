package com.dasinong.farmerClub.outputWrapper;

import java.util.HashSet;
import java.util.Set;

import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.NatDisSpec;
import com.dasinong.farmerClub.model.PetDisSpec;

public class LocationWrapper {
	private static final long serialVersionUID = 1L;

	private Long locationId;
	private String region;
	private String province;
	private String city;
	private String country;
	private String district;
	private String community;
	private double latitude;
	private double longtitude;

	public LocationWrapper(Location l) {
		super();
		this.locationId = l.getLocationId();
		this.region = l.getRegion();
		this.province = l.getProvince();
		this.city = l.getCity();
		this.country = l.getCountry();
		this.district = l.getDistrict();
		this.community = l.getCommunity();
		this.latitude = l.getLatitude();
		this.longtitude = l.getLongtitude();
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

}
