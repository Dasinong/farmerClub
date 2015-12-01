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
import com.dasinong.farmerClub.facade.ITaskFacade;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class TaskController extends RequireUserLoginController {

	@RequestMapping(value = "/getAllTask", produces = "application/json")
	@ResponseBody
	public Object getAllTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		Long fId = requestX.getLong("fieldId");
		ITaskFacade tf = (ITaskFacade) ContextLoader.getCurrentWebApplicationContext().getBean("taskFacade");
		return tf.getAllTask(fId);
	}

	@RequestMapping(value = "/getCurrentTask", produces = "application/json")
	@ResponseBody
	public Object getCurrentTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		Long fId = requestX.getLong("fieldId");
		Long currentStageId = requestX.getLong("currentStageId");
		ITaskFacade tf = (ITaskFacade) ContextLoader.getCurrentWebApplicationContext().getBean("taskFacade");
		return tf.getCurrentTask(fId, currentStageId);
	}

	@RequestMapping(value = "/updateTask", produces = "application/json")
	@ResponseBody
	public Object updateTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		Long fieldId = requestX.getLong("fieldId");

		if (requestX.hasParameter("taskId") && requestX.hasParameter("taskStatus")) {
			Long id = requestX.getLong("taskId");
			boolean status = requestX.getBool("taskStatus");
			ITaskFacade tf = (ITaskFacade) ContextLoader.getCurrentWebApplicationContext().getBean("taskFacade");
			return tf.updateTask(fieldId, id, status);
		} else if (requestX.hasParameter("taskIds") && requestX.hasParameter("taskStatuss")) {
			Long[] taskIds = requestX.getArrayOfLong("taskIds");
			Boolean[] taskStatuss = requestX.getArrayOfBoolean("taskStatuss");
			if (taskIds.length != taskStatuss.length) {
				throw new InvalidParameterException("taskIds", "List<int>", "taskStatuss", "List<boolean>");
			}

			HashMap<Long, Boolean> tasks = new HashMap<Long, Boolean>();
			for (int i = 0; i < taskIds.length; i++) {
				tasks.put(taskIds[i], taskStatuss[i]);
			}
			ITaskFacade tf = (ITaskFacade) ContextLoader.getCurrentWebApplicationContext().getBean("taskFacade");
			return tf.updateTasks(fieldId, tasks);
		} else {
			throw new MissingParameterException();
		}
	}
}
