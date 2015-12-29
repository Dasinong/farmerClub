package com.dasinong.farmerClub;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IStoreDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.dao.IWeatherSubscriptionDao;
import com.dasinong.farmerClub.facade.IStoreFacade;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.store.StoreStatus;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class StoreController extends RequireUserLoginController {

	@RequestMapping(value = "/stores", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object reorderWeatherSubscriptions(HttpServletRequest request, HttpServletResponse response)
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

		return facade.create(user, name, desc, locationId, streetAndNumber, latitude, longtitude, contactName, phone,
				source, type);
	}
}
