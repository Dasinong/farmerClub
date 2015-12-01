package com.dasinong.farmerClub.weather;

import java.util.Date;

import org.codehaus.jackson.JsonNode;

public class SevenDayNormal {
	String dayWeather;
	String nightWeather;
	public int dayTemp;
	public int nightTemp;
	short dayWD;
	short nightWD;
	short dayWP;
	short nightWP;
	public Date sunrise;
	public Date sunset;

	public SevenDayNormal(String dayWeather, String nightWeather, int dayTemp, int nightTemp, short dayWD,
			short nightWD, short dayWP, short nightWP, Date sunrise, Date sunset) {
		super();
		this.dayWeather = dayWeather;
		this.nightWeather = nightWeather;
		this.dayTemp = dayTemp;
		this.nightTemp = nightTemp;
		this.dayWD = dayWD;
		this.nightWD = nightWD;
		this.dayWP = dayWP;
		this.nightWP = nightWP;
		this.sunrise = sunrise;
		this.sunset = sunset;
	}

	public SevenDayNormal(JsonNode forcast) {
		this.dayWeather = forcast.get("fa").getTextValue();
		this.nightWeather = forcast.get("fb").getTextValue();
		try {
			this.dayTemp = Integer.parseInt(forcast.get("fc").getTextValue());
		} catch (Exception e) {
			this.dayTemp = -100;
		}

		try {
			this.nightTemp = Integer.parseInt(forcast.get("fd").getTextValue());
		} catch (Exception e) {
			this.nightTemp = -100;
		}
		try {
			this.dayWD = Short.parseShort(forcast.get("fe").getTextValue());
		} catch (Exception e) {
			this.dayWD = -1;
		}
		try {
			this.nightWD = Short.parseShort(forcast.get("ff").getTextValue());
		} catch (Exception e) {
			this.nightWD = -1;
		}
		try {
			this.dayWP = Short.parseShort(forcast.get("fg").getTextValue());
		} catch (Exception e) {
			this.dayWP = -1;
		}
		try {
			this.nightWP = Short.parseShort(forcast.get("fh").getTextValue());
		} catch (Exception e) {
			this.nightWP = -1;
		}
		String[] daynight = forcast.get("fi").getTextValue().split("\\|");
		if (daynight.length == 2) {
			this.sunrise = formatDate(daynight[0]);
			this.sunset = formatDate(daynight[1]);
		}
	}

	private Date formatDate(String input) {
		String[] time = input.split(":");
		try {
			if (time.length == 2) {
				int hour = Integer.parseInt(time[0]);
				int min = Integer.parseInt(time[1]);
				Date date = new Date();
				date.setHours(hour);
				date.setMinutes(min);
				return date;
			} else
				return null;
		} catch (Exception e) {
			return null;
		}
	}
}
