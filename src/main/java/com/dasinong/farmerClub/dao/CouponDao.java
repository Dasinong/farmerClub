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
	public List<Coupon> findByScannerIdAndCampaignId(long scannerId,long campaignId) {
		List<Coupon> coupons = this.getHibernateTemplate().find("from Coupon where scannerId = ? and campaignId = ?", scannerId, campaignId);
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
		template.setMaxResults(100);
		if (coupons == null || coupons.size() == 0) {
			return null;
		}
		return coupons.get(0);
	}
	
	@Override
	public long countScannedCoupon(long campaignId,long scannerId) {
		HibernateTemplate template = this.getHibernateTemplate();
		String hql = "select count(*) from Coupon where campaignId="+ campaignId +" and scannerId="+scannerId;
		Long count = (Long)getHibernateTemplate().find(hql).listIterator().next();
		return count.intValue();
	}
	
	@Override
	public double sumP1amount(long campaignId,long scannerId) {
		HibernateTemplate template = this.getHibernateTemplate();
		String hql = "select sum(p1amount) from Coupon where campaignId="+ campaignId +" and scannerId="+scannerId;
		Double sum = (double)getHibernateTemplate().find(hql).listIterator().next();
		return (sum==null)?0:sum.doubleValue();
	}
	
	@Override
	public double sumP2amount(long campaignId,long scannerId) {
		HibernateTemplate template = this.getHibernateTemplate();
		String hql = "select sum(p2amount) from Coupon where campaignId="+ campaignId +" and scannerId="+scannerId;
		Double sum = (Double)getHibernateTemplate().find(hql).listIterator().next();
		return (sum==null)?0:sum.doubleValue();
	}
	
}
