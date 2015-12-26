package com.dasinong.farmerClub.model;

import java.sql.Timestamp;
import java.util.List;

public class RetailerStore {
	private long id;
	private long ownerId;
	private String name;
	private Timestamp createdAt;
	
	public RetailerStore() {}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public long getOwnerId() {
		return this.ownerId;
	}
	
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
