package com.dasinong.farmerClub;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.facade.ICropFacade;
import com.dasinong.farmerClub.outputWrapper.CropDetailsWrapper;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class CropController extends RequireUserLoginController {

	@RequestMapping(value = "/getCropDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCropDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		ICropFacade facade = (ICropFacade) ContextLoader.getCurrentWebApplicationContext().getBean("cropFacade");
		
		Long cropId = requestX.getLong("id");
		Long subStageId = requestX.getLongOptional("subStageId", null);
		CropDetailsWrapper crop;
		if (subStageId == null) {
			crop = facade.getCropDetailsById(cropId);
		} else {
			crop = facade.getCropDetailsByIdAndSubStageId(cropId, subStageId);
		}
		
		data.put("crop", crop);
		result.put("respCode", 200);
		result.put("respMsg", "成功");
		result.put("data", data);
		return result;
	}
}
