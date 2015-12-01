package com.dasinong.farmerClub.model;

import java.sql.Timestamp;

public class SecurityCode {

	private Long codeId;
	private String code;
	private Timestamp createdAt;
	private Timestamp expiredAt;

	public SecurityCode() {
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setExpiredAt(Timestamp expiredAt) {
		this.expiredAt = expiredAt;
	}

	public Long getCodeId() {
		return this.codeId;
	}

	public String getCode() {
		return this.code;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public Timestamp getExpiredAt() {
		return this.expiredAt;
	}
}