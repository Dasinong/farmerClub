package com.dasinong.farmerClub.ruleEngine.domain;

public class Recommendation {
	public String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Recommendation(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "Recommendation [message=" + message + "]";
	}

}
