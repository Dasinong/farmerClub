package com.dasinong.farmerClub.model;

import java.io.Serializable;

public class SubScribeList implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long ownerId;

	private String targetName;
	private String cellphone;
	private String province;
	private String city;
	private String country;
	private String district;
	private double area;
	private Long cropId;
	private boolean isAgriWeather;
	private boolean isNatAler;
	private boolean isRiceHelper;
	private String cropName;

	public SubScribeList() {
	};

	public SubScribeList(Long ownerId, String targetName, String cellphone, String province, String city,
			String country, String district, double area, Long cropId, boolean isAgriWeather, boolean isNatAler,
			boolean isRiceHelper) {
		super();
		this.ownerId = ownerId;
		this.targetName = targetName;
		this.cellphone = cellphone;
		this.province = province;
		this.city = city;
		this.country = country;
		this.district = district;
		this.area = area;
		this.cropId = cropId;
		this.isAgriWeather = isAgriWeather;
		this.isNatAler = isNatAler;
		this.isRiceHelper = isRiceHelper;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
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

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public Long getCropId() {
		return cropId;
	}

	public void setCropId(Long cropId) {
		this.cropId = cropId;
	}

	public boolean getIsAgriWeather() {
		return isAgriWeather;
	}

	public void setIsAgriWeather(boolean isAgriWeather) {
		this.isAgriWeather = isAgriWeather;
	}

	public boolean getIsNatAler() {
		return isNatAler;
	}

	public void setIsNatAler(boolean isNatAler) {
		this.isNatAler = isNatAler;
	}

	public boolean getIsRiceHelper() {
		return isRiceHelper;
	}

	public void setIsRiceHelper(boolean isRiceHelper) {
		this.isRiceHelper = isRiceHelper;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

}
