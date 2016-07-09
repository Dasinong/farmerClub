package com.dasinong.farmerClub.facade;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IMonitorLocationDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.datapool.AllMonitorLocation;
import com.dasinong.farmerClub.model.MonitorLocation;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.ruleEngine.rules.Rule;
import com.dasinong.farmerClub.util.LunarHelper;
import com.dasinong.farmerClub.weather.All24h;
import com.dasinong.farmerClub.weather.All7d;
import com.dasinong.farmerClub.weather.ForcastDInfo;
import com.dasinong.farmerClub.weather.GetLive24h;
import com.dasinong.farmerClub.weather.GetLive7d;
import com.dasinong.farmerClub.weather.GetLiveWeather;
import com.dasinong.farmerClub.weather.Live7dFor;
import com.dasinong.farmerClub.weather.LiveWeatherData;
import com.dasinong.farmerClub.weather.SoilLiquid;
import com.dasinong.farmerClub.weather.TwentyFourHourForcast;
import com.dasinong.farmerClub.weather.SevenDayForcast.ForcastInfo;

public class WeatherFacade implements IWeatherFacade {
	
	private Logger logger = LoggerFactory.getLogger(WeatherFacade.class);
	
	@Override
	public Object getWeather(double lat, double lon) {
		try {
			Long mlId = AllMonitorLocation.getInstance().getNearest(lat, lon);
			return getWeather(mlId, lat, lon);
		} catch (Exception e) {
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("respCode", 405);
			result.put("message", "初始化检测地址列表出错");
			return result;
		}
	}

	@Override
	public Object getWeather(Long areaId, double lat, double lon) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> data = new HashMap<String, Object>();

		result.put("respCode", 200);
		result.put("message", "获取成功");

		// 获得当前天气
		GetLiveWeather glw = new GetLiveWeather(areaId.toString());
		LiveWeatherData lwd = glw.getLiveWeather();
		data.put("current", lwd);

		// 获得12小时天气
		TwentyFourHourForcast tfhf;
		GetLive24h g24 = new GetLive24h(areaId.toString());
		tfhf = g24.getLiveWeather();
		if (tfhf == null)
			tfhf = All24h.get24h().get24h(areaId);
		if (tfhf != null) {
			try {
				// ForcastDInfo[] n24h = tfhf.info;
				ForcastDInfo[] n24h = new ForcastDInfo[25];
				int count = 0;
				Date cur = new Date();
				int max = -100;
				int min = 50;
				for (ForcastDInfo f : tfhf.info) {
					if ((f != null) && f.time.after(cur)) {
						n24h[count] = f;
						count++;
					}
					if (f != null) {
						max = Math.max(f.temperature, max);
						min = Math.min(f.temperature, min);
					}
				}
				;
				lwd.daymax = Math.max(max, lwd.l1);
				lwd.daymin = Math.min(min, lwd.l1);
				data.put("n24h", n24h);

				int p1 = 0;
				int p2 = 0;
				int p3 = 0;
				int p4 = 0;

				// 获得24小时降水概率
				if (tfhf.getSize() <= 24) {
					this.logger.warn("Not enough data to compute POP on area " + areaId);
				} else {
					for (int i = 0; i < tfhf.getSize(); i++) {
						if (tfhf.info[i].time.getHours() < 6)
							p1 = Math.max(p1, tfhf.info[i].pOP);
						else if (tfhf.info[i].time.getHours() < 12)
							p2 = Math.max(p2, tfhf.info[i].pOP);
						else if (tfhf.info[i].time.getHours() < 18)
							p3 = Math.max(p3, tfhf.info[i].pOP);
						else if (tfhf.info[i].time.getHours() < 24)
							p4 = Math.max(p4, tfhf.info[i].pOP);
					}

					HashMap<String, Integer> pOP = new HashMap<String, Integer>();
					pOP.put("morning", p1);
					pOP.put("noon", p2);
					pOP.put("night", p3);
					pOP.put("nextmidnight", p4);
					data.put("POP", pOP);
				}
			} catch (Exception e) {
				this.logger.error("Process 24h failed " + areaId, e);
			}
		} else {
			this.logger.error("Get next 24h failed " + areaId);
		}

		// 获得7天预测
		try {
			if (All7d.getAll7d().get7d(areaId) != null) {
				try {
					Live7dFor l7d = GetLive7d.getAllLive7d().getLive7d(areaId);
					Long sunrise = l7d.sevenDay[0].sunrise.getTime();
					if (System.currentTimeMillis() > sunrise)
						sunrise = sunrise + 24 * 60 * 60 * 1000;
					Long sunset = l7d.sevenDay[0].sunset.getTime();
					if (System.currentTimeMillis() > sunset)
						sunset = sunset + 24 * 60 * 60 * 1000;
					data.put("sunrise", sunrise);
					data.put("sunset", sunset);
				} catch (Exception e) {
					this.logger.error("Not able to load normal 7d", e);
				}

				ForcastInfo[] n7d = All7d.getAll7d().get7d(areaId).aggregateData;
				data.put("n7d", n7d);
			}
		} catch (Exception e) {
			this.logger.error("Get next 7d failed", e);
		}

		try {
			int workable = Rule.workable(areaId);
			int sprayable = Rule.sprayable(areaId);
			data.put("workable", workable);
			data.put("sprayable", sprayable);
		} catch (Exception e) {
			this.logger.error("No detailed 24h statistic for location " + areaId, e);
			data.put("workable", -1);
			data.put("sprayable", -1);
		}
		
		double soilHum = 0;
		try {
			soilHum = SoilLiquid.getSoilLi().getSoil(lat, lon);
			soilHum = Math.round(100 * soilHum) / 100.0;
		} catch (Exception e) {
			this.logger.error("Load soilHum failed", e);
		}
		data.put("soilHum", soilHum);

		data.put("date", LunarHelper.getTodayLunar());
		result.put("data", data);
		return result;
	}
	
	@Override
	public Object getWeather(Long areaId) {
		try {
			IMonitorLocationDao locDao = (IMonitorLocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("monitorLocationDao");
			MonitorLocation loc = locDao.findByCode(areaId);
			return getWeather(areaId, loc.getLatitude(), loc.getLongitude());
		} catch (Exception e) {
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("respCode", 405);
			result.put("message", "初始化检测地址列表出错");
			return result;
		}
	}
	
	@Override
	public void setUserRegion(User user, double lat, double lon) {
		try {
			Long mlId = AllMonitorLocation.getInstance().getNearest(lat, lon);
			IMonitorLocationDao locDao = (IMonitorLocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("monitorLocationDao");
			IUserDao userdao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
			MonitorLocation loc = locDao.findByCode(mlId);
			user.setRegion(loc.getCityDetail());
		    userdao.update(user);
		}catch(Exception e){
			
		}
	}
	
	@Override
	public void setUserRegion(User user, Long mlid) {
		try {
			IMonitorLocationDao locDao = (IMonitorLocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("monitorLocationDao");
			IUserDao userdao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
			MonitorLocation loc = locDao.findByCode(mlid);
			user.setRegion(loc.getCityDetail());
		    userdao.update(user);
		}catch(Exception e){
			
		}
	}
}
