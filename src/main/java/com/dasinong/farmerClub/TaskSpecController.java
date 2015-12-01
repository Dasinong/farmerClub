package com.dasinong.farmerClub;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.exceptions.InvalidParameterException;
import com.dasinong.farmerClub.facade.ITaskSpecFacade;
import com.dasinong.farmerClub.outputWrapper.StepWrapper;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class TaskSpecController extends RequireUserLoginController {

	ITaskSpecFacade tsf;

	@RequestMapping(value = "/getTaskSpec", produces = "application/json")
	@ResponseBody
	public Object getTaskSpec(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		tsf = (ITaskSpecFacade) ContextLoader.getCurrentWebApplicationContext().getBean("taskSpecFacade");
		Long taskSpecId = requestX.getLong("taskSpecId");
		return tsf.getTaskSpec(taskSpecId);
	}

	@RequestMapping(value = "/getSteps", produces = "application/json")
	@ResponseBody
	public Object getSteps(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		tsf = (ITaskSpecFacade) ContextLoader.getCurrentWebApplicationContext().getBean("taskSpecFacade");
		Long taskSpecId = requestX.getLong("taskSpecId");
		Long fieldId = requestX.getLongOptional("fieldId", 0L);
		List<StepWrapper> sw = tsf.getSteps(taskSpecId, fieldId);

		result.put("respCode", 200);
		result.put("message", "任务列表获取成功");
		result.put("data", sw);
		return result;
	}
}
