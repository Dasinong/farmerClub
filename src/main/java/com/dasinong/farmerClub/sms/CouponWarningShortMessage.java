package com.dasinong.farmerClub.sms;

public class CouponWarningShortMessage extends ShortMessageBase{
	
	private long campaign;
	private int remainCoupon;
	
	public CouponWarningShortMessage(long campaign,int remainCoupon){
		this.campaign = campaign;
		this.remainCoupon = remainCoupon;
		this.receivers = new String[]{
				"13162881998"
		};
	}
	
	@Override
	public ShortMessageType getType() {
		return ShortMessageType.Coupon_Warning;
	}

	@Override
	public String getContent() {
		return campaign+"号活动还剩"+remainCoupon+"张卷";
	}

	@Override
	public String getSmsProductId() {
		return SmsProductId.NOTIFICATION;
	}

}
