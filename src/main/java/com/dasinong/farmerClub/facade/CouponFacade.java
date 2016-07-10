package com.dasinong.farmerClub.facade;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.sms.EventMessage;
import com.dasinong.farmerClub.coupon.CouponCampaignType;
import com.dasinong.farmerClub.coupon.CouponLocker;
import com.dasinong.farmerClub.coupon.exceptions.CampaignNotInClaimRangeException;
import com.dasinong.farmerClub.coupon.exceptions.CampaignNotInRedeemRangeException;
import com.dasinong.farmerClub.coupon.exceptions.CanNotClaimMultipleCouponException;
import com.dasinong.farmerClub.coupon.exceptions.CanNotRedeemOthersCouponException;
import com.dasinong.farmerClub.coupon.exceptions.CanNotScanMoreException;
import com.dasinong.farmerClub.coupon.exceptions.CouponAlreadyRedeemedException;
import com.dasinong.farmerClub.coupon.exceptions.NoMoreAvailableCouponException;
import com.dasinong.farmerClub.coupon.exceptions.NotAuthorizedToScanCouponException;
import com.dasinong.farmerClub.coupon.exceptions.NotEnoughAmountException;
import com.dasinong.farmerClub.coupon.exceptions.NotEnoughAuthException;
import com.dasinong.farmerClub.coupon.exceptions.OnlyRetailerCanSeeScannedCouponsException;
import com.dasinong.farmerClub.dao.ICouponCampaignDao;
import com.dasinong.farmerClub.dao.ICouponDao;
import com.dasinong.farmerClub.dao.ICouponRequestDao;
import com.dasinong.farmerClub.dao.IMonitorLocationDao;
import com.dasinong.farmerClub.dao.IProStockDao;
import com.dasinong.farmerClub.dao.IStoreDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.datapool.AllMonitorLocation;
import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.CouponRequest;
import com.dasinong.farmerClub.model.MonitorLocation;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.UserType;
import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;
import com.dasinong.farmerClub.outputWrapper.GroupedScannedCouponsWrapper;
import com.dasinong.farmerClub.sms.CouponWarningShortMessage;
import com.dasinong.farmerClub.sms.SMS;
import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.QRGenUtil;

@Transactional
public class CouponFacade implements ICouponFacade {
	
	
	@Override
	public CouponWrapper darenClaim(long campaignId, long ownerId, long refuid) throws Exception {
		Coupon coupon  = claimCoupon(campaignId,ownerId,"");
		IStoreDao storeDao =  (IStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("storeDao");
		Store store = storeDao.getByOwnerId(refuid);
		List<Store> stores = new ArrayList<Store>();
		stores.add(store);
		return new CouponWrapper(coupon,stores);
	}
	
	@Override
	public CouponWrapper claim(long campaignId, long ownerId, String comment) throws Exception {
		Coupon coupon  = claimCoupon(campaignId,ownerId, comment);
		return new CouponWrapper(coupon,true);
	}
	
	@Override
	public CouponWrapper claim(long campaignId, long ownerId, double lat, double lon, String comment) throws Exception {
		Coupon coupon  = claimCoupon(campaignId,ownerId, comment);
		return new CouponWrapper(coupon,lat,lon);
	}
	
	
	@Override
	public Coupon claimCoupon(long campaignId, long ownerId, String comment) throws Exception {
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");

		CouponCampaign campaign = campaignDao.findById(campaignId);
		if (!campaign.isInClaimTimeRange()) {
			throw new CampaignNotInClaimRangeException();
		}
		
		
		List<Coupon> coupons = couponDao.findByOwnerIdAndCampaignId(ownerId, campaignId);
		if (campaign.getType()!=CouponCampaignType.INSURANCE){
			if (coupons.size() > 0) {
				throw new CanNotClaimMultipleCouponException();
			}
		}
		
		if (campaign.getUnclaimedVolume()<=0L){
			CouponWarningShortMessage cwsm = new CouponWarningShortMessage(campaignId,0);
			SMS.send(cwsm);
			throw new NoMoreAvailableCouponException();
		}
		
		Coupon coupon = null;
		while (true) {
			// Randomly pick a coupon so that race condition is less likely to happen
			coupon = couponDao.findRandomClaimableCoupon(campaignId);
			
			if (campaign.getVolume()>5000L){
				if (campaign.getUnclaimedVolume()==500) {
					CouponWarningShortMessage cwsm = new CouponWarningShortMessage(campaignId,500);
					SMS.send(cwsm);
				}
				if (campaign.getUnclaimedVolume()==100) {
					CouponWarningShortMessage cwsm = new CouponWarningShortMessage(campaignId,500);
					SMS.send(cwsm);
				}
			}
			else{
				if ((long) campaign.getVolume()*0.2 == campaign.getUnclaimedVolume())
				{
					CouponWarningShortMessage cwsm = new CouponWarningShortMessage(campaignId,coupons.size());
					SMS.send(cwsm);
				}
				if ((long) campaign.getVolume()*0.1 == campaign.getUnclaimedVolume())
				{
					CouponWarningShortMessage cwsm = new CouponWarningShortMessage(campaignId,coupons.size());
					SMS.send(cwsm);
				}
			}
			if (coupon == null) {
				throw new NoMoreAvailableCouponException();
			}
			
			// Just in case that others are locking the same coupon
			if (CouponLocker.tryLock(coupon.getId())) {
				break;
			}
		}
		
		try {
			if(campaign.getType()==CouponCampaignType.INSURANCE && comment!=null && !comment.equals("")){
				{
					double[] amounts = parseInsComment(comment);
					if (amounts[0]<3L && amounts[1]<1L) throw new NotEnoughAmountException();
					coupon.setP1amount(amounts[0]); //凯润
					coupon.setP2amount(amounts[1]); //健达
				}
			}
			
			QRGenUtil.gen("function=coupon&userId="+ownerId+"&couponId="+coupon.getId()+"&campaignId="+campaignId+
					"&amount="+coupon.getAmount()+"&comment="+coupon.getComment(), 
					Env.getEnv().CouponQRDir, ""+coupon.getId());
			User owner = userDao.findById(ownerId);
			coupon.setOwner(owner);
			coupon.setClaimedAt(new Timestamp((new Date()).getTime()));			
			couponDao.save(coupon);
		} catch (Exception ex) {
			// Unlock the coupon
			throw ex;
		} finally{
			CouponLocker.unlock(coupon.getId());
		}

		campaignDao.decrementVolume(campaignId);
		return coupon;
	}
	
	
	
	@Override
	public CouponWrapper redeem(long couponId, long ownerId, long scannerId) throws Exception {
		System.out.println("User " + scannerId + " scanned coupon " + couponId);
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		IStoreDao storeDao = (IStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("storeDao");
		Coupon coupon = couponDao.findById(couponId);
		
		if (ownerId != coupon.getOwner().getUserId()) {
			throw new CanNotRedeemOthersCouponException();
		}
		
		User scanner = userDao.findById(scannerId);
		
		CouponCampaign campaign = coupon.getCampaign();
		
		if (coupon.isRedeemed()) {
			
			throw new CouponAlreadyRedeemedException();
		}
		
		if (!coupon.canBeRedeemed()){
				throw new CampaignNotInRedeemRangeException();
		}
		
		Store store = storeDao.getByOwnerId(scanner.getUserId());
		if (store==null || !campaign.getRetailerStores().contains(store)){
			throw new NotAuthorizedToScanCouponException();
		}
		
		if (couponDao.countScannedCoupon(ownerId,campaign.getId())>200L){
			throw new CanNotScanMoreException();
		}
		
		
		coupon.setScanner(scanner);
		coupon.setRedeemedAt(new Timestamp((new Date()).getTime()));
		couponDao.save(coupon);

		return new CouponWrapper(coupon);
	}
	
	
	@Override
	public HashMap<String,Object> bsfredeem(long couponId, long ownerId, long scannerId, boolean isDaren) throws Exception {
		System.out.println("User " + scannerId + " scanned coupon " + couponId);
		HashMap<String,Object> result=new HashMap<String,Object>();
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		IStoreDao storeDao = (IStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("storeDao");
		Coupon coupon = couponDao.findById(couponId);
		
		if (ownerId != coupon.getOwner().getUserId()) {
			throw new CanNotRedeemOthersCouponException();
		}
		
		User scanner = userDao.findById(scannerId);
		
		CouponCampaign campaign = coupon.getCampaign();
		
		if (coupon.isRedeemed()) {
			throw new CouponAlreadyRedeemedException();
		}
		
		if (isDaren){
			if ((((new Date()).getTime()-coupon.getClaimedAt().getTime())>(long) 3600*1000*24*31) ||
				(((new Date()).getTime()-coupon.getClaimedAt().getTime())<(long) 3600*1000*24*9)) {
					throw new CampaignNotInRedeemRangeException();
				}
		}else{
			if (((new Date()).getTime()-coupon.getClaimedAt().getTime())>(long) 3600*1000*24*31) {
					throw new CampaignNotInRedeemRangeException();
			}
		}
		
		
		
		Store store = storeDao.getByOwnerId(scanner.getUserId());
		if (store==null || !campaign.getRetailerStores().contains(store)){
			throw new NotAuthorizedToScanCouponException();
		}
		
		//Hard coded: for insurance event
		if (coupon.getType() == CouponCampaignType.INSURANCE){
			IProStockDao psdDao = (IProStockDao) ContextLoader.getCurrentWebApplicationContext().getBean("proStockDao");
			double kairunauth = 0; //凯润
			double jiandaauth = 0; //健达
			if (coupon.getCampaign().getId() == 15){ 
				kairunauth = kairunauth + 4*psdDao.computeAuth(11L, scannerId);
				kairunauth = kairunauth + 5*psdDao.computeAuth(12L, scannerId);
				kairunauth = kairunauth + 10*psdDao.computeAuth(13L, scannerId);
				kairunauth = kairunauth + 20*psdDao.computeAuth(89L, scannerId);
				kairunauth = kairunauth + 6*psdDao.computeAuth(90L, scannerId);
				kairunauth = kairunauth + 6*psdDao.computeAuth(95L, scannerId);
				kairunauth = kairunauth + 10*psdDao.computeAuth(97L, scannerId);
				kairunauth = kairunauth + 10*psdDao.computeAuth(107L, scannerId);
				kairunauth = kairunauth + 10*psdDao.computeAuth(129L, scannerId);
				kairunauth = kairunauth + 4*psdDao.computeAuth(164L, scannerId);
				kairunauth = kairunauth + 6*psdDao.computeAuth(165L, scannerId);
				kairunauth = kairunauth + 10*psdDao.computeAuth(166L, scannerId);
				kairunauth = kairunauth + 10*psdDao.computeAuth(167L, scannerId);
				kairunauth = kairunauth + 10*psdDao.computeAuth(168L, scannerId);
				kairunauth = kairunauth + 20*psdDao.computeAuth(169L, scannerId);
				kairunauth = kairunauth + 10*psdDao.computeAuth(170L, scannerId);
				kairunauth = kairunauth + 5*psdDao.computeAuth(184L, scannerId);
				kairunauth = kairunauth + 200*psdDao.computeAuth(191L, scannerId);


			    jiandaauth = jiandaauth + 4*psdDao.computeAuth(115L, scannerId);
			    jiandaauth = jiandaauth + 10*psdDao.computeAuth(116L, scannerId);
			    jiandaauth = jiandaauth + 10*psdDao.computeAuth(141L, scannerId);
			    jiandaauth = jiandaauth + 10*psdDao.computeAuth(179L, scannerId);
			    jiandaauth = jiandaauth + 10*psdDao.computeAuth(181L, scannerId);
			    jiandaauth = jiandaauth + 20*psdDao.computeAuth(182L, scannerId);
			}
			
			double kairunsum = couponDao.sumP1amount(coupon.getCampaign().getId(), scannerId)+coupon.getP1amount();
			double jiandasum = couponDao.sumP2amount(coupon.getCampaign().getId(), scannerId)+coupon.getP2amount();
			if (kairunsum>kairunauth || 
					jiandasum>jiandaauth){
				throw new NotEnoughAuthException(jiandaauth,jiandasum,kairunauth,kairunsum);
				//throw 授权已满异常
			}else{
				StringBuilder msg = new StringBuilder();
				msg.append("健达授权："+ jiandaauth + "L ");
				msg.append("已扫描："+ jiandasum + "L");
				msg.append("凯润授权："+ kairunauth + "L ");
				msg.append("已扫描："+ kairunsum + "L");
				result.put("message", msg.toString());
				
				if (campaign.getId()==15L){
					try{
						User receiver = userDao.findById(ownerId);
						EventMessage message = new EventMessage(1000L, receiver.getCellPhone(), 
								"恭喜你已经成功加入巴斯夫香蕉关爱基金活动！");
						SMS.send(message);
					}catch(Exception e){
						System.out.println("发送通知失败");
					}
				}
			}
		}
		
		if (couponDao.countScannedCoupon(ownerId,campaign.getId())>200L){
			throw new CanNotScanMoreException();
		}
		
		
		coupon.setScanner(scanner);
		coupon.setRedeemedAt(new Timestamp((new Date()).getTime()));
		couponDao.save(coupon);

		CouponWrapper cw = new CouponWrapper(coupon);
		
		result.put("coupon",cw);
		return result;
	}
	
	@Override
	public List<CouponCampaignWrapper> findClaimableCampaigns(long institutionId) {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		List<CouponCampaign> campaigns = campaignDao.findClaimable(institutionId);
		
		List<CouponCampaignWrapper> campaignWrappers = new ArrayList<CouponCampaignWrapper>();
		for (CouponCampaign campaign : campaigns) {
			campaignWrappers.add(new CouponCampaignWrapper(campaign,false));
		}
		
		return campaignWrappers;
	}
	
	
	@Override
	public List<CouponCampaignWrapper> findDarenCampaigns(long institutionId, long refuid) {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		List<CouponCampaign> campaigns = campaignDao.findClaimable(institutionId);
		
		List<CouponCampaignWrapper> campaignWrappers = new ArrayList<CouponCampaignWrapper>();
		
		IStoreDao storeDao =  (IStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("storeDao");
		Store store = storeDao.getByOwnerId(refuid);
		List<Store> stores = new ArrayList<Store>();
		stores.add(store);
		for (CouponCampaign campaign : campaigns) {
			campaignWrappers.add(new CouponCampaignWrapper(campaign,stores));
		}
		
		return campaignWrappers;
	}
	
	@Override
	public List<CouponCampaignWrapper> findClaimableCampaigns(long institutionId, double lat,double lon) {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		List<CouponCampaign> campaigns = campaignDao.findClaimable(institutionId);
		
		List<CouponCampaignWrapper> campaignWrappers = new ArrayList<CouponCampaignWrapper>();
		for (CouponCampaign campaign : campaigns) {
			campaignWrappers.add(new CouponCampaignWrapper(campaign,lat,lon));
		}
		
		return campaignWrappers;
	}

	
	@Override
	public CouponCampaignWrapper getCampaign(long campaignId, boolean expand) {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		CouponCampaign campaign = campaignDao.findById(campaignId);
		return new CouponCampaignWrapper(campaign,expand);
	}
	
	@Override
	public CouponCampaignWrapper getCampaign(long campaignId) {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		CouponCampaign campaign = campaignDao.findById(campaignId);
		return new CouponCampaignWrapper(campaign);
	}
	
	@Override
	public CouponCampaignWrapper getCampaign(long campaignId, double lat, double lon) {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		List<Store> store = new ArrayList<Store>();
		List<String> cities = new ArrayList<String>();
		CouponCampaign campaign = campaignDao.findById(campaignId);
		//First find nearby retailer store and the location of the store
		for (Store s : campaign.getRetailerStores()){
			if ((s.getLocation().getLatitude()-lat)*(s.getLocation().getLatitude()-lat)
					+(s.getLocation().getLongtitude()-lon)*(s.getLocation().getLongtitude()-lon)<0.1){
			   if (!cities.contains(s.getLocation().getCity())) cities.add(s.getLocation().getCity());
			}
		}
		//For all the store in the city, use it.
		for (Store s : campaign.getRetailerStores()){
			if (cities.contains(s.getLocation().getCity())){
				store.add(s);
			}
		}
		return new CouponCampaignWrapper(campaign, store);
	}
	
	@Override
	public CouponCampaignWrapper getDarenCampaign(long campaignId, long refuid) {
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponCampaignDao");
		IStoreDao storeDao =  (IStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("storeDao");
		Store store = storeDao.getByOwnerId(refuid);
		List<Store> stores = new ArrayList<Store>();
		stores.add(store);
		CouponCampaign campaign = campaignDao.findById(campaignId);
		return new CouponCampaignWrapper(campaign, stores);
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
	public List<CouponWrapper> findCouponsByOwnerId(long ownerId, double lat, double lon) {
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		List<Coupon> coupons = couponDao.findByOwnerId(ownerId);
		
		List<CouponWrapper> wrappers = new ArrayList<CouponWrapper>();
		for (Coupon coupon : coupons) {
			//wrappers.add(new CouponWrapper(coupon, true /* expandCampaign */));
			wrappers.add(new CouponWrapper(coupon, lat, lon));
		}
		
		return wrappers;
	}
	
	@Override
	public List<CouponWrapper> findDarenCouponsByOwnerId(long ownerId, long refuid) {
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		IStoreDao storeDao =(IStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("storeDao");
		List<Store> stores = new ArrayList<Store>();
		Store store = storeDao.getByOwnerId(refuid);
		stores.add(store);
		List<Coupon> coupons = couponDao.findByOwnerId(ownerId);
		
		List<CouponWrapper> wrappers = new ArrayList<CouponWrapper>();
		for (Coupon coupon : coupons) {
			wrappers.add(new CouponWrapper(coupon, stores));
		}
		
		return wrappers;
	}
	
	
	
	@Override
	public List<CouponWrapper> findCouponsByOwnerId(long ownerId, boolean expand) {
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		List<Coupon> coupons = couponDao.findByOwnerId(ownerId);
		
		List<CouponWrapper> wrappers = new ArrayList<CouponWrapper>();
		for (Coupon coupon : coupons) {
			wrappers.add(new CouponWrapper(coupon, expand /* expandCampaign */));
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
	
	
	@Override
	public List<CouponWrapper> findCouponsByScannerAndCampaignId(long scannerId,long campaignId){
		
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		List<Coupon> coupons = couponDao.findByScannerIdAndCampaignId(scannerId, campaignId);
		List<CouponWrapper> wrappers = new ArrayList<CouponWrapper>();
		for (Coupon coupon : coupons){
			try{
				wrappers.add(new CouponWrapper(coupon));
			}catch(Exception e){
				//@Xiyao: Since there are no strict foreign key constrain on the DB side, 
				// sometimes null pointer causing issue.
				// Monitor such kind of exception and enhance DB constrain while guarantee ORM works properly
			}
		}
		return wrappers;
	
	}
	
	
	@Override
	public List<CouponCampaignWrapper> findCampaginsByScannerId(long scannerId){
		IStoreDao storeDao = (IStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("storeDao");
		Store store = storeDao.getByOwnerId(scannerId);
	
		List<CouponCampaignWrapper> wrappers = new ArrayList<CouponCampaignWrapper>();
		if (store!=null){
			for (CouponCampaign couponCampaign : store.getCouponCampaigns()){
				try{
					wrappers.add(new CouponCampaignWrapper(couponCampaign,false));
				}catch(Exception e){
					//@Xiyao: Since there are no strict foreign key constrain on the DB side, 
					// sometimes null pointer causing issue.
					// Monitor such kind of exception and enhance DB constrain while guarantee ORM works properly
				}
			}
		}
		return wrappers;
	
	}
	
	@Override
	public Date getCouponClaimTime(long couponId){
		ICouponDao couponDao = (ICouponDao) ContextLoader.getCurrentWebApplicationContext().getBean("couponDao");
		Coupon cou = couponDao.findById(couponId);
		if (cou!=null)
			return cou.getClaimedAt();	
		else return new Date();
	}

	private double[] parseInsComment(String comment){
		double[] result = new double[2];
		String[] contents= comment.split(";");
		result[0] = Double.parseDouble(contents[0].split(":")[1]); //凯润
		result[1] = Double.parseDouble(contents[1].split(":")[1]); //健达
		return result;
	}
}
