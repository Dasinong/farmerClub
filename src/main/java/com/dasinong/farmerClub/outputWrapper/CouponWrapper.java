package com.dasinong.farmerClub.outputWrapper;

import java.sql.Timestamp;

import com.dasinong.farmerClub.coupon.CouponCampaignType;
import com.dasinong.farmerClub.coupon.CouponDisplayStatus;
import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.User;

public class CouponWrapper {
	
	public long id;
	public long amount;
	public CouponCampaignType type;
	public Long campaignId;
	public CouponCampaignWrapper campaign;
	public Long scannerId;
	public Long ownerId;
	public CouponDisplayStatus displayStatus;
	public Timestamp claimedAt;
	public Timestamp redeemedAt;
	public Timestamp createdAt;
	
	public CouponWrapper(Coupon coupon) {
		this(coupon, false);
	}

	public CouponWrapper(Coupon coupon, boolean expandCampaign) {
		this.id = coupon.getId();
		this.amount = coupon.getAmount();
		this.type = coupon.getType();
		
		if (expandCampaign) {
			this.campaign = new CouponCampaignWrapper(coupon.getCampaign());
		} else {
			this.campaignId = coupon.getCampaign().getId();
		}
		
		if (coupon.getOwner() != null) {
			this.ownerId = coupon.getOwner().getUserId();
		}
		
		if (coupon.getScanner() != null) {
			this.scannerId = coupon.getScanner().getUserId();
		}
		
		this.claimedAt = coupon.getClaimedAt();
		this.redeemedAt = coupon.getRedeemedAt();
		this.createdAt = coupon.getCreatedAt();
		
		if (coupon.isRedeemed()) {
			this.displayStatus = CouponDisplayStatus.USED;
		} else if (coupon.canBeRedeemed()) {
			this.displayStatus = CouponDisplayStatus.NOT_USED;
		} else {
			this.displayStatus = CouponDisplayStatus.EXPIRED;
		}
	}
}
