package com.dasinong.farmerClub.coupon;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.coupon.exceptions.CampaignNotInClaimRangeException;
import com.dasinong.farmerClub.coupon.exceptions.CampaignNotInRedeemRangeException;
import com.dasinong.farmerClub.coupon.exceptions.CanNotClaimMultipleCouponException;
import com.dasinong.farmerClub.coupon.exceptions.CouponAlreadyRedeemedException;
import com.dasinong.farmerClub.coupon.exceptions.NoMoreAvailableCouponException;
import com.dasinong.farmerClub.dao.ICouponCampaignDao;
import com.dasinong.farmerClub.dao.ICouponDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.User;

public class CouponMutator {
	private ICouponCampaignDao campaignDao;
	private ICouponDao couponDao;
	private IUserDao userDao;
	
	public CouponMutator() {
		this.couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		this.campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		this.userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
	}
	
	public CouponMutator setCouponDao(ICouponDao couponDao) {
		this.couponDao = couponDao;
		return this;
	}
	
	public CouponMutator setCampaignDao(ICouponCampaignDao campaignDao) {
		this.campaignDao = campaignDao;
		return this;
	}
	
	public Coupon redeem(long couponId, long scannerId) throws Exception {
		User scanner = userDao.findById(scannerId);
		Coupon coupon = couponDao.findById(couponId);
		CouponCampaign campaign = coupon.getCampaign();
		
		if (coupon.isRedeemed()) {
			throw new CouponAlreadyRedeemedException();
		}
		
		if (!campaign.isInRedeemTimeRange()) {
			throw new CampaignNotInRedeemRangeException();
		}
		
		coupon.setScanner(scanner);
		coupon.setRedeemedAt(new Timestamp((new Date()).getTime()));
		couponDao.save(coupon);
		
		return coupon;
	}
	
	public Coupon claim(long campaignId, long ownerId) throws Exception {
		CouponCampaign campaign = campaignDao.findById(campaignId);
		if (!campaign.isInClaimTimeRange()) {
			throw new CampaignNotInClaimRangeException();
		}
		
		List<Coupon> coupons = couponDao.findByOwnerIdAndCampaignId(ownerId, campaignId);
		if (coupons.size() > 0) {
			throw new CanNotClaimMultipleCouponException();
		}
		
		Coupon coupon = null;
		while (true) {
			// Randomly pick a coupon so that race condition is less likely to happen
			coupon = couponDao.findRandomClaimableCoupon(campaignId);
			if (coupon == null) {
				throw new NoMoreAvailableCouponException();
			}
			
			// Just in case that others are locking the same coupon
			if (CouponLocker.tryLock(coupon.getId())) {
				break;
			}
		}
		
		try {
			User owner = userDao.findById(ownerId);
			coupon.setOwner(owner);
			coupon.setClaimedAt(new Timestamp((new Date()).getTime()));
			couponDao.save(coupon);
		} catch (Exception ex) {
			// Unlock the coupon
			CouponLocker.unlock(coupon.getId());
			throw ex;
		}

		campaignDao.decrementVolume(campaignId);
		
		return coupon;
	}
}
