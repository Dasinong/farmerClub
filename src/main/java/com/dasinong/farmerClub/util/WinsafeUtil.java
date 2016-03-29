package com.dasinong.farmerClub.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//The Http wrapper to integrate with winsafe system
public class WinsafeUtil {
	private String ServerUrl="http://basf.winsafe.cn/bva/";
	private String username="apiuser";
	private String password="a123456";
	
	private Logger logger = LoggerFactory.getLogger(WinsafeUtil.class);

	public WinsafeUtil() {

	}

	public String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
	
	public String postFile(String urls, Map<String, String> textMap, Map<String,String> fileMap) {
		String res="";
        
       
        java.net.HttpURLConnection conn = null;  
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
        try {  
            java.net.URL url = new java.net.URL(urls);  
            conn = (java.net.HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
  
            OutputStream out = new DataOutputStream(conn.getOutputStream());  
            // text    
            if (textMap != null) {  
                StringBuffer strBuf = new StringBuffer();  
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");  
                    strBuf.append(inputValue);  
                }  
                out.write(strBuf.toString().getBytes());  
            }  
  
            // file    
            if (fileMap != null) {  
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    File file = new File(inputValue);  
                    String filename = file.getName();  
                    String contentType = "multipart/form-data; boundary="+BOUNDARY;  
  
                    StringBuffer strBuf = new StringBuffer();  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");  
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
  
                    out.write(strBuf.toString().getBytes());  
  
                    DataInputStream in = new DataInputStream(new FileInputStream(file));  
                    int bytes = 0;  
                    byte[] bufferOut = new byte[1024];  
                    while ((bytes = in.read(bufferOut)) != -1) {  
                        out.write(bufferOut, 0, bytes);  
                    }  
                    in.close();  
                }  
            }  
  
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
            out.write(endData);  
            out.flush();  
            out.close();  
  
            // 读取返回数据    
            StringBuffer strBuf = new StringBuffer();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {  
            System.out.println("发送POST请求出错。" + urls);  
            e.printStackTrace();  
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }  
        return res;  
    }    

	public String getCustInfo(String custid){
		String url = ServerUrl + "CustomerInfo.ashx";
		String param = "username="+username+"&password="+password+"&custid="+custid+"&isbva=1";
		return sendPost(url,param);
	}
	
	public String getProductInfo(String boxcode){
		String url = ServerUrl + "ProductInfo.ashx";
		String param = "username="+username+"&password="+password+"&boxcode="+boxcode;
		return sendPost(url,param);
	}
	
	public String getCustStockReport(String custid, String date){
		String url = ServerUrl + "CustStockingReport.ashx";
		String param = "username="+username+"&password="+password+"&custid="+custid+"&date="+date;
		return sendPost(url,param);
	}
	
	public String antiFake(String fwcode){
		String url = ServerUrl + "FWQuery.ashx";
		String param = "username="+username+"&password="+password+"&fwcode="+fwcode;
		return sendPost(url,param);
	}
	
	public String stockScan(String custid, String fileName){
		String url = ServerUrl + "stockingscan.ashx";
		Map<String, String> textMap = new HashMap<String, String>();  
        textMap.put("username", username);  
        textMap.put("password", password);  
        textMap.put("custid", custid);  
        Map<String, String> fileMap = new HashMap<String, String>();  
        fileMap.put("userfile", fileName);  
		return postFile(url,textMap,fileMap);
	}

	
	
	
	public static void main(String[] args) {
		WinsafeUtil winUtil = new WinsafeUtil();
		String result = winUtil.getCustInfo("62269");
		System.out.println(result);
		result = winUtil.getProductInfo("U0110125511350B05##110113");
		System.out.println(result);
		result = winUtil.getCustStockReport("60001", "201401");
		System.out.println(result);
		result = winUtil.antiFake("0730000001009591");
		System.out.println(result);
		result = winUtil.stockScan("62269","/Users/jiangsean/622692_20160315101011.txt");
		System.out.println(result);
		
	}

	


}
