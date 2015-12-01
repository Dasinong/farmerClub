package com.dasinong.farmerClub.exceptions;

public class InvalidAppAccessTokenException extends Exception {

	private String token;

	public InvalidAppAccessTokenException(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}