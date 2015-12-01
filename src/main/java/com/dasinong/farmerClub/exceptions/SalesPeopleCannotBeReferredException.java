package com.dasinong.farmerClub.exceptions;

public class SalesPeopleCannotBeReferredException extends Exception {

	private Long userId;
	
	public SalesPeopleCannotBeReferredException(Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
}
