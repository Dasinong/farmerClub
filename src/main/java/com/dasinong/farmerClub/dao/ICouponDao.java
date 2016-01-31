package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.Coupon;

public interface ICouponDao extends IEntityDao<Coupon> {

	List<Coupon> findByOwnerId(long ownerId);
	
	Coupon findRandomClaimableCoupon(long campaignId);
	
	List<Coupon> findByScannerId(long scannerId);
	
	List<Coupon> findByOwnerIdAndCampaignId(long ownerId, long campaignId);

}
