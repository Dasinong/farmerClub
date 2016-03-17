package com.dasinong.farmerClub;

import java.util.ArrayList;
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

import com.dasinong.farmerClub.coupon.CustomizeCouponCampaignFilter;
import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.exceptions.UserIsNotLoggedInException;
import com.dasinong.farmerClub.facade.ICouponFacade;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.CouponCampaignWrapper;
import com.dasinong.farmerClub.outputWrapper.CouponWrapper;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class CouponCampaignController extends RequireUserLoginController {
	
	@RequestMapping(value = "/couponCampaigns/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCouponCampaign(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
	
		ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
		CouponCampaignWrapper campaign;
		User user = this.getLoginUser(request);
		if (user!=null){
			HttpServletRequestX requestX = new HttpServletRequestX(request);
			double lat = requestX.getDoubleOptional("lat",0.00);
			double lon = requestX.getDoubleOptional("lon",0.00);
					
			if ("jiandadaren".equals(user.getUserType()) && (user.getRefuid()!=null)){
				campaign = facade.getDarenCampaign(id,user);
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
						campaign =facade.getCampaign(id,lat,lon);
					}else{
						campaign = facade.getCampaign(id);
					}
				}
				else{
					campaign =facade.getCampaign(id,lat,lon);
				}
			}
			data.put("campaign", campaign);
			result.put("respCode", 200);
			result.put("message", "获取成功");
			result.put("data", data);
		}
		else throw (new UserIsNotLoggedInException());
		String couponId = request.getParameter("couponId");
		if (couponId!=null && !couponId.equals("")){
			result.put("claimAt", facade.getCouponClaimTime(Long.parseLong(couponId)));
		}

		return result;	
	}
	
	@RequestMapping(value = "/couponCampaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCouponCampaigns(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		
		if (user!=null){
			HttpServletRequestX requestX = new HttpServletRequestX(request);
			double lat = requestX.getDoubleOptional("lat",0.00);
			double lon = requestX.getDoubleOptional("lon",0.00);
			ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
			List<CouponCampaignWrapper> campaigns;
			
			//daren will use the location from its recommender
			if ("jiandadaren".equals(user.getUserType()) && (user.getRefuid()!=null)){
				campaigns = facade.findDarenCampaigns(user.getInstitutionId(),user);
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
						campaigns = facade.findClaimableCampaigns(user.getInstitutionId(),lat,lon);
					}
					else{
						campaigns = facade.findClaimableCampaigns(user.getInstitutionId());
					}
				}
				else{
					campaigns = facade.findClaimableCampaigns(user.getInstitutionId(),lat,lon);
				}
			}
			
			List<CouponWrapper> coupons = facade.findCouponsByOwnerId(user.getUserId(),false);
			if (coupons!=null){
				data.put("coupons", coupons);
			}
			
			if (user.getInstitutionId() == 3L){
				List<CouponCampaignWrapper> bsfcampaigns = new ArrayList<CouponCampaignWrapper>();
				if (user.getUserType().equals("jiandadaren")){
					for(CouponCampaignWrapper campaign : campaigns){
						if (CustomizeCouponCampaignFilter.isDarenEvent(campaign.id)) bsfcampaigns.add(campaign);
					}
				}else{
					for(CouponCampaignWrapper campaign : campaigns){
						if (CustomizeCouponCampaignFilter.isFarmerEvent(campaign.id)) bsfcampaigns.add(campaign);
					}
				}
				data.put("campaigns", bsfcampaigns);
			}
			else{
				data.put("campaigns", campaigns);
			}
		}
		else throw (new UserIsNotLoggedInException());
		
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;
	}
	
	

	@RequestMapping(value = "/getScannableCampaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getScannableCampaigns(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		
		if (user!=null){
			ICouponFacade facade = (ICouponFacade) ContextLoader.getCurrentWebApplicationContext().getBean("couponFacade");
			List<CouponCampaignWrapper> campaigns = facade.findCampaginsByScannerId(user.getUserId());
			data.put("campaigns", campaigns);
		}
		else throw (new UserIsNotLoggedInException());
		
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;
	}
}
