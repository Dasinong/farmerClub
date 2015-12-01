/**
 * 
 */
package com.dasinong.farmerClub.ruleEngine.domain;

/**
 * @author caichao
 *
 */
public class Reminder {
	private String message;

	public Reminder(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Reminder [message=" + message + "]";
	}

}
