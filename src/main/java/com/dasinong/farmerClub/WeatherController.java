package com.dasinong.farmerClub;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.xml.sax.SAXException;

import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.exceptions.InvalidParameterException;
import com.dasinong.farmerClub.facade.IWeatherFacade;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class WeatherController extends BaseController {

	IWeatherFacade wf;

	@RequestMapping(value = "/loadWeather", produces = "application/json")
	@ResponseBody
	public Object loadWeather(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		User user = this.getLoginUser(request);

		Map<String, Object> result = new HashMap<String, Object>();
		IWeatherFacade wf = (IWeatherFacade) ContextLoader.getCurrentWebApplicationContext().getBean("weatherFacade");

		if (user == null) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			return wf.getWeather(lat, lon);
		}

		Long mlid = requestX.getLongOptional("monitorLocationId", -1L);
		if (mlid == -1L) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			if (user.getRegion() == null || user.getRegion().isEmpty()){
				wf.setUserRegion(user, lat, lon);
			}
			return wf.getWeather(lat, lon);
		}

		if (user.getRegion() == null || user.getRegion().isEmpty()){
			wf.setUserRegion(user, mlid);
		}
		return wf.getWeather(mlid);
	}

}
