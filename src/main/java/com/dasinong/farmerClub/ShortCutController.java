package com.dasinong.farmerClub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.LocationDao;
import com.dasinong.farmerClub.datapool.AllMonitorLocation;
import com.dasinong.farmerClub.exceptions.InvalidParameterException;
import com.dasinong.farmerClub.exceptions.UnexpectedLatAndLonException;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.LocationWrapper;
import com.dasinong.farmerClub.util.GeoUtil;
import com.dasinong.farmerClub.util.HttpServletRequestX;

//Add some short cut here to check/sync frontend and backend status
@Controller
public class ShortCutController extends BaseController {

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkIsLogin", produces = "application/json")
	@ResponseBody
	public Object checkLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		if (user == null) {
			result.put("respCode", 100);
			result.put("message", "尚未登陆");
			result.put("data", false); // Check when this is used. Should
										// deprecate this function.
			return result;
		} else {
			result.put("respCode", 200);
			result.put("message", "用户已登陆");
			result.put("data", true);
			return result;
		}

	}

	@RequestMapping(value = "/getMonLocIdByGeo", produces = "application/json")
	@ResponseBody
	public Object getMonLocIdByGeo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		double lat = requestX.getDouble("lat");
		double lon = requestX.getDouble("lon");

		try {
			Long mlId = AllMonitorLocation.getInstance().getNearest(lat, lon);
			result.put("respCode", 200);
			result.put("message", "获得监控地址成功");
			result.put("data", mlId);
			return result;
		} catch (Exception e) {
			throw new UnexpectedLatAndLonException(lat, lon);
		}
	}

	@RequestMapping(value = "/searchLocationByLatAndLon", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object searchLocationByLatAndLon(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		double lat = requestX.getDouble("lat");
		double lon = requestX.getDouble("lon");

		LocationDao ld = (LocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
		Location nearest = null;

		// normally you can find hundreds of locations with range = 0.01
		// and thousands of locations with range = 0.1
		// and tens of thousands of locations with range = 1
		double[] ranges = { 0.01, 0.1, 1 };
		for (double range : ranges) {
			List<Location> ls = (List<Location>) ld.findLocationsInRange(lat, lon, range);
			if (ls != null && ls.size() > 0) {
				GeoUtil geo = new GeoUtil(ls);
				nearest = geo.getNearLoc(lat, lon);
				break;
			}
		}

		if (nearest == null) {
			// This should NEVER happen!
			throw new UnexpectedLatAndLonException(lat, lon);
		}

		LocationWrapper locWrapper = new LocationWrapper(nearest);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", locWrapper);

		return result;
	}

}
