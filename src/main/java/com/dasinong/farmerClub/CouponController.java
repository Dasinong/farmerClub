package com.dasinong.farmerClub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.facade.ICouponFacade;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class CouponController extends RequireUserLoginController {
	
	@RequestMapping(value = "/claimCoupon", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object claimCoupon(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		
		Long campaignId = requestX.getLong("campaignId");
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		CouponWrapper coupon = facade.claim(campaignId, user.getUserId());
		
		data.put("coupon", coupon);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;	
	}
	
	@RequestMapping(value = "/redeemCoupon", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object redeemCoupon(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		
		long couponId = requestX.getLong("couponId");
		long userId = requestX.getLong("userId");
		
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		CouponWrapper coupon = facade.redeem(couponId, userId, user.getUserId());
		
		data.put("coupon", coupon);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;	
	}
	
	@RequestMapping(value = "/getCoupons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		List<CouponWrapper> coupons = facade.findCouponsByOwnerId(user.getUserId());
		
		data.put("coupons", coupons);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;	
	}
	
	@RequestMapping(value = "/getScannedCoupons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getScannedCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		List<CouponWrapper> coupons = facade.findCouponsByScannerId(user.getUserId());
		
		data.put("coupons", coupons);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;	
	}
}