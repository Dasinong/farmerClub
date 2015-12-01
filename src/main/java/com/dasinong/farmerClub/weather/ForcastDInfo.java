package com.dasinong.farmerClub.weather;

import java.util.Date;

public class ForcastDInfo {
	public Date time;
	public int temperature; // ℃
	public int relativeHumidity;
	public int windDirection_10m; // Degrees
	public double windSpeed_10m; // mps
	public double accumRainTotal; // mm
	public double accumSnowTotal; // mm
	public double accumIceTotal; // mm
	public int pOP; // Probability of precipitation
	public String icon;

	public ForcastDInfo(Date time, int temperature, int relativeHumidity, int windDirection_10m, double windSpeed_10m,
			double accumRainTotal, double accumSnowTotal, double accumIceTotal, int pOP, String icon) {
		super();
		this.time = time;
		this.temperature = temperature;
		this.relativeHumidity = relativeHumidity;
		this.windDirection_10m = windDirection_10m;
		this.windSpeed_10m = windSpeed_10m;
		this.accumRainTotal = accumRainTotal;
		this.accumSnowTotal = accumSnowTotal;
		this.accumIceTotal = accumIceTotal;
		this.pOP = pOP;
		this.icon = icon;
	}
}
