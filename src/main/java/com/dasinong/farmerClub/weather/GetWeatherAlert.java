package com.dasinong.farmerClub.weather;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dasinong.farmerClub.util.WISWeather;

public class GetWeatherAlert {

	private String areaId;
	private static HashMap<String, List<WeatherAlert>> _weatheralert = new HashMap<String, List<WeatherAlert>>();

	private Logger logger = LoggerFactory.getLogger(GetWeatherAlert.class);

	public GetWeatherAlert(String areaId) {
		this.areaId = areaId;
	}

	public List<WeatherAlert> getWeatherAlert() {
		Long currentTime = System.currentTimeMillis();

		// 只有在确实存在weather alert的情况下返回预警信息，其它情况一律返回null,作无预警处理
		// 如果有缓存的新鲜数据，那么直接返回缓存的新鲜数据
		if (_weatheralert.containsKey(areaId)
				&& (currentTime - _weatheralert.get(areaId).get(0).timeStamp.getTime()) < 5 * 60 * 1000)
			return _weatheralert.get(areaId);

		WISWeather wisw = new WISWeather(this.areaId, "alarm");
		String result = wisw.Commute();
		List<WeatherAlert> wa = null;
		try {
			if (!result.equals("key error") && !result.equals("")) {
				wa = WeatherAlert.parseHTTPResult(areaId, result);
				if (wa != null) // 请求正常，且有预警信息
					_weatheralert.put(areaId, wa);
			}
			if (wa == null && _weatheralert.containsKey(areaId)) { // 无预警信息，
																	// 如果遇到请求，总是会再次请求预警信息，并且总是删除过期的预警信息
				_weatheralert.remove(areaId);
			}
		} catch (Exception e) {
			logger.error("Error happened when parse HTTP get weather alert result!", e);
		}

		return wa;
	}

	public static void main(String[] args) {
		GetWeatherAlert gwa = new GetWeatherAlert("101260301");
		gwa.getWeatherAlert();
	}
}
