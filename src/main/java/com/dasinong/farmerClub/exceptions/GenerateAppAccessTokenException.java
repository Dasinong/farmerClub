package com.dasinong.farmerClub.exceptions;

public class GenerateAppAccessTokenException extends Exception {

	private Long appId;

	public GenerateAppAccessTokenException(Long appId) {
		this.appId = appId;
	}

	public Long getAppId() {
		return this.appId;
	}

}
