package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long locationId;
	private Set<Field> fields = new HashSet<Field>();
	private Set<NatDisSpec> natDisSpecs = new HashSet<NatDisSpec>();
	private Set<PetDisSpec> petDisSpecs = new HashSet<PetDisSpec>();

	private String region;
	private String province;
	private String city;
	private String country;
	private String district;
	private String community;
	private double latitude;
	private double longtitude;

	public Location() {
	}

	public Location(String region, String province, String city, String country, String district, String community,
			double latitude, double longtitude) {
		super();
		this.region = region;
		this.province = province;
		this.city = city;
		this.country = country;
		this.district = district;
		this.community = community;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Set<Field> getFields() {
		return fields;
	}

	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}

	public Set<NatDisSpec> getNatDisSpecs() {
		return natDisSpecs;
	}

	public void setNatDisSpecs(Set<NatDisSpec> natDisSpecs) {
		this.natDisSpecs = natDisSpecs;
	}

	public Set<PetDisSpec> getPetDisSpecs() {
		return petDisSpecs;
	}

	public void setPetDisSpecs(Set<PetDisSpec> petDisSpecs) {
		this.petDisSpecs = petDisSpecs;
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

	public String toString() {
		if (this.province.equals(this.city)) {
			return this.province + this.country + this.district + this.community;
		}

		return this.province + this.city + this.country + this.district + this.community;
	}

}
