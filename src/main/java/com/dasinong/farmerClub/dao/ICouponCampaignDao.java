package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.CouponCampaign;

public interface ICouponCampaignDao extends IEntityDao<CouponCampaign> {

	List<CouponCampaign> findByInstitutionId(long institutionId);
	
	List<CouponCampaign> findClaimable();
	
	void decrementVolume(long campaignId);

}
