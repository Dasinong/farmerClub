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
	//public String pictureUrl;
	public String[] pictureUrls;
	public long totalVolume;
	public long unclaimedVolume;
	public CouponCampaignType type;
	public Timestamp claimTimeStart;
	public Timestamp claimTimeEnd;
	public Timestamp redeemTimeStart;
	public Timestamp redeemTimeEnd;
	public long amount;
	
	public InstitutionWrapper institution = null;
	public List<StoreWrapper> stores = null;
	
	public CouponCampaignWrapper(CouponCampaign campaign){
		this(campaign,true);
	}
	public CouponCampaignWrapper(CouponCampaign campaign, boolean expand) {
		this.id = campaign.getId();
		this.name = campaign.getName();
		this.description = campaign.getDescription();
		this.pictureUrls = campaign.getPictureUrl().split(";");
		this.totalVolume = campaign.getVolume();
		this.unclaimedVolume = campaign.getUnclaimedVolume();
		this.type = campaign.getType();
		this.claimTimeEnd = campaign.getClaimTimeEnd();
		this.claimTimeStart = campaign.getClaimTimeStart();
		this.redeemTimeEnd = campaign.getRedeemTimeEnd();
		this.redeemTimeStart = campaign.getRedeemTimeStart();
		this.institution = new InstitutionWrapper(campaign.getInstitution());
		this.stores = new ArrayList<StoreWrapper>();
		this.amount = campaign.getAmount();
		if(expand){
			for (Store store : campaign.getRetailerStores()) {
				this.stores.add(new StoreWrapper(store));
			}
		}
	}
	
	public CouponCampaignWrapper(CouponCampaign campaign, double lat, double lon) {
		this.id = campaign.getId();
		this.name = campaign.getName();
		this.description = campaign.getDescription();
		this.pictureUrls = campaign.getPictureUrl().split(";");
		this.totalVolume = campaign.getVolume();
		this.unclaimedVolume = campaign.getUnclaimedVolume();
		this.type = campaign.getType();
		this.claimTimeEnd = campaign.getClaimTimeEnd();
		this.claimTimeStart = campaign.getClaimTimeStart();
		this.redeemTimeEnd = campaign.getRedeemTimeEnd();
		this.redeemTimeStart = campaign.getRedeemTimeStart();
		this.institution = new InstitutionWrapper(campaign.getInstitution());
		this.stores = new ArrayList<StoreWrapper>();
		this.amount = campaign.getAmount();
		for (Store store : campaign.getRetailerStores()) {
			if (((store.getLocation().getLatitude()-lat)*(store.getLocation().getLatitude()-lat)
					+(store.getLocation().getLongtitude()-lon)*(store.getLocation().getLongtitude()-lon))<0.01){
					this.stores.add(new StoreWrapper(store));
			}
		}
	}
	
	//Only show selected store
	public CouponCampaignWrapper(CouponCampaign campaign, List<Store> stores) {
		this.id = campaign.getId();
		this.name = campaign.getName();
		this.description = campaign.getDescription();
		this.pictureUrls = campaign.getPictureUrl().split(";");
		this.totalVolume = campaign.getVolume();
		this.unclaimedVolume = campaign.getUnclaimedVolume();
		this.type = campaign.getType();
		this.claimTimeEnd = campaign.getClaimTimeEnd();
		this.claimTimeStart = campaign.getClaimTimeStart();
		this.redeemTimeEnd = campaign.getRedeemTimeEnd();
		this.redeemTimeStart = campaign.getRedeemTimeStart();
		this.institution = new InstitutionWrapper(campaign.getInstitution());
		this.stores = new ArrayList<StoreWrapper>();
		this.amount = campaign.getAmount();
		for (Store store : stores) {
			this.stores.add(new StoreWrapper(store));
		}
	}


}
