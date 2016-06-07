package com.dasinong.farmerClub.model;

import java.sql.Timestamp;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.dasinong.farmerClub.coupon.CouponCampaignType;
import com.dasinong.farmerClub.coupon.CouponDisplayStatus;

public class Coupon {

	private long id;
	private long amount;
	private CouponCampaignType type;
	private CouponCampaign campaign;
	private User scanner;
	private User owner;
	private Timestamp claimedAt;
	private Timestamp redeemedAt;
	private Timestamp createdAt;
	
	//Supports tracing two product amount.
	private double p1amount; //凯润
	private double p2amount; //健达
	
	private String comment;
	
	public Coupon() {}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getAmount() {
		return this.amount;
	}
	
	public void setAmount(long amount) {
		this.amount = amount;
	}

	public CouponCampaignType getType() {
		return this.type;
	}
	
	public void setType(CouponCampaignType type) {
		this.type = type;
	}
	
	public CouponCampaign getCampaign() {
		return this.campaign;
	}
	
	public void setCampaign(CouponCampaign campaign) {
		this.campaign = campaign;
	}
	
	public User getScanner() {
		return this.scanner;
	}
	
	public void setScanner(User scanner) {
		this.scanner = scanner;
	}
	
	public User getOwner() {
		return this.owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Timestamp getClaimedAt() {
		return this.claimedAt;
	}
	
	public void setClaimedAt(Timestamp claimedAt) {
		this.claimedAt = claimedAt;
	}
	
	public Timestamp getRedeemedAt() {
		return this.redeemedAt;
	}
	
	public void setRedeemedAt(Timestamp redeemedAt) {
		this.redeemedAt = redeemedAt;
	}
	
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public boolean isClaimed() {
		return (this.owner != null) && (this.claimedAt != null);
	}
	
	public boolean isRedeemed() {
		return this.isClaimed() && (this.scanner != null) && (this.redeemedAt != null);
	}
	
	public boolean canBeClaimed() {
		return !this.isClaimed() && this.campaign.isInClaimTimeRange();
	}
	
	public boolean canBeRedeemed() {
		return !this.isRedeemed() && this.campaign.isInRedeemTimeRange();
	}
	
	public CouponDisplayStatus getDisplayStatus() {
		if (this.isRedeemed()) {
			return CouponDisplayStatus.USED;
		} else if (this.isClaimed()) {
			Timestamp current = new Timestamp((new Date()).getTime());
			if (current.after(this.campaign.getRedeemTimeEnd())) {
				return CouponDisplayStatus.EXPIRED;
			} else {
				return CouponDisplayStatus.NOT_USED;
			}
		} else {
			return CouponDisplayStatus.UNCLAIMED;
		}
	}

	public double getP1amount() {
		return p1amount;
	}

	public void setP1amount(double p1amount) {
		this.p1amount = p1amount;
	}

	public double getP2amount() {
		return p2amount;
	}

	public void setP2amount(double p2amount) {
		this.p2amount = p2amount;
	}

	public String getComment() {
		this.setComment();
		return comment;
	}
	
	public void setComment() {
		this.comment = "凯润："+ p1amount + "L；健达："+p2amount+"L";
	}
	
}
