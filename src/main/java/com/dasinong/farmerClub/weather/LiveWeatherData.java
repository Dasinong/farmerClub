/**
 * 
 */
package com.dasinong.farmerClub.weather;

import org.codehaus.jackson.map.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.*;

import java.io.IOException;
import java.util.Date;

/**
 * @author Dell
 *
 */
public class LiveWeatherData {
	public String code;
	public Date timeStamp;
	public int l1;
	public int l2;
	public int l3;
	public int l4;
	public String l5;
	public String l6;
	public String l7;
	public int daymin = -100;
	public int daymax = -100;

	private Logger logger = LoggerFactory.getLogger(LiveWeatherData.class);

	/**
	 * 
	 */
	public LiveWeatherData(String code, int l1, int l2, int l3, int l4, String l5, String l6, String l7) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.timeStamp = new Date();
		this.timeStamp.setTime(100000); // Time set on create routine not
										// guarantee content is good.
		this.l1 = l1;
		this.l2 = l2;
		this.l3 = l3;
		this.l4 = l4;
		this.l5 = l5;
		this.l6 = l6;
		this.l7 = l7;
	}

	public void parseHTTPResult(String code, String result)
			throws JsonParseException, JsonProcessingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		this.code = code;
		this.timeStamp = new Date();

		try {
			JsonNode node = mapper.readTree(result);
			JsonNode observe = node.get("observe");
			JsonNode areaWeather = observe.get(code);
			JsonNode firstNode = areaWeather.get("l");
			this.l1 = Integer.parseInt(firstNode.get("l1").toString().replace('\"', ' ').trim());
			this.l2 = Integer.parseInt(firstNode.get("l2").toString().replace('\"', ' ').trim());
			this.l3 = Integer.parseInt(firstNode.get("l3").toString().replace('\"', ' ').trim());
			this.l4 = Integer.parseInt(firstNode.get("l4").toString().replace('\"', ' ').trim());
			this.l5 = firstNode.get("l5").toString().replace('\"', ' ').trim();
			this.l6 = firstNode.get("l6").toString().replace('\"', ' ').trim();
			this.l7 = firstNode.get("l7").toString().replace('\"', ' ').trim();
			this.timeStamp = new Date();
		} catch (Exception e) {
			logger.error("Error happened when processing json live weather data!", e);
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String code = "101010100";
		String result = "{\"observe\":{\"101010100\":{\"l\":{\"l1\":\"27\",\"l2\":\"65\",\"l3\":\"3\",\"l4\":\"5\",\"l5\":\"03\",\"l6\":\"0\",\"l6\":\"0.0\",\"l7\":\"11:40\"}}}}";
		// String result = "{\"k1\":\"v1\",\"k2\":\"v2\"}";
		LiveWeatherData test = new LiveWeatherData("0", 0, 0, 0, 0, "0", "0.0", "0:00");
		try {
			test.parseHTTPResult(code, result);
		} catch (Exception e) {
			System.out.println("Error when call parseHTTPResult!");
		}
		System.out.println("json string:" + result);
		System.out.println("test.code:" + test.code);
		System.out.println("test.timeStamp:" + test.timeStamp);
		System.out.println("test.l1:" + test.l1);
		System.out.println("test.l2:" + test.l2);
		System.out.println("test.l3:" + test.l3);
		System.out.println("test.l4:" + test.l4);
		System.out.println("test.l5:" + test.l5);
		System.out.println("test.l6:" + test.l6);
		System.out.println("test.l7:" + test.l7);

	}

}
