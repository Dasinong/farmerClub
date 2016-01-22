package com.dasinong.farmerClub;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasinong.farmerClub.model.DasinongApp;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class GateKeeperController extends BaseController {

	@RequestMapping(value = "/gatekeepers", produces = "application/json")
	@ResponseBody
	public Object getGateKeepers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		int appId = requestX.getInt("appId");
		HashMap<String, Object> gatekeepers = new HashMap<String, Object>();
		
		// Temporary change
		// if (appId == DasinongApp.IOS_FARM_LOG) {
			gatekeepers.put("qqLogin", true);
			gatekeepers.put("weixinLogin", true);
		//}
		
		return gatekeepers;
	}
}
