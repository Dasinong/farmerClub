package com.dasinong.farmerClub.coupon;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.coupon.exceptions.CouponCampaignInvalidParameterException;
import com.dasinong.farmerClub.coupon.exceptions.CouponCampaignNotFoundException;
import com.dasinong.farmerClub.dao.ICouponCampaignDao;
import com.dasinong.farmerClub.dao.ICouponDao;
import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.RetailerStore;

public class CouponCampaignMutator {
	
	private CouponCampaign existingCampaign;
	private ICouponCampaignDao campaignDao;
	private ICouponDao couponDao;
	
	private String name = null;
	private String description = null;
	private long volume = -1L;
	private Institution institution = null;
	private List<RetailerStore> stores = null;
	private CouponCampaignType type = null;
	private Timestamp claimTimeStart = null;
	private Timestamp claimTimeEnd = null;
	private Timestamp redeemTimeStart = null;
	private Timestamp redeemTimeEnd = null;
	private String pictureUrl = null;
	private long amount = 0L;

	public CouponCampaignMutator() {
		this.couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		this.campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
	}
	
	public CouponCampaignMutator(CouponCampaign campaign) {
		this();
		this.existingCampaign = campaign;
	}
	
	public CouponCampaignMutator setCouponDao(ICouponDao couponDao) {
		this.couponDao = couponDao;
		return this;
	}
	
	public CouponCampaignMutator setCampaignDao(ICouponCampaignDao campaignDao) {
		this.campaignDao = campaignDao;
		return this;
	}
	
	public CouponCampaignMutator setName(String name) {
		this.name = name;
		return this;
	}
	
	public CouponCampaignMutator setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public CouponCampaignMutator setVolume(long volume) {
		this.volume = volume;
		return this;
	}
	
	public CouponCampaignMutator setInstitution(Institution inst) {
		this.institution = inst;
		return this;
	}
	
	public CouponCampaignMutator setType(CouponCampaignType type) {
		this.type = type;
		return this;
	}
	
	public CouponCampaignMutator setClaimTimeStart(Timestamp start) {
		this.claimTimeStart = start;
		return this;
	}
	
	public CouponCampaignMutator setClaimTimeEnd(Timestamp end) {
		this.claimTimeEnd = end;
		return this;
	}
	
	public CouponCampaignMutator setRedeemTimeStart(Timestamp start) {
		this.redeemTimeStart = start;
		return this;
	}
	
	public CouponCampaignMutator setRedeemTimeEnd(Timestamp end) {
		this.redeemTimeEnd = end;
		return this;
	}
	
	public CouponCampaignMutator setAmount(long amount) {
		this.amount = amount;
		return this;
	}
	
	public CouponCampaignMutator setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
		return this;
	}
	
	public CouponCampaignMutator setRetailerStores(List<RetailerStore> stores) {
		this.stores = stores;
		return this;
	}
	
	public CouponCampaign save() throws Exception {
		// validate parameters
		if (name == null || "".equals(name)) {
			throw new CouponCampaignInvalidParameterException("name");
		}
		
		if (volume <= 0) {
			throw new CouponCampaignInvalidParameterException("volume");
		}
		
		if (institution == null) {
			throw new CouponCampaignInvalidParameterException("institution");
		}
		
		if (type == null) {
			throw new CouponCampaignInvalidParameterException("type");
		}
		
		if (type.equals(CouponCampaignType.CASH) && amount <= 0) {
			throw new CouponCampaignInvalidParameterException("amount");
		}
		
		if (claimTimeStart == null || claimTimeEnd == null) {
			throw new CouponCampaignInvalidParameterException("claimTime");
		}
		
		if (claimTimeStart.after(claimTimeEnd)) {
			throw new CouponCampaignInvalidParameterException("claimTimeStart > claimTimeEnd");
		}
		
		if (redeemTimeStart == null || redeemTimeEnd == null) {
			throw new CouponCampaignInvalidParameterException("redeemTime");
		}
		
		if (redeemTimeStart.after(redeemTimeEnd)) {
			throw new CouponCampaignInvalidParameterException("redeemTimeStart > redeemTimeEnd");
		}
		
		// create coupon campaign
		CouponCampaign campaign = new CouponCampaign();					
		campaign.setName(name);
		campaign.setDescription(description);
		campaign.setVolume(volume);
		campaign.setUnclaimedVolume(volume);
		campaign.setInstitution(institution);
		campaign.setType(type);
		campaign.setClaimTimeStart(claimTimeStart);
		campaign.setClaimTimeEnd(claimTimeEnd);
		campaign.setRedeemTimeStart(redeemTimeStart);
		campaign.setRedeemTimeEnd(redeemTimeEnd);
		campaign.setRetailerStores(stores);
		// this.campaignDao.save(campaign);
		
		// TODO: batch insert
		for (int i=0; i<volume;i++) {
			System.out.println("inserting " + i + " coupon");
			Coupon coupon = new Coupon();
			coupon.setCampaign(campaign);
			coupon.setAmount(amount);
			coupon.setType(type);
			campaign.getCoupons().add(coupon);
		}
		System.out.println("coupon size = " + campaign.getCoupons().size());
		this.campaignDao.save(campaign);
		
		return campaign;
	}
	
	public void delete() {
		// TODO: batch delete
		if (existingCampaign == null) {
			return;
		}
		
		campaignDao.delete(existingCampaign);
	}
	
	public void update() throws Exception {
		if (existingCampaign == null) {
			return;
		}
		
		// Only these fields can be updated
		// - name
		// - description
		// - claimTimeStart
		// - claimTimeEnd
		// - redeemTimeStart
		// - redeemTimeEnd
		// - stores

		// Validate parameters
		if (claimTimeStart == null || claimTimeEnd == null) {
			throw new CouponCampaignInvalidParameterException("claimTime");
		}
		
		if (claimTimeStart.after(claimTimeEnd)) {
			throw new CouponCampaignInvalidParameterException("claimTimeStart > claimTimeEnd");
		}
		
		if (redeemTimeStart == null || redeemTimeEnd == null) {
			throw new CouponCampaignInvalidParameterException("redeemTime");
		}
		
		if (redeemTimeStart.after(redeemTimeEnd)) {
			throw new CouponCampaignInvalidParameterException("redeemTimeStart > redeemTimeEnd");
		}
		
		// Update campaign
		if (name != null && !name.equals(""))
			existingCampaign.setName(name);
		
		if (description != null)
			existingCampaign.setDescription(description);		
		
		if (claimTimeStart != null)
			existingCampaign.setClaimTimeStart(claimTimeStart);
		
		if (claimTimeEnd != null)
			existingCampaign.setClaimTimeEnd(claimTimeEnd);
		
		if (redeemTimeStart != null)
			existingCampaign.setRedeemTimeStart(redeemTimeStart);
		
		if (redeemTimeEnd != null)
			existingCampaign.setRedeemTimeEnd(redeemTimeEnd);
		
		if (stores != null || stores.size() != 0)
			existingCampaign.setRetailerStores(stores);
		
		campaignDao.update(existingCampaign);
	}
}
