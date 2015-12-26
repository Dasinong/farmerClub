package com.dasinong.farmerClub.facade;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.coupon.CouponCampaignMutator;
import com.dasinong.farmerClub.coupon.CouponCampaignType;
import com.dasinong.farmerClub.coupon.CouponMutator;
import com.dasinong.farmerClub.coupon.exceptions.CanNotClaimMultipleCouponException;
import com.dasinong.farmerClub.coupon.exceptions.CanNotRedeemOthersCouponException;
import com.dasinong.farmerClub.coupon.exceptions.CouponAlreadyRedeemedException;
import com.dasinong.farmerClub.coupon.exceptions.CouponCanNotBeClaimedException;
import com.dasinong.farmerClub.coupon.exceptions.CouponCanNotBeRedeemedException;
import com.dasinong.farmerClub.coupon.exceptions.NoMoreAvailableCouponException;
import com.dasinong.farmerClub.coupon.exceptions.OnlyRetailerCanSeeScannedCouponsException;
import com.dasinong.farmerClub.dao.ICouponCampaignDao;
import com.dasinong.farmerClub.dao.ICouponDao;
import com.dasinong.farmerClub.dao.IInstitutionDao;
import com.dasinong.farmerClub.dao.IRetailerStoreDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.RetailerStore;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.UserType;
import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;

@Transactional
public class CouponFacade implements ICouponFacade {
	
	@Override
	public CouponWrapper claim(long campaignId, long ownerId) throws Exception {
		Coupon coupon = (new CouponMutator()).claim(campaignId, ownerId);
		return new CouponWrapper(coupon);
	}
	
	@Override
	public CouponWrapper redeem(long couponId, long ownerId, long scannerId) throws Exception {
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		Coupon coupon = couponDao.findById(couponId);
		
		if (ownerId != coupon.getOwner().getUserId()) {
			throw new CanNotRedeemOthersCouponException();
		}
		
		coupon = (new CouponMutator()).redeem(couponId, scannerId);
		return new CouponWrapper(coupon);
	}
	
	@Override
	public void deleteCampaign(long campaignId) {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		CouponCampaign campaign = campaignDao.findById(campaignId);
		(new CouponCampaignMutator(campaign)).delete();
	}

	@Override
	/*public CouponCampaign createCampaign(String name, String description, long volume, long institutionId,
			CouponCampaignType type, Timestamp claimTimeStart, Timestamp claimTimeEnd, Timestamp redeemTimeStart,
			Timestamp redeemTimeEnd, long amount) {*/
	public CouponCampaign createCampaign() throws Exception {
		IInstitutionDao instDao = (IInstitutionDao) ContextLoader.getCurrentWebApplicationContext().getBean("institutionDao");
		IRetailerStoreDao storeDao = (IRetailerStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("retailerStoreDao");
		Institution inst = instDao.findById(2L);
		List<RetailerStore> stores = new ArrayList<RetailerStore>();
		stores.add(storeDao.findById(1L));
		stores.add(storeDao.findById(2L));
		
		CouponCampaign campaign = (new CouponCampaignMutator())
				.setName("赵日天Campaign")
				.setDescription("最牛逼的小学")
				.setInstitution(inst)
				.setType(CouponCampaignType.CASH)
				.setVolume(10)
				.setAmount(30)
				.setRetailerStores(stores)
				.setRedeemTimeStart(Timestamp.valueOf("2016-03-01 00:00:00"))
				.setRedeemTimeEnd(Timestamp.valueOf("2016-03-31 00:00:00"))
				.setClaimTimeStart(Timestamp.valueOf("2016-02-01 00:00:00"))
				.setClaimTimeEnd(Timestamp.valueOf("2016-02-28 00:00:00"))
				.save();

		return campaign;
	}
	
	@Override
	public void test() throws Exception {
		//this.createCampaign("abc", "abc", 100, 2, CouponCampaignType.CASH, Timestamp.valueOf("2016-01-01 00:00:00"), Timestamp.valueOf("2016-01-01 00:00:00"), Timestamp.valueOf("2016-01-01 00:00:00"), Timestamp.valueOf("2016-01-01 00:00:00"), 100);
	}

	@Override
	public List<CouponCampaignWrapper> findClaimableCampaigns() {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		List<CouponCampaign> campaigns = campaignDao.findClaimable();
		
		List<CouponCampaignWrapper> campaignWrappers = new ArrayList<CouponCampaignWrapper>();
		for (CouponCampaign campaign : campaigns) {
			campaignWrappers.add(new CouponCampaignWrapper(campaign));
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

}
