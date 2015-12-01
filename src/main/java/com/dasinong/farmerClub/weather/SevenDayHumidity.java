package com.dasinong.farmerClub.weather;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dasinong.farmerClub.weather.SevenDayForcast.ForcastInfo;

public class SevenDayHumidity {
	class HumidityInfo {
		public Date forecast_time; // 预报时间
		public int dayHumidity; // 天气现象编码
		public int nightHumidity; // 平均温度

		public HumidityInfo(Date forecast_time, int dayHumidity, int nightHumidity) {
			super();
			this.forecast_time = forecast_time;
			this.dayHumidity = dayHumidity;
			this.nightHumidity = nightHumidity;
		}

	}

	public int code;
	public Date startDate;
	public HumidityInfo[] humidity = new HumidityInfo[10];
	private Logger logger = LoggerFactory.getLogger(SevenDayHumidity.class);

	public SevenDayHumidity(int code, Date startDate) {
		this.code = code;
		this.startDate = startDate;
	}

	private int count = 0;

	public void add(Date forecast_time, int dayHumidity, int nightHumidity) {
		if (count < 10) {
			HumidityInfo hi = new HumidityInfo(forecast_time, dayHumidity, nightHumidity);
			humidity[count] = hi;
			count++;
		} else {
			logger.warn("Stack is full for city " + code);
		}
	}

}
