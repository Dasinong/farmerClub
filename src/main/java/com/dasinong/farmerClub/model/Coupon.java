package com.dasinong.farmerClub.model;

import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.dasinong.farmerClub.coupon.CouponCampaignType;

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

}
