package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;


import com.dasinong.farmerClub.model.Coupon;

public class CouponDao extends EntityHibernateDao<Coupon>implements ICouponDao {

	@Override
	public List<Coupon> findByOwnerId(long ownerId) {
		List<Coupon> coupons = this.getHibernateTemplate().find("from Coupon where ownerId = ?", ownerId);
		if (coupons == null || coupons.size() == 0) {
			return new ArrayList<Coupon>();
		}

		return coupons;
	}

	@Override
	public List<Coupon> findByScannerId(long scannerId) {
		List<Coupon> coupons = this.getHibernateTemplate().find("from Coupon where scannerId = ?", scannerId);
		if (coupons == null || coupons.size() == 0) {
			return new ArrayList<Coupon>();
		}

		return coupons;
	}

	@Override
	public List<Coupon> findByOwnerIdAndCampaignId(long ownerId, long campaignId) {
		List<Coupon> coupons = this.getHibernateTemplate().find("from Coupon where ownerId = ? and campaignId = ?",
				ownerId, campaignId);
		if (coupons == null || coupons.size() == 0) {
			return new ArrayList<Coupon>();
		}

		return coupons;
	}

	@Override
	public Coupon findRandomClaimableCoupon(long campaignId) {
		HibernateTemplate template = this.getHibernateTemplate();
		template.setMaxResults(1);
		List<Coupon> coupons = template.find("from Coupon where campaignId = ? and ownerId is null order by rand()", campaignId);
		if (coupons == null || coupons.size() == 0) {
			return null;
		}
		return coupons.get(0);
	}

}
