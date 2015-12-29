package com.dasinong.farmerClub.outputWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponCampaign;

public class GroupedScannedCouponsWrapper {
	
	public List<CouponCampaignWrapper> campaigns = null;
	public HashMap<Long, List<CouponWrapper>> scannedCouponsByCampaign = null;
	
	public GroupedScannedCouponsWrapper(List<Coupon> scannedCoupons) {
		HashMap<Long, CouponCampaign> campaignsById = new HashMap<Long, CouponCampaign>();
		for (Coupon coupon : scannedCoupons) {
			Long campaignId = coupon.getCampaign().getId();
			
			if (!campaignsById.containsKey(campaignId)) {
				campaignsById.put(campaignId, coupon.getCampaign());
			}
		}
		
		scannedCouponsByCampaign = new HashMap<Long, List<CouponWrapper>>();
		for (Coupon coupon : scannedCoupons) {
			Long campaignId = coupon.getCampaign().getId();
			
			if (!scannedCouponsByCampaign.containsKey(campaignId)) {
				scannedCouponsByCampaign.put(campaignId, new ArrayList<CouponWrapper>());
			}
			
			scannedCouponsByCampaign.get(campaignId).add(new CouponWrapper(coupon));
		}
		
		campaigns = new ArrayList<CouponCampaignWrapper>();
		for (Long campaignId : scannedCouponsByCampaign.keySet()) {
			CouponCampaign campaign = campaignsById.get(campaignId);
			CouponCampaignWrapper campaignWrapper = new CouponCampaignWrapper(campaign);
			campaigns.add(campaignWrapper);
		}
	}
}
