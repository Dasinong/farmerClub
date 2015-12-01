package com.dasinong.farmerClub.exceptions;

public class UserAccessTokenNotFoundException extends Exception {

	private Long appId;
	private Long userId;

	public UserAccessTokenNotFoundException(Long appId, Long userId) {
		this.appId = appId;
		this.userId = userId;
	}

	public Long getAppId() {
		return this.appId;
	}

	public Long getUserId() {
		return this.userId;
	}
}
