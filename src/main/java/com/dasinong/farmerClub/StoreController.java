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


import com.dasinong.farmerClub.facade.IStoreFacade;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.StoreWrapper;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class StoreController extends RequireUserLoginController {

	@RequestMapping(value = "/stores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object CreateStore(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IStoreFacade facade = (IStoreFacade) ContextLoader.getCurrentWebApplicationContext().getBean("storeFacade");
		User user = this.getLoginUser(request);
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		String name = requestX.getString("name");
		String desc = requestX.getString("desc");
		Long locationId = requestX.getLong("locationId");
		String streetAndNumber = requestX.getString("streetAndNumber");
		Double latitude = requestX.getDoubleOptional("latitude", null);
		Double longtitude = requestX.getDoubleOptional("longtitude", null);
		String contactName = requestX.getString("contactName");
		String phone = requestX.getString("phone");
		int type = requestX.getInt("type");
		StoreSource source = StoreSource.values()[requestX.getInt("source")];

		
		Map<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> data = new HashMap<String, Object>();
		StoreWrapper sw = facade.createOrUpdate(user, name, desc, locationId, streetAndNumber, latitude, longtitude, contactName, phone,
				source, type);
		data.put("store", sw);
		result.put("respCode", 200);
		result.put("message", "农资店创建/更新成功");
		result.put("data", data);
		return result;
	}
	
	@RequestMapping(value = "/stores", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getStore(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IStoreFacade facade = (IStoreFacade) ContextLoader.getCurrentWebApplicationContext().getBean("storeFacade");
		User user = this.getLoginUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> data = new HashMap<String, Object>();
		StoreWrapper sw = facade.get(user);
		data.put("store", sw);
		result.put("respCode", 200);
		result.put("message", "农资店信息获取");
		result.put("data", data);
		return result;
		
	
	}
}
