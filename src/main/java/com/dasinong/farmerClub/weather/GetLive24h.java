package com.dasinong.farmerClub.weather;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dasinong.farmerClub.util.WISHourWeather;

public class GetLive24h {

	private String areaId;
	private Logger logger = LoggerFactory.getLogger(GetLive24h.class);

	public GetLive24h() {
	}

	public GetLive24h(String areaId) {
		this.areaId = areaId;
	}

	public TwentyFourHourForcast getLiveWeather() {
		long currentTime = System.currentTimeMillis();

		// 如果上次关于这个地方的天气请求距离这次不到20分钟，那么直接返回缓存的天气数据
		if (GetLive24h._live24hdata.containsKey(this.areaId)
				&& (currentTime - GetLive24h._live24hdata.get(this.areaId).timeStamp.getTime()) < 20 * 60 * 1000)
			return GetLive24h._live24hdata.get(this.areaId);

		WISHourWeather wisw = new WISHourWeather(this.areaId, "hforecast");
		String result = wisw.Commute();

		if (result.equals("key error")) {
			System.out.println("areaID : " + this.areaId);
			if (_live24hdata.containsKey(this.areaId)) {
				return _live24hdata.get(this.areaId);
			} else {
				TwentyFourHourForcast tfh = All24h.get24h().get24h(Long.parseLong(this.areaId));
				return tfh;
			}
		} else {
			TwentyFourHourForcast tfh = null;
			try {
				ByteArrayInputStream content = new ByteArrayInputStream(result.getBytes("UTF-8"));
				tfh = new TwentyFourHourForcast(content, Integer.parseInt(this.areaId));
				_live24hdata.put(this.areaId, tfh);
			} catch (Exception e) {
				logger.error("Error happend when processing 24h weather data!", e);
				e.printStackTrace();
			}
			return tfh;
		}

	}

	private static HashMap<String, TwentyFourHourForcast> _live24hdata = new HashMap<String, TwentyFourHourForcast>();

	public static void main(String args[]) {
		GetLive24h gh = new GetLive24h("101010100");
		gh.getLiveWeather();
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

}
