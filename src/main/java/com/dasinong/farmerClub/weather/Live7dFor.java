package com.dasinong.farmerClub.weather;

import java.util.Date;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dasinong.farmerClub.util.WISWeather;

public class Live7dFor {
	Date timeStamp; // System cache update time;
	Long areaid;
	String country;
	String city;
	String provience;
	int cityLvl;
	String citycode;
	String zipcode;
	double lon;
	double lat;
	int alt;
	String radar;
	public Date reportTime; // The report generate time;
	public SevenDayNormal[] sevenDay = new SevenDayNormal[7];
	private Logger logger = LoggerFactory.getLogger(Live7dFor.class);

	public Live7dFor(String result, Long areaid) {
		ObjectMapper mapper = new ObjectMapper();
		this.areaid = areaid;
		this.timeStamp = new Date();
		try {
			JsonNode root = mapper.readTree(result);
			JsonNode f7d = root.get("forecast7d");
			JsonNode loc = f7d.get("" + areaid);
			System.out.println("areaid:" + areaid);
			JsonNode c = loc.get("c");
			this.country = c.get("c2").getTextValue();
			this.city = c.get("c4").getTextValue();
			this.provience = c.get("c6").getTextValue();
			this.cityLvl = Integer.parseInt(c.get("c10").getTextValue());
			this.citycode = c.get("c11").getTextValue();
			this.zipcode = c.get("c12").getTextValue();
			this.lon = c.get("c13").getDoubleValue();
			this.lat = c.get("c14").getDoubleValue();
			this.alt = Integer.parseInt(c.get("c15").getTextValue());
			this.radar = c.get("c16").getTextValue();

			JsonNode f = loc.get("f");
			JsonNode f1 = f.get("f1");
			Iterator<JsonNode> sevendaynormal = f1.iterator();
			int count = 0;
			while (sevendaynormal.hasNext() && count < 7) {
				SevenDayNormal sdn = new SevenDayNormal(sevendaynormal.next());
				this.sevenDay[count] = sdn;
				count++;
			}
			this.timeStamp = new Date();
		} catch (Exception e) {
			logger.error("Error happened when processing json live weather data!", e);
		}
	}

	public static void main(String[] args) {
		WISWeather wisw = new WISWeather("101020100", "forecast7d");
		String result = wisw.Commute();
		Live7dFor l7df = new Live7dFor(result, 101020100L);
		int i = 1;
	};

}
