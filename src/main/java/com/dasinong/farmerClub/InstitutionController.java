package com.dasinong.farmerClub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.crop.CropsForInstitution;
import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.outputWrapper.CropWrapper;

@Controller
public class InstitutionController extends RequireUserLoginController {

	@RequestMapping(value = "/institutions/{instId}/crops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCrops(HttpServletRequest request, HttpServletResponse response, @PathVariable Long instId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> data = new HashMap<String, Object>();
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		
		Long[] cropIds = CropsForInstitution.getIds(instId);
		List<Crop> crops = cropDao.findByIds(cropIds);
		List<CropWrapper> cropWrappers = new ArrayList<CropWrapper>();
		for (Crop crop : crops) {
			cropWrappers.add(new CropWrapper(crop));
		}
		
		data.put("crops", cropWrappers);
		result.put("respCode", 200);
		result.put("respMsg", "获取成功");
		result.put("data", data);
		return result;
	}
}
