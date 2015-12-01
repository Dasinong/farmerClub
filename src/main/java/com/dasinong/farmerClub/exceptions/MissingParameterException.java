package com.dasinong.farmerClub.exceptions;

public class MissingParameterException extends RuntimeException {

	private String paramName = null;

	public MissingParameterException() {
	}

	public MissingParameterException(String name) {
		this.paramName = name;
	}

	public String getParamName() {
		return this.paramName;
	}
}
