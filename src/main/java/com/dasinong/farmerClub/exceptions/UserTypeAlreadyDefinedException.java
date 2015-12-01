package com.dasinong.farmerClub.exceptions;

public class UserTypeAlreadyDefinedException extends Exception {

	private String userType;
	private Long userId;

	public UserTypeAlreadyDefinedException(Long id, String type) {
		this.userId = id;
		this.userType = type;
	}

	public String getUserType() {
		return this.userType;
	}

	public Long getUserId() {
		return this.userId;
	}
}
