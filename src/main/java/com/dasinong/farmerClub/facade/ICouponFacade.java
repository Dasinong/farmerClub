package com.dasinong.farmerClub.facade;

import java.util.List;

import com.dasinong.farmerClub.model.CouponRequest;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;
import com.dasinong.farmerClub.outputWrapper.GroupedScannedCouponsWrapper;

public interface ICouponFacade {
	
	public CouponCampaignWrapper getCampaign(long campaignId, boolean expand);
	
	public CouponCampaignWrapper getCampaign(long campaignId);
	
	public CouponCampaignWrapper getCampaign(long campaignId, double lat, double lon);
	
	public CouponWrapper claim(long campaignId, long ownerId) throws Exception;
	
	public CouponWrapper redeem(long couponId, long ownerId, long scannerId) throws Exception;
	
	public List<CouponWrapper> findCouponsByOwnerId(long ownerId);
	
	public List<CouponWrapper> findCouponsByScannerId(long scannerId) throws Exception;
	
	public GroupedScannedCouponsWrapper groupScannedCouponsByCampaignId(long scannerId) throws Exception;

	void saveCouponRequest(String name, String company, String crop, double area, double yield, String experience,
			String productUseHistory, String contactNumber) throws Exception;

	List<CouponCampaignWrapper> findClaimableCampaigns(long institutionId);

	List<CouponCampaignWrapper> findClaimableCampaigns(long institutionId, double lat, double lon);

	List<CouponWrapper> findCouponsByScannerAndCampaignId(long scannerId, long campaignId);

	List<CouponCampaignWrapper> findCampaginsByScannerId(long scannerId);

	List<CouponWrapper> findCouponsByOwnerId(long ownerId, boolean expand);

}
