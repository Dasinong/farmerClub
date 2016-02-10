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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.dao.IWeatherSubscriptionDao;
import com.dasinong.farmerClub.datapool.AllMonitorLocation;
import com.dasinong.farmerClub.exceptions.MissingParameterException;
import com.dasinong.farmerClub.exceptions.ResourceNotFoundException;
import com.dasinong.farmerClub.exceptions.UserNotFoundInSessionException;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.WeatherSubscription;
import com.dasinong.farmerClub.model.WeatherSubscriptionType;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class WeatherSubscriptionController extends RequireUserLoginController {

	@RequestMapping(value = "/weatherSubscriptions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getWeatherSubscriptions(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);

		IWeatherSubscriptionDao weatherSubsDao = (IWeatherSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("weatherSubscriptionDao");

		List<WeatherSubscription> subs = weatherSubsDao.findByUserId(user.getUserId());
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", subs);
		return result;
	}

	@RequestMapping(value = "/weatherSubscriptions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getWeatherSubscription(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Long id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);

		IWeatherSubscriptionDao weatherSubsDao = (IWeatherSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("weatherSubscriptionDao");

		WeatherSubscription subs = weatherSubsDao.findById(id);
		if (subs == null || !subs.getUserId().equals(user.getUserId())) {
			throw new ResourceNotFoundException(id, "WeatherSubscription");
		}

		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", subs);
		return result;
	}

	@RequestMapping(value = "/weatherSubscriptions/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object deleteWeatherSubscription(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Long id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);

		IWeatherSubscriptionDao weatherSubsDao = (IWeatherSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("weatherSubscriptionDao");

		WeatherSubscription subs = weatherSubsDao.findById(id);
		if (subs == null || !subs.getUserId().equals(user.getUserId())) {
			throw new ResourceNotFoundException(id, "WeatherSubscription");
		}

		weatherSubsDao.delete(subs);
		result.put("respCode", 200);
		result.put("message", "删除成功");
		return result;
	}

	@RequestMapping(value = "/reorderWeatherSubscriptions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object reorderWeatherSubscriptions(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ids[]") Long[] ids) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);

		IWeatherSubscriptionDao weatherSubsDao = (IWeatherSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("weatherSubscriptionDao");
		weatherSubsDao.updateOrdering(ids);

		result.put("respCode", 200);
		result.put("message", "排序成功");

		return result;
	}

	@RequestMapping(value = "/weatherSubscriptions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object createWeatherSubscription(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);

		Long locationId = requestX.getLong("locationId");

		ILocationDao locDao = (ILocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
		Location loc = locDao.findById(locationId);
		if (loc == null) {
			throw new ResourceNotFoundException(locationId, "location");
		}
		AllMonitorLocation allLocation = AllMonitorLocation.getInstance();
		Long monitorLocationId = (long) allLocation.getNearest(loc.getLatitude(), loc.getLongtitude());

		String locationName = loc.toString();

		WeatherSubscription subs = new WeatherSubscription();
		subs.setLocationId(locationId);
		subs.setMonitorLocationId(monitorLocationId);
		subs.setLocationName(locationName);
		subs.setUserId(user.getUserId());
		subs.setType(WeatherSubscriptionType.NORMAL);

		IWeatherSubscriptionDao weatherSubsDao = (IWeatherSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("weatherSubscriptionDao");

		weatherSubsDao.save(subs);
		int rtyCount=0;
		while(subs.getWeatherSubscriptionId()==null && rtyCount<5){
			rtyCount++;
			Thread.sleep(50);
		}
		result.put("respCode", 200);
		result.put("message", "创建成功");
		result.put("data", subs);

		return result;
	}

}
