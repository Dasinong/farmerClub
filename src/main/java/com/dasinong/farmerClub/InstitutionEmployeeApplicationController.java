package com.dasinong.farmerClub;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.facade.IInstitutionEmployeeApplicationFacade;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class InstitutionEmployeeApplicationController extends RequireUserLoginController {

	@RequestMapping(value = "/institutionEmployeeApplications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object createInstitutionEmployeeApplication(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		IInstitutionEmployeeApplicationFacade facade = (IInstitutionEmployeeApplicationFacade) ContextLoader
				.getCurrentWebApplicationContext().getBean("institutionEmployeeApplicationFacade");

		User user = this.getLoginUser(request);
		String contactName = requestX.getString("contactName");
		String cellphone = requestX.getString("cellphone");
		String code = requestX.getString("code");
		String title = requestX.getString("title");
		String region = requestX.getString("region");

		return facade.create(user, contactName, cellphone, code, title, region);
	}
}
