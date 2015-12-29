package com.dasinong.farmerClub;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.facade.IHomeFacade;
import com.dasinong.farmerClub.facade.ILaoNongFacade;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class HomeController extends BaseController {

	@RequestMapping(value = "/home", produces = "application/json")
	@ResponseBody
	public Object home(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.getLoginUser(request);
		IHomeFacade hf = (IHomeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("homeFacade");
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		// 用户未登陆,或用户没有田地,需根据地点获取基础信息
		if (user == null) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			return hf.LoadHome(lat, lon);
		}

		if (user.getFields() == null || user.getFields().size() == 0) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			return hf.LoadHome(lat, lon);
		}

		Long fId = requestX.getLongOptional("fieldId", null);
		if (fId == null) {
			fId = user.getFields().iterator().next().getFieldId(); // 用户没有指定田,默认使用第一片
		}

		// 如果没有田地,输入fieldId=-1;
		if (fId == -1L) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			return hf.LoadHome(lat, lon);
		}

		return hf.LoadHome(user, fId);
	}

	// TODO (xiahonggao): deprecate /getLaoNong and use /laonongs instead
	@RequestMapping(value = "/getLaoNong", produces = "application/json")
	@ResponseBody
	public Object getLaoNong(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.getLoginUser(request);
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		ILaoNongFacade lnf = (ILaoNongFacade) ContextLoader.getCurrentWebApplicationContext().getBean("laoNongFacade");

		// 用户未登陆,或用户没有田地,需根据地点获取基础信息
		if (user == null) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			return lnf.getLaoNongs_DEPRECATED(lat, lon, user);
		}

		if (user.getFields() == null || user.getFields().size() == 0) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			return lnf.getLaoNongs_DEPRECATED(lat, lon, user);
		}

		Long mlId = requestX.getLongOptional("monitorLocationId", null);
		if (mlId == null) {
			mlId = user.getFields().iterator().next().getMonitorLocationId(); // 用户没有指定田,默认使用第一片
		}

		// 如果没有田地,输入fieldId=-1;
		if (mlId == -1L) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			return lnf.getLaoNongs_DEPRECATED(lat, lon, user);
		}
		return lnf.getLaoNongs_DEPRECATED(mlId, user);
	}

	@RequestMapping(value = "/laonongs", produces = "application/json")
	@ResponseBody
	public Object getBanners(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.getLoginUser(request);
		ILaoNongFacade lnf = (ILaoNongFacade) ContextLoader.getCurrentWebApplicationContext().getBean("laoNongFacade");
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		if (user == null) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			return lnf.getLaoNongs(lat, lon, user);
		}

		Long mlId = requestX.getLongOptional("monitorLocationId", -1L);
		if (mlId == -1L) {
			double lat = requestX.getDouble("lat");
			double lon = requestX.getDouble("lon");
			return lnf.getLaoNongs(lat, lon, user);			
		}
		return lnf.getLaoNongs(mlId, user);
	}

}
