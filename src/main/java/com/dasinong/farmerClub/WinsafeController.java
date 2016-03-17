package com.dasinong.farmerClub;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dasinong.farmerClub.dao.IProStockDao;
import com.dasinong.farmerClub.exceptions.UserIsNotLoggedInException;
import com.dasinong.farmerClub.model.ProStock;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.ProStockWrapper;
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
		String boxcode = requestX.getString("boxcode");	
		boolean stocking = requestX.getBool("stocking");	
		
		WinsafeUtil winsafe = new WinsafeUtil();
		String winSafeResult = winsafe.getProductInfo(boxcode);
		
		 try{
	 			ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.readTree(winSafeResult);
				JsonNode returnCode = root.get("returnCode");
				if (returnCode.getIntValue() == 0){
					HashMap<String,String> data = new HashMap<String,String>();
					JsonNode returnData = root.get("returnData").get(0);
					boxcode = returnData.get("boxcode").getTextValue();
					data.put("boxcode",boxcode);
					String proid = returnData.get("proid").getTextValue();
					data.put("proid",proid);
					String prodName = returnData.get("proname").getTextValue();
					data.put("proname",prodName);
					String prospecial = returnData.get("prospecial").getTextValue();
					data.put("prospecial",prospecial);
					result.put("respCode", 200);
					result.put("message", "获取成功");
					result.put("data", data);
				    if (stocking){
				    	 IProStockDao proStockDao = (IProStockDao) ContextLoader.getCurrentWebApplicationContext().getBean("proStockDao");
						 ProStock ps = new ProStock();
						 
						 ps.setUserid(user.getUserId());
						 ps.setProdid(Long.parseLong(proid));
						 ps.setProdname(prodName);
						 ps.setProspecial(prospecial);
						 ps.setScanat(new Date());
						 ps.setBoxcode(boxcode);
						 proStockDao.save(ps);
						 result.put("message", "入库成功");
				    }
				} 
				else{
					result.put("respCode", 205);
					result.put("message", "获取失败");
				}
			}catch (Exception e) {
				System.out.println("Error happened when parsing winsafe result");
				result.put("respCode", 205);
				result.put("message", "获取失败");
			}
		 
		
		 
	     return result;
	}
	
	@RequestMapping(value = "/checkStock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object checkStock(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String custid="";
		if (user.getWinsafeid() == null || user.getWinsafeid() == 0L){
        	custid = "60001";
        }else{
        	custid = "" + user.getWinsafeid();
        }
		String date = requestX.getString("date");
		WinsafeUtil winsafe = new WinsafeUtil();
		String winSafeResult = winsafe.getCustStockReport(custid, date);
		
        try{
 			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(winSafeResult);
			JsonNode returnCode = root.get("returnCode");
			if (returnCode.getIntValue() == 0){
				HashMap<String,Object> data = new HashMap<String,Object>();
				JsonNode returnData = root.get("returnData").get(0);
				String proid = returnData.get("proid").getTextValue();
				data.put("proid",proid);
				String prodName = returnData.get("proname").getTextValue();
				data.put("proname",prodName);
				String prospecial = returnData.get("prospecial").getTextValue();
				data.put("prospecial",prospecial);
				int count = returnData.get("count").getIntValue();
				data.put("count",count);
				date = returnData.get("date").getTextValue();
				data.put("date",date);
				custid = returnData.get("custid").getTextValue();
				data.put("custid",custid);
				result.put("respCode", 200);
				result.put("message", "获取成功");
				result.put("data", data);
			} 
			else{
				result.put("respCode", 205);
				result.put("message", "获取失败");
			}
		}catch (Exception e) {
			System.out.println("Error happened when parsing winsafe result");
			result.put("respCode", 205);
			result.put("message", "获取失败");
		}
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
				String winSafeResult = "";
		        if (user.getWinsafeid()==null || user.getWinsafeid() == 0L){
		        	winSafeResult = winsafe.stockScan("62269",fileName);
		        }else{
		        	winSafeResult = winsafe.stockScan(""+user.getWinsafeid(),fileName);
		        }
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
	
	
	
	@RequestMapping(value = "/checkBSFStock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object checkBSFStock(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser(request);
		IProStockDao proStockDao = (IProStockDao) ContextLoader.getCurrentWebApplicationContext().getBean("proStockDao");
		List<Object[]> proStocks = proStockDao.countStockByUserid(user.getUserId());
		List<ProStockWrapper> proStockWrappers = new ArrayList<ProStockWrapper>();
		for(Object[] proStock : proStocks){
			ProStockWrapper pw = new ProStockWrapper((long) proStock[0],(String) proStock[1],(String) proStock[2], (long) proStock[3]);
			proStockWrappers.add(pw);
		}
		result.put("respCode", 200);
		result.put("message", "获取成功");
		result.put("data", proStockWrappers);
		return result;
	}
	
}
