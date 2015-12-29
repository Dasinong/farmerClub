package com.dasinong.farmerClub.facade;

import java.util.List;

import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;
import com.dasinong.farmerClub.outputWrapper.GroupedScannedCouponsWrapper;

public interface ICouponFacade {
	
	public CouponCampaignWrapper getCampaign(long campaignId);

	public List<CouponCampaignWrapper> findClaimableCampaigns();
	
	public CouponWrapper claim(long campaignId, long ownerId) throws Exception;
	
	public CouponWrapper redeem(long couponId, long ownerId, long scannerId) throws Exception;
	
	public List<CouponWrapper> findCouponsByOwnerId(long ownerId);
	
	public List<CouponWrapper> findCouponsByScannerId(long scannerId) throws Exception;
	
	public GroupedScannedCouponsWrapper groupScannedCouponsByCampaignId(long scannerId) throws Exception;

}
