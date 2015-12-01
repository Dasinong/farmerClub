package com.dasinong.farmerClub;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.sms.RefAppShortMessage;
import com.dasinong.farmerClub.sms.SMS;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class ChannelController extends RequireUserLoginController {

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/refapp", produces = "application/json")
	@ResponseBody
	public Object refapp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.getLoginUser(request);
		String cellphone = request.getParameter("cellPhones");
		if (cellphone != null && !"".equals(cellphone)) {
			String[] target = cellphone.split(",");
			SMS.send(new RefAppShortMessage(user.getInstitutionId()), target);
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", "200");
		result.put("message", "消息发送任务已部署");
		return result;
	}

}
