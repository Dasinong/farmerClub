package com.dasinong.farmerClub;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.dasinong.farmerClub.facade.IFieldFacade;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.FieldWrapper;
import com.dasinong.farmerClub.outputWrapper.SubStageWrapper;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class FieldController extends RequireUserLoginController {

	@RequestMapping(value = "/createField", produces = "application/json")
	@ResponseBody
	public Object createField(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.getLoginUser(request);
		Map<String, Object> result = new HashMap<String, Object>();

		HttpServletRequestX requestX = new HttpServletRequestX(request);

		String fieldName = requestX.getStringOptional("fieldName", "");
		boolean isActive = requestX.getBool("isActive");
		boolean seedingortransplant = requestX.getBool("seedingortransplant");
		double area = requestX.getDouble("area");
		Long locationId = requestX.getLong("locationId");
		Long varietyId = requestX.getLong("varietyId");
		Long currentStageId = requestX.getLongOptional("currentStageId", 0L);
		Long yield = requestX.getLongOptional("yield", 0L);
		Date startDate = requestX.getDate("startDate");

		IFieldFacade ff = (IFieldFacade) ContextLoader.getCurrentWebApplicationContext().getBean("fieldFacade");

		try {
			FieldWrapper fw = ff.createField(user, fieldName, startDate, isActive, seedingortransplant, area,
					locationId, varietyId, currentStageId, yield);
			result.put("respCode", 200);
			result.put("message", "添加田地成功");
			result.put("data", fw);
			return result;
		} catch (Exception e) {
			result.put("respCode", 500);
			result.put("message", e.getCause());
			return result;
		}

	}

	@RequestMapping(value = "/searchNearUser", produces = "application/json")
	@ResponseBody
	public Object searchNearUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar ccal = Calendar.getInstance();
			Calendar scal = Calendar.getInstance();
			scal.setTime(sdf.parse("20150511"));
			int difference = (int) Math.floor((ccal.getTimeInMillis() - scal.getTimeInMillis()) / 60000000L);
			result.put("respCode", 200);
			result.put("message", "附近农户数");
			result.put("data", difference);
			return result;
		} catch (Exception e) {
			result.put("respCode", 500);
			result.put("message", e.getCause());
			return result;
		}
	}

	@RequestMapping(value = "/changeStage", produces = "application/json")
	@ResponseBody
	public Object changeStage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		Long fieldId;
		Long currentStageId;

		try {
			fieldId = Long.parseLong(request.getParameter("fieldId"));
			currentStageId = Long.parseLong(request.getParameter("currentStageId"));
		} catch (Exception e) {
			throw new InvalidParameterException("fieldId", "long", "currentStageId", "long");
		}

		IFieldFacade ff = (IFieldFacade) ContextLoader.getCurrentWebApplicationContext().getBean("fieldFacade");
		FieldWrapper fw = ff.changeField(fieldId, currentStageId);
		result.put("respCode", 200);
		result.put("message", "更换阶段成功");
		result.put("data", fw);
		return result;
	}

	@RequestMapping(value = "/getStages", produces = "application/json")
	@ResponseBody
	public Object getStages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		Long varietyId = requestX.getLong("varietyId");
		IFieldFacade ff = (IFieldFacade) ContextLoader.getCurrentWebApplicationContext().getBean("fieldFacade");

		List<SubStageWrapper> ssw = ff.getStages(varietyId);
		result.put("respCode", 200);
		result.put("message", "获得阶段列表成功");
		result.put("data", ssw);
		return result;
	}

}
