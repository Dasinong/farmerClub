package com.dasinong.farmerClub.weather;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.WISWeather;

public class GetLive7d {

	private static GetLive7d live7d;
	private Logger logger = LoggerFactory.getLogger(GetLive7d.class);

	public static GetLive7d getAllLive7d() {
		if (live7d == null) {
			live7d = new GetLive7d();
			return live7d;
		} else {
			return live7d;
		}
	}

	private GetLive7d() {
		/*
		 * Set<Integer> locations = null; try { locations =
		 * AllLocation.getLocation()._allLocation.keySet(); } catch (IOException
		 * e) { e.printStackTrace(); } for(Integer code : locations){ Live7dFor
		 * result = getLive7d(code); _Live7dFor.put(code,result); }
		 */
	}

	public Live7dFor getLive7d(Long areaId) {
		long currentTime = System.currentTimeMillis();
		// 如果上次关于这个地方的天气请求距离这次不到live7dBufferTime分钟，那么直接返回缓存的天气数据
		if (this._Live7dFor.containsKey(areaId)
				&& (currentTime - this._Live7dFor.get(areaId).timeStamp.getTime()) < Env.getEnv().live7dBufferTime)
			return this._Live7dFor.get(areaId);

		WISWeather wisw = new WISWeather("" + areaId, "forecast7d");
		String result = wisw.Commute();

		Live7dFor live7dFor = null;
		try {
			live7dFor = new Live7dFor(result, areaId);
			_Live7dFor.put(areaId, live7dFor);
		} catch (Exception e) {
			logger.error("Error happend when processing 7d live forcast!", e);
			this._Live7dFor.get(areaId);
		}
		return live7dFor;
	}

	private HashMap<Long, Live7dFor> _Live7dFor = new HashMap<Long, Live7dFor>();

	public static void main(String args[]) {
		GetLive7d gh = new GetLive7d();
		gh.getLive7d(101010100L);
	}
}
