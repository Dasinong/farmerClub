package com.dasinong.farmerClub.exceptions;

public class SmsProviderResponseParseException extends Exception {

	private String response;
	
	public SmsProviderResponseParseException(String response) {
		this.response = response;
	}
	
	public String getResponse() {
		return this.response;
	}
}
