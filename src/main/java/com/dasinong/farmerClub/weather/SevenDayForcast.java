package com.dasinong.farmerClub.weather;

import java.util.Date;

public class SevenDayForcast {
	public class ForcastInfo {
		public Date forecast_time; // 预报时间
		public short weathern; // 天气现象编码
		public String weather;
		public double temp; // 平均温度
		public double max_temp; // 最高温
		public double min_temp; // 最低温
		public short ff_level; // 风向编码
		public short dd_level; // 风力编码
		public double rain; // 降水量

		public ForcastInfo(Date forecast_time, short weather, double temp, double max_temp, double min_temp,
				short ff_level, short dd_level, double rain) {
			super();
			this.forecast_time = forecast_time;
			this.weathern = weather;
			if (weathern < 10) {
				this.weather = "0" + weather;
			} else {
				this.weather = "" + weather;
			}
			this.temp = temp;
			this.max_temp = max_temp;
			this.min_temp = min_temp;
			this.ff_level = ff_level;
			this.dd_level = dd_level;
			this.rain = rain;
		}

		// 并不精确
		public void aggre(ForcastInfo newInfo) {
			this.max_temp = Math.max(max_temp, newInfo.max_temp);
			this.min_temp = Math.min(min_temp, newInfo.min_temp);
			this.temp = (this.max_temp + this.min_temp) / 2;
			this.ff_level = (short) Math.max(ff_level, newInfo.ff_level);
			this.dd_level = (short) Math.max(dd_level, newInfo.dd_level);
			this.rain = rain + newInfo.rain; // ?降水量?
			this.weathern = (short) Math.max(this.weathern, newInfo.weathern);
			if (weathern < 10) {
				this.weather = "0" + weathern;
			} else {
				this.weather = "" + weathern;
			}
		}
	}

	private int rc = 0;
	private int ac = 0;
	public long code;
	public Date startDate;
	public ForcastInfo[] rawData = new ForcastInfo[38];
	public ForcastInfo[] aggregateData = new ForcastInfo[8];

	public SevenDayForcast(long code, Date startDate) {
		this.code = code;
		this.startDate = startDate;
	}

	public void addRawData(Date forecast_time, short weather, double temp, double max_temp, double min_temp,
			short ff_level, short dd_level, double rain) {
		ForcastInfo newData = new ForcastInfo(forecast_time, weather, temp, max_temp, min_temp, ff_level, dd_level,
				rain);
		if (rc == 0) {
			rawData[rc] = newData;
			aggregateData[ac] = new ForcastInfo(forecast_time, weather, temp, max_temp, min_temp, ff_level, dd_level,
					rain);
			rc++;
		} else {
			rawData[rc] = newData;
			if (newData.forecast_time.getDay() == rawData[rc - 1].forecast_time.getDay()) {
				aggregateData[ac].aggre(newData);
			} else {
				ac++;
				aggregateData[ac] = newData;
			}
			rc++;
		}
	}
}
