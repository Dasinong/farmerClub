package com.dasinong.farmerClub.exceptions;

public class GenerateUserAccessTokenException extends Exception {

	private Long userId;
	private Long appId;

	public GenerateUserAccessTokenException(Long userId, Long appId) {
		this.userId = userId;
		this.appId = appId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public Long getAppId() {
		return this.appId;
	}
}
