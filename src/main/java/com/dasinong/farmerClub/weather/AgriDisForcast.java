package com.dasinong.farmerClub.weather;

public class AgriDisForcast {
	String date = "";
	String avgTemp = ""; // 平均气温
	String maxTemp = "";
	String minTemp = "";
	String windSpeed = "";
	String relativeHumidity = "";
	String disasterInfo = "";

	public AgriDisForcast() {
	}

	public AgriDisForcast(String date, String avgTemp, String maxTemp, String minTemp, String windSpeed,
			String relativeHumidity, String disasterInfo) {
		super();
		this.date = date;
		this.avgTemp = avgTemp;
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
		this.windSpeed = windSpeed;
		this.relativeHumidity = relativeHumidity;
		this.disasterInfo = disasterInfo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAvgTemp() {
		return avgTemp;
	}

	public void setAvgTemp(String avgTemp) {
		this.avgTemp = avgTemp;
	}

	public String getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}

	public String getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(String minTemp) {
		this.minTemp = minTemp;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getRelativeHumidity() {
		return relativeHumidity;
	}

	public void setRelativeHumidity(String relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}

	public String getDisasterInfo() {
		return disasterInfo;
	}

	public void setDisasterInfo(String disasterInfo) {
		this.disasterInfo = disasterInfo;
	}
}
