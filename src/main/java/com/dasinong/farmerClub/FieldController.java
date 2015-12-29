package com.dasinong.farmerClub;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.facade.IFieldFacade;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.FieldWrapper;
import com.dasinong.farmerClub.outputWrapper.SubStageWrapper;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class FieldController extends RequireUserLoginController {
	
	@RequestMapping(value = "/getField", produces = "application/json")
	@ResponseBody
	public Object getField(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		IFieldFacade facade = (IFieldFacade)  ContextLoader.getCurrentWebApplicationContext().getBean("fieldFacade");
		
		long fieldId = requestX.getLong("id");
		FieldWrapper field = facade.findById(fieldId);
		
		data.put("field", field);
		result.put("respCode", 200);
		result.put("message", "取得数据成功");
		result.put("data", data);
		return result;
	}
	
	@RequestMapping(value = "/createField", produces = "application/json")
	@ResponseBody
	public Object createField(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.getLoginUser(request);
		Map<String, Object> result = new HashMap<String, Object>();

		HttpServletRequestX requestX = new HttpServletRequestX(request);

		String fieldName = requestX.getStringOptional("fieldName", "");
		double area = requestX.getDouble("area");
		Long locationId = requestX.getLong("locationId");
		Long cropId = requestX.getLong("cropId");
		Long currentStageId = requestX.getLongOptional("currentStageId", 0L);

		IFieldFacade ff = (IFieldFacade) ContextLoader.getCurrentWebApplicationContext().getBean("fieldFacade");

		try {
			FieldWrapper fw = ff.createField(user, fieldName, area,
					locationId, cropId, currentStageId);
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

	@RequestMapping(value = "/changeFieldStage", produces = "application/json")
	@ResponseBody
	public Object changeFieldStage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		
		Long fieldId = requestX.getLong("fieldId");
		Long subStageId = requestX.getLong("subStageId");

		IFieldFacade ff = (IFieldFacade) ContextLoader.getCurrentWebApplicationContext().getBean("fieldFacade");
		FieldWrapper fw = ff.changeField(fieldId, subStageId);
		
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

		Long cropId = requestX.getLong("cropId");
		IFieldFacade ff = (IFieldFacade) ContextLoader.getCurrentWebApplicationContext().getBean("fieldFacade");

		List<SubStageWrapper> ssw = ff.getStages(cropId);
		result.put("respCode", 200);
		result.put("message", "获得阶段列表成功");
		result.put("data", ssw);
		return result;
	}

}
