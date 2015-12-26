package com.dasinong.farmerClub.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dasinong.farmerClub.model.CouponCampaign;

public class CouponCampaignDao extends EntityHibernateDao<CouponCampaign> implements ICouponCampaignDao {

	@Override
	public List<CouponCampaign> findByInstitutionId(long institutionId) {
		List<CouponCampaign> campaigns = this.getHibernateTemplate().find("from CouponCampaign where institutionId = ?", institutionId);
		if (campaigns == null || campaigns.size() == 0) {
			return new ArrayList<CouponCampaign>();
		}
		
		return campaigns;
	}

	@Override
	public List<CouponCampaign> findClaimable() {
		Timestamp current = new Timestamp((new Date()).getTime()); 
		List<CouponCampaign> campaigns = this.getHibernateTemplate().find(
				"from CouponCampaign where unclaimedVolume > 0 and claimTimeStart < ? and claimTimeEnd > ?",
				current, current);
		if (campaigns == null || campaigns.size() == 0) {
			return new ArrayList<CouponCampaign>();
		}
		return campaigns;
	}

	@Override
	public void decrementVolume(long campaignId) {
		this.getHibernateTemplate().bulkUpdate(
				"update CouponCampaign set unclaimedVolume = unclaimedVolume - 1 where id = ?", campaignId);
	}

}
