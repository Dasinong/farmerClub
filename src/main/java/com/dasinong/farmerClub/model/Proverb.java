package com.dasinong.farmerClub.model;

import java.io.Serializable;

public class Proverb implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long proverbId;
	private String lunarCalender;
	private String weather;
	private String month;
	private String content = "";

	public Proverb() {
	}

	public Proverb(String content) {
		this.content = content;
	}

	public Long getProverbId() {
		return proverbId;
	}

	public void setProverbId(Long proverbId) {
		this.proverbId = proverbId;
	}

	public String getLunarCalender() {
		return lunarCalender;
	}

	public void setLunarCalender(String lunarCalender) {
		this.lunarCalender = lunarCalender;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
