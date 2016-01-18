package com.dasinong.farmerClub.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.coupon.CouponMutator;
import com.dasinong.farmerClub.coupon.exceptions.CanNotRedeemOthersCouponException;
import com.dasinong.farmerClub.coupon.exceptions.OnlyRetailerCanSeeScannedCouponsException;
import com.dasinong.farmerClub.dao.ICouponCampaignDao;
import com.dasinong.farmerClub.dao.ICouponDao;
import com.dasinong.farmerClub.dao.ICouponRequestDao;
import com.dasinong.farmerClub.dao.IEntityDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.CouponRequest;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.UserType;
import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;
import com.dasinong.farmerClub.outputWrapper.GroupedScannedCouponsWrapper;

@Transactional
public class CouponFacade implements ICouponFacade {
	
	@Override
	public CouponWrapper claim(long campaignId, long ownerId) throws Exception {
		Coupon coupon = (new CouponMutator()).claim(campaignId, ownerId);
		return new CouponWrapper(coupon);
	}
	
	@Override
	public CouponWrapper redeem(long couponId, long ownerId, long scannerId) throws Exception {
		System.out.println("User " + scannerId + " scanned coupon " + couponId);
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		Coupon coupon = couponDao.findById(couponId);
		
		if (ownerId != coupon.getOwner().getUserId()) {
			throw new CanNotRedeemOthersCouponException();
		}
		
		coupon = (new CouponMutator()).redeem(couponId, scannerId);
		return new CouponWrapper(coupon);
	}
	
	@Override
	public List<CouponCampaignWrapper> findClaimableCampaigns() {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		List<CouponCampaign> campaigns = campaignDao.findClaimable();
		
		List<CouponCampaignWrapper> campaignWrappers = new ArrayList<CouponCampaignWrapper>();
		for (CouponCampaign campaign : campaigns) {
			campaignWrappers.add(new CouponCampaignWrapper(campaign,false));
		}
		
		return campaignWrappers;
	}

	@Override
	public CouponCampaignWrapper getCampaign(long campaignId) {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		CouponCampaign campaign = campaignDao.findById(campaignId);
		return new CouponCampaignWrapper(campaign);
	}

	@Override
	public List<CouponWrapper> findCouponsByOwnerId(long ownerId) {
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		List<Coupon> coupons = couponDao.findByOwnerId(ownerId);
		
		List<CouponWrapper> wrappers = new ArrayList<CouponWrapper>();
		for (Coupon coupon : coupons) {
			wrappers.add(new CouponWrapper(coupon, true /* expandCampaign */));
		}
		
		return wrappers;
	}

	@Override
	public List<CouponWrapper> findCouponsByScannerId(long scannerId) throws Exception {
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		User user = userDao.findById(scannerId);
		if (!UserType.isRetailer(user)) {
			throw new OnlyRetailerCanSeeScannedCouponsException();
		}
		
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		List<Coupon> coupons = couponDao.findByScannerId(scannerId);
		
		List<CouponWrapper> wrappers = new ArrayList<CouponWrapper>();
		for (Coupon coupon : coupons) {
			wrappers.add(new CouponWrapper(coupon));
		}
		
		return wrappers;
	}

	@Override
	public GroupedScannedCouponsWrapper groupScannedCouponsByCampaignId(long scannerId) throws Exception {
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		User user = userDao.findById(scannerId);
		if (!UserType.isRetailer(user)) {
			//throw new OnlyRetailerCanSeeScannedCouponsException();
		}
		
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		List<Coupon> coupons = couponDao.findByScannerId(scannerId);
		
		return new GroupedScannedCouponsWrapper(coupons);
	}	
	
	@Override
	public void saveCouponRequest(String name,String company, String crop, double area, double yield, String experience,
				String productUseHistory, String contactNumber){
		CouponRequest cr = new CouponRequest(name,company,crop,area,yield,experience,productUseHistory,contactNumber);

		ICouponRequestDao couponRequestDao = (ICouponRequestDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponRequestDao");
		couponRequestDao.save(cr);
	}

}
