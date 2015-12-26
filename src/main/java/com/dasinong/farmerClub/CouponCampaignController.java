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
public class CouponCampaignController extends RequireUserLoginController {
	
	@RequestMapping(value = "/createCouponCampaign", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object testCoupon(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		facade.createCampaign();
		return null;
	}
	
	@RequestMapping(value = "/deleteCouponCampaign", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object deleteCouponCampaign(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		Long campaignId = requestX.getLong("id");
		facade.deleteCampaign(campaignId);
		
		data.put("success", true);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", true);
		return result;
	}
	
	@RequestMapping(value = "/couponCampaigns/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCouponCampaign(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		
		data.put("campaign", facade.getCampaign(id));
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;	
	}
	
	@RequestMapping(value = "/couponCampaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getWeatherSubscriptions(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		List<CouponCampaignWrapper> campaigns = facade.findClaimableCampaigns();
		data.put("campaigns", campaigns);
		
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;
	}
}
