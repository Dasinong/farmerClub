package com.dasinong.farmerClub;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.exceptions.InvalidParameterException;
import com.dasinong.farmerClub.facade.ISubScribeFacade;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class SubScribeController extends RequireUserLoginController {

	@RequestMapping(value = "/insertSubScribeList", produces = "application/json")
	@ResponseBody
	public Object insertSubScribeList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = this.getLoginUser(request);
		Map<String, Object> result = new HashMap<String, Object>();

		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String targetName = requestX.getString("targetName");
		String cellphone = requestX.getString("cellphone");
		String province = requestX.getString("province");
		String city = requestX.getString("city");
		String country = requestX.getString("country");
		String district = requestX.getString("district");
		// Check parameter here
		String cropName = requestX.getString("cropId");
		// Long cropId = requestX.getString("");
		double area = requestX.getDouble("area");
		boolean isAgriWeather = requestX.getBool("isAgriWeather");
		boolean isNatAlter = requestX.getBool("isNatAlter");
		boolean isRiceHelper = requestX.getBool("isRiceHelper");
		;

		ISubScribeFacade ssf = (ISubScribeFacade) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeFacade");
		try {
			Long cropId = Long.parseLong(cropName);
			return ssf.insertSubScribeList(user, targetName, cellphone, province, city, country, district, area, cropId,
					isAgriWeather, isNatAlter, isRiceHelper);
		} catch (Exception e) {
			return ssf.insertSubScribeList(user, targetName, cellphone, province, city, country, district, area,
					cropName, isAgriWeather, isNatAlter, isRiceHelper);
		}
	}

	@RequestMapping(value = "/getSubScribeLists", produces = "application/json")
	@ResponseBody
	public Object getSubScribeLists(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = this.getLoginUser(request);
		Map<String, Object> result = new HashMap<String, Object>();

		ISubScribeFacade ssf = (ISubScribeFacade) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeFacade");
		return ssf.getSubScribeLists(user);
	}

	@RequestMapping(value = "/loadSubScribeList", produces = "application/json")
	@ResponseBody
	public Object loadSubScribeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		Long id = requestX.getLong("id");
		ISubScribeFacade ssf = (ISubScribeFacade) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeFacade");
		return ssf.loadSubScribeList(id);
	}

	@RequestMapping(value = "/updateSubScribeList", produces = "application/json")
	@ResponseBody
	public Object updateSubScribeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.getLoginUser(request);

		HttpServletRequestX requestX = new HttpServletRequestX(request);
		Long id = requestX.getLong("id");
		String targetName = requestX.getString("targetName");
		String cellphone = requestX.getString("cellphone");
		String province = requestX.getString("province");
		String city = requestX.getString("city");
		String country = requestX.getString("country");
		String district = requestX.getString("district");
		double area = requestX.getDouble("area");
		String cropName = requestX.getString("cropName");
		boolean isAgriWeather = requestX.getBool("isAgriWeather");
		boolean isNatAlter = requestX.getBool("isNatAlter");
		boolean isRiceHelper = requestX.getBool("isRiceHelper");

		ISubScribeFacade ssf = (ISubScribeFacade) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeFacade");
		try {
			Long cropId = Long.parseLong(cropName);
			return ssf.updateSubScribeList(id, user, targetName, cellphone, province, city, country, district, area,
					cropId, isAgriWeather, isNatAlter, isRiceHelper);
		} catch (Exception e) {
			return ssf.updateSubScribeList(id, user, targetName, cellphone, province, city, country, district, area,
					cropName, isAgriWeather, isNatAlter, isRiceHelper);
		}
	}

	@RequestMapping(value = "/deleteSubScribeList", produces = "application/json")
	@ResponseBody
	public Object deleteSubScribeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		Long id = requestX.getLong("id");
		ISubScribeFacade ssf = (ISubScribeFacade) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeFacade");
		return ssf.deleteSubScribeList(id);
	}
}
