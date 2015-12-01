package com.dasinong.farmerClub.ruleEngine.rules;

import com.dasinong.farmerClub.weather.All24h;
import com.dasinong.farmerClub.weather.GetLive24h;
import com.dasinong.farmerClub.weather.TwentyFourHourForcast;

//This class is to define rules with direct data in hand.
public class Rule {
	public static int workable(long monitorLocationId) {
		int workable = 1;
		TwentyFourHourForcast tfhf;
		GetLive24h g24 = new GetLive24h("" + monitorLocationId);
		tfhf = g24.getLiveWeather();
		if (tfhf == null)
			tfhf = All24h.get24h().get24h(monitorLocationId);
		if (tfhf == null) {
			System.out.println("获取" + monitorLocationId + "地区24小时预测失败");
			workable = -1;
		}
		for (int i = 0; i < tfhf.getSize(); i++) {
			if (tfhf.info[i] != null && tfhf.info[i].accumRainTotal > 5) {
				workable = 0;
			}
		}
		return workable;
	}

	public static int sprayable(long monitorLocationId) {
		int sprayable = 1;
		TwentyFourHourForcast tfhf;
		GetLive24h g24 = new GetLive24h("" + monitorLocationId);
		tfhf = g24.getLiveWeather();
		if (tfhf == null)
			tfhf = All24h.get24h().get24h(monitorLocationId);
		if (tfhf == null) {
			System.out.println("获取" + monitorLocationId + "地区24小时预测失败");
			sprayable = -1;
		}
		int max = 0;
		int min = 35;
		for (int i = 0; i < tfhf.getSize() && i < 8; i++) {
			if (tfhf.info[i].temperature > max) {
				max = tfhf.info[i].temperature;
			}
			if (tfhf.info[i].temperature < min) {
				min = tfhf.info[i].temperature;
			}
		}
		if (max >= 35 || min <= 12)
			sprayable = 0;
		/*
		 * int i =0; int count =0; Date data = new Date();
		 * 
		 * while (i<tfhf.info.length && count<6){ if
		 * (tfhf.info[i].time.getTime()> data.getTime()){ count++; if
		 * (tfhf.info[i].windSpeed_10m>3) sprayable =false; } i++; } if
		 * (count<6) sprayable = false;
		 */
		for (int i = 0; i < tfhf.getSize() && i < 8; i++) {
			if (tfhf.info[i].windSpeed_10m > 4)
				sprayable = 0;
			if (tfhf.info[i].icon.equals("clear") || tfhf.info[i].icon.equals("clearnight")
					|| tfhf.info[i].icon.equals("cloudy") || tfhf.info[i].icon.equals("cloudynight")
					|| tfhf.info[i].icon.equals("mostlyclear") || tfhf.info[i].icon.equals("mostlyclearnight")
					|| tfhf.info[i].icon.equals("mostlycloudy") || tfhf.info[i].icon.equals("mostlycloudynight")
					|| tfhf.info[i].icon.equals("partlycloudy") || tfhf.info[i].icon.equals("partlycloudynight")) {
			} else {
				sprayable = 0;
			}
		}
		return sprayable;
	}

}
