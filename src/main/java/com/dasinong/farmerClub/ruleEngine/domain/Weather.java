/**
 * 
 */
package com.dasinong.farmerClub.ruleEngine.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author caichao
 *
 */
public class Weather {
	private int curTemperature;
	private int maxTemperature;
	private int minTemperature;
	private int windSpeed;
	private Date date;
	private List<WeatherPhenomena> phenomena = new ArrayList<>();

	public int getCurTemperature() {
		return curTemperature;
	}

	public void setCurTemperature(int curTemperature) {
		this.curTemperature = curTemperature;
	}

	public int getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(int maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public int getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(int minTemperature) {
		this.minTemperature = minTemperature;
	}

	public Weather(int curTemperature, Date date) {
		this.curTemperature = curTemperature;
		this.date = date;
	}

	public void addPhenomenon(WeatherPhenomena phenomena) {
		this.phenomena.add(phenomena);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<WeatherPhenomena> getPhenomena() {
		return phenomena;
	}

	public void setPhenomena(List<WeatherPhenomena> phenomena) {
		this.phenomena = phenomena;
	}

	public int getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(int windSpeed) {
		this.windSpeed = windSpeed;
	}

	public boolean isGoingToRainInHours(int hours) {
		return true;

	}

}
