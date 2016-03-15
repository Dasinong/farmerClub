package com.dasinong.farmerClub;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.dao.IWeatherSubscriptionDao;
import com.dasinong.farmerClub.exceptions.UserIsNotLoggedInException;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.WeatherSubscription;
import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.HttpServletRequestX;
import com.dasinong.farmerClub.util.WinsafeUtil;

@Controller
public class WinsafeController extends RequireUserLoginController {
	
	@RequestMapping(value = "/getWinsafeProductInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getWinsafeProductInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);

		HttpServletRequestX requestX = new HttpServletRequestX(request);
		long boxcode = requestX.getLong("boxcode");
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", "");
		return result;
	}
	
	@RequestMapping(value = "/checkStock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object checkStock(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		Date date = requestX.getDate("date");

		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", "");
		return result;
	}
	
	@RequestMapping(value = "/fwQuery", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		Long fwcode = requestX.getLong("fwcode");

		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", "");
		return result;
	}
	
	@RequestMapping(value = "/stockScan", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object stockScan(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception  {
		HashMap<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		if (user == null) {
			throw new UserIsNotLoggedInException();
		}
		MultipartFile file = request.getFile("file");
        try{
			if (!file.isEmpty()) {
				
				//this.servletContext.getRealPath("/");
				String filePath = Env.getEnv().StockDir;
				String fileName = filePath + "/" + file.getOriginalFilename();
				File dest = new File(fileName);
				file.transferTo(dest);
				WinsafeUtil winsafe = new WinsafeUtil();
				String winSafeResult = winsafe.stockScan("62269",fileName);
				result.put("data", dest );
				result.put("winsafe", winSafeResult );
				result.put("respCode", 200);
				result.put("message", "上传成功");
				return result;
			}	else{
				result.put("respCode", 405);
				result.put("message", "上传文件为空");
				return result;
			}
        }catch(Exception e){
			result.put("respCode", 301);
			result.put("message", "上传出现错误");
			return result;
        }
	}
	
	
	public Object stockScan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		
		
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", "");
		return result;
	}
	
}
