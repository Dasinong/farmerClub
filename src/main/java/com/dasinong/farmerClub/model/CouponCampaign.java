package com.dasinong.farmerClub.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dasinong.farmerClub.coupon.CouponCampaignType;

public class CouponCampaign {

	private long id;
	private String name;
	private String description;
	private String pictureUrl;
	private long volume;
	private long unclaimedVolume;
	private long amount;
	private CouponCampaignType type;
	private Timestamp claimTimeStart;
	private Timestamp claimTimeEnd;
	private Timestamp redeemTimeStart;
	private Timestamp redeemTimeEnd;
	private Timestamp createdAt;
	
	private Institution institution;
	private List<Coupon> coupons = new ArrayList<Coupon>();
	private List<Store> retailerStores = new ArrayList<Store>();
	
	public CouponCampaign() {}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPictureUrl() {
		return this.pictureUrl;
	}
	
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	public long getVolume() {
		return this.volume;
	}
	
	public void setVolume(long volume) {
		this.volume = volume;
	}
	
	public long getUnclaimedVolume() {
		return this.unclaimedVolume;
	}
	
	public void setUnclaimedVolume(long volume) {
		this.unclaimedVolume = volume;
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
	
	public Timestamp getClaimTimeStart() {
		return this.claimTimeStart;
	}
	
	public void setClaimTimeStart(Timestamp StartTime) {
		this.claimTimeStart = StartTime;
	}
	
	public Timestamp getClaimTimeEnd() {
		return this.claimTimeEnd;
	}
	
	public void setClaimTimeEnd(Timestamp EndTime) {
		this.claimTimeEnd = EndTime;
	}
	
	public Timestamp getRedeemTimeStart() {
		return this.redeemTimeStart;
	}
	
	public void setRedeemTimeStart(Timestamp StartTime) {
		this.redeemTimeStart = StartTime;
	}
	
	public Timestamp getRedeemTimeEnd() {
		return this.redeemTimeEnd;
	}
	
	public void setRedeemTimeEnd(Timestamp EndTime) {
		this.redeemTimeEnd = EndTime;
	}
	
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public Institution getInstitution() {
		return this.institution;
	}
	
	public void setInstitution(Institution inst) {
		this.institution = inst;
	}
	
	public List<Coupon> getCoupons() {
		return this.coupons;
	}
	
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public List<Store> getRetailerStores() {
		return this.retailerStores;
	}
	
	public void setRetailerStores(List<Store> stores) {
		this.retailerStores = stores;
	}
	
	public boolean isInClaimTimeRange() {
		Timestamp current = new Timestamp((new Date()).getTime());
		System.out.println(this.claimTimeStart);
		System.out.println(this.getClaimTimeStart());
		return current.after(this.claimTimeStart) && current.before(this.claimTimeEnd);
	}
	
	public boolean isInRedeemTimeRange() {
		Timestamp current = new Timestamp((new Date()).getTime());
		return current.after(this.redeemTimeStart) && current.before(this.redeemTimeEnd);
	}
}
