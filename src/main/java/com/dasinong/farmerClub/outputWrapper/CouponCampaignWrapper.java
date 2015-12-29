package com.dasinong.farmerClub.outputWrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.dasinong.farmerClub.coupon.CouponCampaignType;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.Store;

public class CouponCampaignWrapper {

	public long id;
	public String name;
	public String description;
	public String pictureUrl;
	public long totalVolume;
	public long unclaimedVolume;
	public CouponCampaignType type;
	public Timestamp claimTimeStart;
	public Timestamp claimTimeEnd;
	public Timestamp redeemTimeStart;
	public Timestamp redeemTimeEnd;
	
	public InstitutionWrapper institution = null;
	public List<StoreWrapper> stores = null;
	
	public CouponCampaignWrapper(CouponCampaign campaign) {
		this.id = campaign.getId();
		this.name = campaign.getName();
		this.description = campaign.getDescription();
		this.pictureUrl = campaign.getPictureUrl();
		this.totalVolume = campaign.getVolume();
		this.unclaimedVolume = campaign.getUnclaimedVolume();
		this.type = campaign.getType();
		this.claimTimeEnd = campaign.getClaimTimeEnd();
		this.claimTimeStart = campaign.getClaimTimeStart();
		this.redeemTimeEnd = campaign.getRedeemTimeEnd();
		this.redeemTimeStart = campaign.getRedeemTimeStart();
		this.institution = new InstitutionWrapper(campaign.getInstitution());
		this.stores = new ArrayList<StoreWrapper>();
		for (Store store : campaign.getRetailerStores()) {
			this.stores.add(new StoreWrapper(store));
		}
	}
}
