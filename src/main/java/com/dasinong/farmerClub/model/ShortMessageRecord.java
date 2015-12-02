package com.dasinong.farmerClub.model;

import java.sql.Timestamp;

import com.dasinong.farmerClub.util.StringUtils;

public class ShortMessageRecord {

	private long id;
	private String externalId;
	private long senderId;
	private String receivers;
	private String data;
	private ShortMessageStatus status;
	private String errorResponse;
	private Timestamp createdAt;
	private Timestamp retriedAt;
	private int retriedTimes;
	
	public ShortMessageRecord() {}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getExternalId() {
		return this.externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	public long getSenderId() {
		return this.senderId;
	}
	
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	
	public String getReceivers() {
		return this.receivers;
	}
	
	public void setReceivers(String[] receivers) {
		this.receivers = StringUtils.join(",", receivers);
	}
	
	public String getData() {
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public ShortMessageStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(ShortMessageStatus status) {
		this.status = status;
	}
	
	public String getErrorResponse() {
		return this.errorResponse;
	}
	
	public void setErrorResponse(String errorResponse) {
		this.errorResponse = errorResponse;
	}
	
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public Timestamp getRetriedAt() {
		return this.retriedAt;
	}
	
	public void setRetriedAt(Timestamp retriedAt) {
		this.retriedAt = retriedAt;
	}
	
	public int getRetriedTimes() {
		return this.retriedTimes;
	}
	
	public void setRetriedTimes(int retriedTimes) {
		this.retriedTimes = retriedTimes;
	}
}
