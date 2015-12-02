package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CropSubscription implements Serializable {

	private static final Long serialVersionUID = 1L;
	
	private Long id;
	private Long cropId;
	private String cropName;
	private Long userId;
	private Timestamp createdAt;
	
	public CropSubscription() {}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setCropId(Long cropId) {
		this.cropId = cropId;
	}
	
	public Long getCropId() {
		return this.cropId;
	}
	
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	
	public String getCropName() {
		return this.cropName;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}
	
}
