package com.dasinong.farmerClub;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
						 ProStock ps = proStockDao.getByBoxcode(boxcode);
				    	 if (ps==null){
					    	 ps = new ProStock();
							 
							 ps.setUserid(user.getUserId());
							 ps.setProdid(Long.parseLong(proid));
							 ps.setProdname(prodName);
							 ps.setProspecial(prospecial);
							 ps.setScanat(new Date());
							 ps.setBoxcode(boxcode);
							 proStockDao.save(ps);
							 result.put("message", "入库成功");
							 
							 try{
								Long winsafeId = user.getWinsafeid();
								if (winsafeId==0L){
									winsafeId=62269L;
								}
								DateFormat df =new SimpleDateFormat("yyyyMMddHHmmss");
								String fileName= ""+winsafeId+"_"+df.format(new Date())+".txt";
								//File f = new File();
								StringBuilder content = new StringBuilder();
								content.append("*****");
								content.append(df.format(new Date()));
								content.append(boxcode);
								content.append("7");
								File f = new File(Env.getEnv().StockDir,fileName);
								if (!f.exists()){f.createNewFile();}
								FileWriter fileWritter = new FileWriter(f.getPath(),true);
					            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					            bufferWritter.write(content.toString());
					            bufferWritter.close();
								
								String saveFileResult = "";
								//work around now. Should guarantee user is registered
								saveFileResult = winsafe.stockScan(winsafeId.toString(),f.getPath());
								System.out.println("boxcode:"+ boxcode + " stocked. "+saveFileResult);
							 }catch(Exception e){
								 System.out.println("Issue in upload stock file content");
							 }
							 
							 
				    	 }else{
				    		 result.put("message", "已入库");
				    	 }
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
		result.put("respCode", 200);
		result.put("message", "上传成功");
		User user = this.getLoginUser(request);
		if (user == null) {
			throw new UserIsNotLoggedInException();
		}
		MultipartFile file = request.getFile("file");
        try{
			if (!file.isEmpty()) {				
				//this.servletContext.getRealPath("/");
				String filePath = Env.getEnv().StockDir;
				String bvaid="";
				if (user.getWinsafeid()==null || user.getWinsafeid() == 0L){
					bvaid = "62269";
		        }else{
		        	bvaid = ""+user.getWinsafeid();
		        }
				String fileName = filePath + "/" + bvaid +"_"+ file.getOriginalFilename();
				File dest = new File(fileName);
				file.transferTo(dest);
								
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
				String stockingInfo = null;
				IProStockDao proStockDao = (IProStockDao) ContextLoader.getCurrentWebApplicationContext().getBean("proStockDao");
				try{
					while((stockingInfo = br.readLine())!=null)
					{
						String times = stockingInfo.substring(5, 19);
						SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();  
						df.applyPattern("yyyyMMddHHmmss");
						Date time = df.parse(times);
						System.out.println(time);
						String boxcode = stockingInfo.substring(19,45);
						System.out.println(boxcode);
						
					    ProStock ps = proStockDao.getByBoxcode(boxcode);
					    if (ps!=null){
					    	System.out.println(ps.getBoxcode()+"已入库!");
					    }
					    else{
					    	WinsafeUtil winsafe = new WinsafeUtil();
							String winSafeResult = winsafe.getProductInfo(boxcode);
						    
						 	ObjectMapper mapper = new ObjectMapper();
							JsonNode root = mapper.readTree(winSafeResult);
							JsonNode returnCode = root.get("returnCode");
							if (returnCode.getIntValue() == 0){
								HashMap<String,String> data = new HashMap<String,String>();
								JsonNode returnData = root.get("returnData").get(0);
								boxcode = returnData.get("boxcode").getTextValue();
								String proid = returnData.get("proid").getTextValue();
								String prodName = returnData.get("proname").getTextValue();
								String prospecial = returnData.get("prospecial").getTextValue();
									
								ps = new ProStock();
								ps.setUserid(user.getUserId());
								ps.setProdid(Long.parseLong(proid));
								ps.setProdname(prodName);
								ps.setProspecial(prospecial);
								ps.setScanat(time);
								ps.setBoxcode(boxcode);
								proStockDao.save(ps);
							}
							else{
								System.out.println(ps.getBoxcode()+"入库失败!");
								result.put("message", "部分产品入库失败，详见日志");
							}
							
					    }
					}
				}catch (Exception e) {
					System.out.println("文件格式错误");
					result.put("respCode", 307);
					result.put("message", "文件格式错误");
				}finally{
					br.close();
				}

				WinsafeUtil winsafe = new WinsafeUtil();
				String winSafeResult = "";
				//work around now. Should guarantee user is registered
				winSafeResult = winsafe.stockScan(bvaid,fileName);
			
				result.put("data", dest );
				result.put("winsafe", winSafeResult );
				return result;
			}else{
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
