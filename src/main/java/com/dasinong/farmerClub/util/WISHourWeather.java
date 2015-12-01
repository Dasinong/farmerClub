package com.dasinong.farmerClub.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dasinong.farmerClub.weather.LiveWeatherData;

public class WISHourWeather {

	private static final String url = "http://data.fuwu.weather.com.cn/hourlyyol/HourlyForecast";
	// private static final String app_id = "05ac98ab74edb72f01eb";
	private static final String app_id = "05ac98ab74edb72f01eb8c0370eb4a28";
	private static final String short_app_id = "05ac98";
	private static final String key = "YOLOO_webapi_data";
	private String date;
	private String areaId;
	private String type;
	private Logger logger = LoggerFactory.getLogger(WISHourWeather.class);

	public WISHourWeather(String areaId, String type) {
		this.areaId = areaId;
		this.type = type;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		date = sdf.format(new Date());
	}

	public String Commute() {
		String result = "";
		BufferedReader in = null;

		try {
			URL realUrl = new URL(this.getRealURL());
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			// connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			/*
			 * // 获取所有响应头字段 Map<String, List<String>> map =
			 * connection.getHeaderFields(); // 遍历所有的响应头字段 for (String key :
			 * map.keySet()) { System.out.println(key + "--->" + map.get(key));
			 * } // 定义 BufferedReader输入流来读取URL的响应
			 */
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			result = "";
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("WISHourWeather发送GET请求出现异常", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;

	}

	public String getRealURL() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
		String result;
		String urlkey = WISHourWeather.url + "?areaid=" + this.areaId + "&type=" + this.type + "&date=" + date
				+ "&appid=" + this.app_id;
		// System.out.println(urlkey);
		byte[] skey = WISHourWeather.key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(skey, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(urlkey.getBytes());
		byte[] encodeBytes = Base64.encodeBase64(rawHmac);
		String finalkey = new String(encodeBytes, "utf-8");
		finalkey = URLEncoder.encode(finalkey, "utf-8");
		result = WISHourWeather.url + "?areaid=" + this.areaId + "&type=" + this.type + "&date=" + date + "&appid="
				+ this.short_app_id + "&key=" + finalkey;
		// System.out.println(result);
		return result;
	}

	public static void main(String[] args) {
		WISWeather wisw = new WISWeather("101010100", "alarm");
		wisw.Commute();
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

}
