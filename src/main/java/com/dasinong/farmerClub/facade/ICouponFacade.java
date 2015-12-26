package com.dasinong.farmerClub.facade;

import java.sql.Timestamp;
import java.util.List;

import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.coupon.CouponCampaignType;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;

public interface ICouponFacade {

	/*public CouponCampaign createCampaign(String name, String description, long volume, long institutionId,
			CouponCampaignType type, Timestamp claimTimeStart, Timestamp claimTimeEnd, Timestamp redeemTimeStart,
			Timestamp redeemTimeEnd, long amount) throws Exception;*/
	public CouponCampaign createCampaign() throws Exception;
	
	public CouponCampaignWrapper getCampaign(long campaignId);
	
	public void deleteCampaign(long campaignId);

	public List<CouponCampaignWrapper> findClaimableCampaigns();
	
	public CouponWrapper claim(long campaignId, long ownerId) throws Exception;
	
	public CouponWrapper redeem(long couponId, long ownerId, long scannerId) throws Exception;
	
	public List<CouponWrapper> findCouponsByOwnerId(long ownerId);
	
	public List<CouponWrapper> findCouponsByScannerId(long scannerId) throws Exception;

	public void test() throws Exception;

}
