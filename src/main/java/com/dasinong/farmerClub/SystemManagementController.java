package com.dasinong.farmerClub;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasinong.farmerClub.exceptions.MissingParameterException;

@Controller
public class SystemManagementController extends BaseController {
	
	@RequestMapping(value = "/weatherIssue", produces = "application/json")
	@ResponseBody
	public Object insertSoilReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String issue = request.getParameter("issue");
		String monitorLocationId = request.getParameter("monitorLocationId");
		if (issue == null || issue.equals("") || monitorLocationId == null || monitorLocationId.equals("")) {
			throw new MissingParameterException();
		}
		result.put("message", "提交成功");
		result.put("respCode", 200);
		return result;
	}

	@RequestMapping(value = "/userFeedback", produces = "application/json")
	@ResponseBody
	public Object userFeedback(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("msgId");
		String monitorLocationId = request.getParameter("Up_YourNum");
		String tel = request.getParameter("Up_UserTel");
		String upUserMsg = request.getParameter("Up_UserMsg");
		result.put("message", "提交成功");
		result.put("respCode", 200);
		return result;
	}
}
