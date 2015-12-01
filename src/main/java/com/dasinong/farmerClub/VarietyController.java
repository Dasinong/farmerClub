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
import com.dasinong.farmerClub.exceptions.MissingParameterException;
import com.dasinong.farmerClub.facade.IVarietyFacade;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class VarietyController extends BaseController {

	@RequestMapping(value = "/getVarietyList", produces = "application/json")
	@ResponseBody
	public Object getVariety(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String cropName = requestX.getStringOptional("cropName", "");
		Long cropId = requestX.getLongOptional("cropId", null);
		Long locationId = requestX.getLongOptional("locationId", null);
		String province = requestX.getStringOptional("province", "");

		if (cropId != null && !province.equals("")) {
			IVarietyFacade vf = (IVarietyFacade) ContextLoader.getCurrentWebApplicationContext()
					.getBean("varietyFacade");
			return vf.getVariety(cropId, province);
		}

		if (cropId != null && locationId != null) {
			IVarietyFacade vf = (IVarietyFacade) ContextLoader.getCurrentWebApplicationContext()
					.getBean("varietyFacade");
			return vf.getVariety(cropId, locationId);
		}

		if (!cropName.equals("") && !province.equals("")) {
			IVarietyFacade vf = (IVarietyFacade) ContextLoader.getCurrentWebApplicationContext()
					.getBean("varietyFacade");
			return vf.getVariety(cropName, province);
		}

		if (!cropName.equals("") && locationId != null) {
			IVarietyFacade vf = (IVarietyFacade) ContextLoader.getCurrentWebApplicationContext()
					.getBean("varietyFacade");
			return vf.getVariety(cropName, locationId);
		}

		throw new MissingParameterException();
	}

}
