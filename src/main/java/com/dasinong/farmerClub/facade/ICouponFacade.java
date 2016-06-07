package com.dasinong.farmerClub.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponRequest;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;
import com.dasinong.farmerClub.outputWrapper.GroupedScannedCouponsWrapper;

public interface ICouponFacade {
	
	public CouponCampaignWrapper getCampaign(long campaignId, boolean expand);
	
	public CouponCampaignWrapper getCampaign(long campaignId);
	
	public CouponCampaignWrapper getCampaign(long campaignId, double lat, double lon);
	
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

	Date getCouponClaimTime(long couponId);

	CouponCampaignWrapper getDarenCampaign(long campaignId, long refuid);

	List<CouponWrapper> findCouponsByOwnerId(long ownerId, double lat, double lon);

	List<CouponWrapper> findDarenCouponsByOwnerId(long ownerId, long refuid);

	//CouponWrapper bsfredeem(long couponId, long ownerId, long scannerId, boolean isDaren) throws Exception;
	HashMap<String,Object> bsfredeem(long couponId, long ownerId, long scannerId, boolean isDaren) throws Exception;
	
	CouponWrapper darenClaim(long campaignId, long ownerId, long refuid) throws Exception;

	public CouponWrapper claim(long campaignId, long ownerId, String comment) throws Exception;
	
	CouponWrapper claim(long campaignId, long ownerId, double lat, double lon, String comment) throws Exception;

	List<CouponCampaignWrapper> findDarenCampaigns(long institutionId, long refuid);

	Coupon claimCoupon(long campaignId, long ownerId, String amount) throws Exception;

}
