package com.dasinong.farmerClub;

import java.util.HashMap;
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

import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.dao.LocationDao;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.outputWrapper.LocationWrapper;
import com.dasinong.farmerClub.util.GeoUtil;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class LocationController extends BaseController {

	@RequestMapping(value = "/getLocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getLocation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		String province = requestX.getString("province");
		String city = requestX.getString("city");
		String country = requestX.getString("country");
		String district = requestX.getString("district");

		ILocationDao ld = (ILocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
		List<Location> ls = (List<Location>) ld.getIdList(province, city, country, district);
		Map<String, Long> idlist = new HashMap<String, Long>();
		for (Location l : ls) {
			if (!l.getCommunity().equals("")) {
				idlist.put(l.getCommunity(), l.getLocationId());
			} else {
				idlist.put(l.getDistrict(), l.getLocationId());
			}
		}

		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", idlist);

		return result;
	}

	@RequestMapping(value = "/searchLocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object searchLocation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		String province = requestX.getString("province");
		String city = requestX.getString("city");
		String country = requestX.getString("country");
		Double lat = requestX.getDouble("lat");
		Double lon = requestX.getDouble("lon");

		LocationDao ld = (LocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
		List<Location> ls = (List<Location>) ld.getHibernateTemplate()
				.find("from Location where province=? and city=? and country=?", province, city, country);
		GeoUtil geo = new GeoUtil(ls);
		Location nearest = geo.getNearLoc(lat, lon);
		LocationWrapper nearl = new LocationWrapper(nearest);
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", nearl);

		return result;
	}
}
