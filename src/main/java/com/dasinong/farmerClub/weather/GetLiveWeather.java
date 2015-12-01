package com.dasinong.farmerClub.weather;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dasinong.farmerClub.util.WISWeather;

public class GetLiveWeather {

	private String areaId;
	private Logger logger = LoggerFactory.getLogger(GetLiveWeather.class);

	public GetLiveWeather() {
	}

	public GetLiveWeather(String areaId) {
		this.areaId = areaId;
	}

	public LiveWeatherData getLiveWeather() {
		long currentTime = System.currentTimeMillis();

		// 如果上次关于这个地方的天气请求距离这次不到20分钟，那么直接返回缓存的天气数据
		if (GetLiveWeather._liveweatherdata.containsKey(this.areaId)
				&& (currentTime - GetLiveWeather._liveweatherdata.get(this.areaId).timeStamp.getTime()) < 20 * 60
						* 1000)
			return GetLiveWeather._liveweatherdata.get(this.areaId);

		WISWeather wisw = new WISWeather(this.areaId, "observe");
		String result = wisw.Commute();

		if (result.equals("key error")) {
			System.out.println("areaID : " + this.areaId);
			if (_liveweatherdata.containsKey(this.areaId)) {
				return _liveweatherdata.get(this.areaId);
			} else {
				LiveWeatherData initialWeatherData = new LiveWeatherData(this.areaId, 0, 0, 0, 0, "0", "0", "00:00");
				_liveweatherdata.put(this.areaId, initialWeatherData);
				return initialWeatherData;
			}
		} else {
			LiveWeatherData initialWeatherData = new LiveWeatherData(this.areaId, 0, 0, 0, 0, "0", "0", "00:00");
			try {
				initialWeatherData.parseHTTPResult(this.areaId, result);
				_liveweatherdata.put(this.areaId, initialWeatherData);
			} catch (Exception e) {
				logger.error("Error happend when processing live weather data!", e);
			}
			return initialWeatherData;
		}
	}

	private static HashMap<String, LiveWeatherData> _liveweatherdata = new HashMap<String, LiveWeatherData>();

	public static void main(String args[]) {
		GetLiveWeather gh = new GetLiveWeather("101010100");
		gh.getLiveWeather();
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

}
