package com.dasinong.farmerClub.exceptions;

public class MultipleUserAccessTokenException extends Exception {

	private Long userId;
	private Long appId;

	public MultipleUserAccessTokenException(Long userId, Long appId) {
		this.appId = appId;
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public Long getAppId() {
		return this.appId;
	}

}
