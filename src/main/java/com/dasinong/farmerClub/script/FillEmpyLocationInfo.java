package com.dasinong.farmerClub.script;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.model.Location;

public class FillEmpyLocationInfo {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		
		ILocationDao locDao = (ILocationDao) applicationContext.getBean("locationDao");
		List<Location> locs = locDao.findEmptyLocations();
		
		for (Location loc : locs) {
			if (loc.getLatitude() != 0 && loc.getLongtitude() != 0) {
				continue;
			}
			
			JSONObject json;
			try {
				json = FillEmpyLocationInfo.getLoc(loc);
			} catch (Exception ex) {
				Thread.sleep(5000);
				json = FillEmpyLocationInfo.getLoc(loc);
			}
			
			Double lat = json.getDouble("lat");
			Double lon = json.getDouble("lng");
			System.out.println(lat + " " + lon);
			
			loc.setLatitude(lat);
			loc.setLongtitude(lon);
			locDao.update(loc);
		}
	}
	
	private static JSONObject getLoc(Location location) throws Exception {
		String url = "http://api.map.baidu.com/geocoder/v2/?ak=R2hpT6e50GY1v75OmBGequ3k&output=json&address=";
		url += location.toString();
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		JSONObject root = new JSONObject(response.toString());
		JSONObject result = root.getJSONObject("result");
		JSONObject loc = result.getJSONObject("location");
		return loc;
	}
}
