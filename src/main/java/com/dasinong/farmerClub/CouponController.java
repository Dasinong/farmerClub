package com.dasinong.farmerClub;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.coupon.CouponCampaignType;
import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.exceptions.UserIsNotLoggedInException;
import com.dasinong.farmerClub.facade.ICouponFacade;
import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;
import com.dasinong.farmerClub.outputWrapper.GroupedScannedCouponsWrapper;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class CouponController extends RequireUserLoginController {
	
	@RequestMapping(value = "/claimCoupon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object claimCoupon(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		double lat = requestX.getDoubleOptional("lat",0.00);
		double lon = requestX.getDoubleOptional("lon",0.00);
		
		Long campaignId = requestX.getLong("campaignId");
		//Long amount = requestX.getLongOptional("amount", -1L);
		String comment = requestX.getStringOptional("comment", "");
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		CouponWrapper coupon; 
		
		CouponCampaignWrapper cc = facade.getCampaign(campaignId);
		if (cc.type!=CouponCampaignType.INSURANCE)
		{
			if ("jiandadaren".equals(user.getUserType()) && (user.getRefuid()!=null)){
				coupon = facade.darenClaim(campaignId, user.getUserId(),user.getRefuid());
			}else{
				if (lat==0.00 ||lon==0.00)
				{
					if (user.getFields()!=null && user.getFields().size()>0){
						ILocationDao locationDao = (ILocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
						//No session here. Manually check here. Consider to hide to transaction in facade in refactor. 
						Long lid = user.getFields().iterator().next().getLocation().getLocationId();
						Location l = locationDao.findById(lid);
						lat = l.getLatitude();
						lon = l.getLongtitude();
						coupon =facade.claim(campaignId, user.getUserId(),lat,lon,"");
					}else{
						coupon = facade.claim(campaignId, user.getUserId(),"");
					}
				}
				else{
					coupon =facade.claim(campaignId, user.getUserId(),lat,lon,"");
				}
			}
		}else{
			if (lat==0.00 ||lon==0.00)
			{
				if (user.getFields()!=null && user.getFields().size()>0){
					ILocationDao locationDao = (ILocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
					//No session here. Manually check here. Consider to hide to transaction in facade in refactor. 
					Long lid = user.getFields().iterator().next().getLocation().getLocationId();
					Location l = locationDao.findById(lid);
					lat = l.getLatitude();
					lon = l.getLongtitude();
					coupon =facade.claim(campaignId,user.getUserId(), lat,lon,comment);
				}else{
					coupon = facade.claim(campaignId, user.getUserId(),comment);
				}
			}
			else{
				coupon =facade.claim(campaignId, user.getUserId(),lat,lon,comment);
			}
		}
		
		
		data.put("coupon", coupon);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;	
	}
	
	@RequestMapping(value = "/redeemCoupon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object redeemCoupon(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		
		long couponId = requestX.getLong("couponId");
		long userId = requestX.getLong("userId");
		
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		CouponWrapper coupon;
		if(user.getInstitutionId() == 3L){
			data = facade.bsfredeem(couponId, userId, user.getUserId(),"jiandadaren".equals(user.getUserType()));
			result.put("respCode", 200);
			result.put("message", data.get("message"));
			result.put("data", data);
		}else{
			coupon = facade.redeem(couponId, userId, user.getUserId());
			data.put("coupon", coupon);
			result.put("respCode", 200);
			result.put("message", "获取成功");
			result.put("data", data);
		}

		
		return result;	
	}
	
	@RequestMapping(value = "/getCoupons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		
		
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		double lat = requestX.getDoubleOptional("lat",0.00);
		double lon = requestX.getDoubleOptional("lon",0.00);
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		List<CouponWrapper> coupons;
		
		if ("jiandadaren".equals(user.getUserType()) && (user.getRefuid()!=null)){
			coupons = facade.findDarenCouponsByOwnerId(user.getUserId(), user.getRefuid());
		}else{//other check by location
			if (lat==0.00 ||lon==0.00)
			{
				if (user.getFields()!=null && user.getFields().size()>0){
					ILocationDao locationDao = (ILocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
					//No session here. Manually check here. Consider to hide to transaction in facade in refactor. 
					Long lid = user.getFields().iterator().next().getLocation().getLocationId();
					Location l = locationDao.findById(lid);
					lat = l.getLatitude();
					lon = l.getLongtitude();
					coupons = facade.findCouponsByOwnerId(user.getUserId(), lat, lon);
				}
				else{
					coupons = facade.findCouponsByOwnerId(user.getUserId());
				}
			}
			else{
				coupons = facade.findCouponsByOwnerId(user.getUserId(), lat, lon);
			}
		}
	
		data.put("coupons", coupons);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;	
	}
	
	@RequestMapping(value = "/getScannedCouponsGroupByCampaign", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getScannedCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		GroupedScannedCouponsWrapper groupedCoupons = facade.groupScannedCouponsByCampaignId(user.getUserId());
		
		data.put("groupedCoupons", groupedCoupons);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;	
	}
	
	@RequestMapping(value = "/requestCoupon", method = RequestMethod.POST)
	@ResponseBody
	public Object postRequestCoupon(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String name = requestX.getString("name");
		String company = requestX.getString("company");
		String crop = requestX.getString("crop");
		double area = requestX.getDouble("area");
		double yield = requestX.getDouble("yield");
		String experience = requestX.getString("experience");  
		String productUseHistory = requestX.getString("productUseHistory");
		String contactNumber = requestX.getString("contactNumber");
		
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		facade.saveCouponRequest(name,company,crop,area,yield,experience,productUseHistory,contactNumber);
		
		result.put("respCode", 200);
		result.put("message", "认领成功");
		return result;	
	}
	
	
	@RequestMapping(value = "/getScannedCouponsByCampaignId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getScannedCouponsByCampaignId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		User user = this.getLoginUser(request);
		long campaignId = requestX.getLong("campaignId");
		
		if(user!=null){
			ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
			List<CouponWrapper> coupons = facade.findCouponsByScannerAndCampaignId(user.getUserId(), campaignId);
			data.put("coupons", coupons);
			CouponCampaignWrapper campaign = facade.getCampaign(campaignId,false);
			data.put("campaign", campaign);
		}
		else throw new UserIsNotLoggedInException();
		
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;	
	}
}
