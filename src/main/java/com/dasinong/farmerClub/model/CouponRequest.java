package com.dasinong.farmerClub.model;


public class CouponRequest {
	private long id;
	private String name;
	private String company;
	private String crop;
	private double area;
	private double yield;
	private String experience;
	private String productUseHistory;
	private String contactNumber;
	
	public CouponRequest(){
		
	}
	
	
	
	public CouponRequest(String name, String company, String crop, double area, double yield, String experience,
			String productUseHistory, String contactNumber) {
		super();
		this.name = name;
		this.company = company;
		this.crop = crop;
		this.area = area;
		this.yield = yield;
		this.experience = experience;
		this.productUseHistory = productUseHistory;
		this.contactNumber = contactNumber;
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCrop() {
		return crop;
	}
	public void setCrop(String crop) {
		this.crop = crop;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getYield() {
		return yield;
	}
	public void setYield(double yield) {
		this.yield = yield;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getProductUseHistory() {
		return productUseHistory;
	}
	public void setProductUseHistory(String productUseHistory) {
		this.productUseHistory = productUseHistory;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	
}
