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

import com.dasinong.farmerClub.crop.CropsForInstitution;
import com.dasinong.farmerClub.crop.CropsWithSubstage;
import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.dao.ICropSubscriptionDao;

import com.dasinong.farmerClub.exceptions.ResourceNotFoundException;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.CropWrapper;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.CropSubscription;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class CropSubscriptionController extends RequireUserLoginController {

	@RequestMapping(value = "/getSubscriableCrops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getSubscriableCrops(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		Map<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		User user = this.getLoginUser(request);
		Long instId = user.getInstitutionId();
		Long[] cropIds = null;
		if (instId > 0) {
			cropIds = CropsForInstitution.getIds(instId);
		} else {
			cropIds = CropsWithSubstage.getIds();
		}
		
		List<Crop> crops = cropDao.findByIds(cropIds);
		List<CropWrapper> cropWrappers = new ArrayList<CropWrapper>();
		for (Crop crop : crops) {
			cropWrappers.add(new CropWrapper(crop));
		}
		
		data.put("crops", cropWrappers);	
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;
	}
	
	@RequestMapping(value = "/cropSubscriptions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCropSubscriptions(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		User user = this.getLoginUser(request);

		ICropSubscriptionDao cropSubsDao = (ICropSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("cropSubscriptionDao");

		List<CropSubscription> subs = cropSubsDao.findByUserId(user.getUserId());
		
		data.put("subscriptions", subs);	
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;
	}
	
	@RequestMapping(value = "/cropSubscriptions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCropSubscription(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Long id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		User user = this.getLoginUser(request);

		ICropSubscriptionDao cropSubsDao = (ICropSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("cropSubscriptionDao");

		CropSubscription subs = cropSubsDao.findById(id);
		if (subs == null || (subs.getUserId() != user.getUserId())) {
			throw new ResourceNotFoundException(id, "CropSubscription");
		}

		data.put("subscription", subs);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", data);
		return result;
	}

	@RequestMapping(value = "/cropSubscriptions/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object deleteCropSubscription(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Long id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);

		ICropSubscriptionDao cropSubsDao = (ICropSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("cropSubscriptionDao");

		CropSubscription subs = cropSubsDao.findById(id);
		if (subs == null || subs.getUserId() != user.getUserId()) {
			throw new ResourceNotFoundException(id, "CropSubscription");
		}

		cropSubsDao.delete(subs);
		result.put("respCode", 200);
		result.put("message", "删除成功");
		return result;
	}
	
	@RequestMapping(value = "/cropSubscriptions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object createCropSubscription(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		ICropSubscriptionDao cropSubsDao = (ICropSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("cropSubscriptionDao");

		HashMap<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);

		Long[] cropIds = requestX.getArrayOfLongOptional("cropId", null);
		if (cropIds == null) {
			cropIds = new Long[1];
			cropIds[0] = requestX.getLong("cropId");
		}
	
		List<CropSubscription> existingSubs = cropSubsDao.findByUserId(user.getUserId());
		List<Long> subscribedCropIds = new ArrayList<Long>();
		for (CropSubscription subs : existingSubs) {
			subscribedCropIds.add(subs.getCropId());
		}
		
		ArrayList<CropSubscription> subs = new ArrayList<CropSubscription>();
		for (Long cropId : cropIds) {
			// if crop has been subscribed, skip it
			if (subscribedCropIds.contains(cropId)) {
				continue;
			}
			
			Crop crop = cropDao.findById(cropId);
			if (crop == null) {
				throw new ResourceNotFoundException(cropId, "crop");
			}
			
			CropSubscription sub = new CropSubscription();
			sub.setCropId(cropId);
			sub.setCropName(crop.getCropName());
			sub.setUserId(user.getUserId());
			cropSubsDao.save(sub);
			
			subs.add(sub);
		}
		
		data.put("subscriptions", subs);
		result.put("respCode", 200);
		result.put("message", "创建成功");
		result.put("data", data);

		return result;
	}
}