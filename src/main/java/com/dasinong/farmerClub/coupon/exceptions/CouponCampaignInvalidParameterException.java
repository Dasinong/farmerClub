package com.dasinong.farmerClub.coupon.exceptions;

public class CouponCampaignInvalidParameterException extends Exception {

	private String msg;
	
	public CouponCampaignInvalidParameterException(String msg) {
		this.msg = msg;
	}
}
