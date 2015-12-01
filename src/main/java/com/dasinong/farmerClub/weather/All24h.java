package com.dasinong.farmerClub.weather;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.xml.sax.SAXException;

import com.dasinong.farmerClub.sms.SMS;
import com.dasinong.farmerClub.sms.WeatherDataShortMessage;
import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.WeatherAdmins;

public class All24h implements IWeatherBuffer {
	private static All24h all24h;
	private Logger logger = LoggerFactory.getLogger(All24h.class);

	public static All24h get24h() {
		if (all24h == null) {
			// all24h = new All24h();
			all24h = (All24h) ContextLoader.getCurrentWebApplicationContext().getBean("all24h");
			return all24h;
		} else {
			return all24h;
		}
	}

	private All24h() {
		_all24h = new HashMap<Long, TwentyFourHourForcast>();
		try {
			loadContent(latestSourceFile());
		} catch (Exception e) {
			logger.error("Initialize 24h failed", e);
			String content = "Initialize 24h failed on " + new Date() + " with file " + latestSourceFile();
			SMS.sendSafe(new WeatherDataShortMessage(content), WeatherAdmins.getSubscribers());
		}
		all24h = this;
	}

	// 自动更新
	@Override
	public void updateContent() {
		updateContent(latestSourceFile());
	}

	// 强制更新
	@Override
	public void updateContent(String basefolder) {
		// HashMap<Long,TwentyFourHourForcast> old24h = _all24h;
		// _all24h = new HashMap<Long,TwentyFourHourForcast>();
		try {
			loadContent(basefolder);
		} catch (Exception e) {
			logger.error("update 24h failed", e);
			String content = "Update 24h failed on " + new Date() + " with file " + basefolder;
			SMS.sendSafe(new WeatherDataShortMessage(content), WeatherAdmins.getSubscribers());
		}
	}

	private String latestSourceFile() {
		String basefolder;
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			basefolder = "E:/weather/2015072120";
		} else {
			basefolder = Env.getEnv().WorkingDir + "/data/weather/hour/current";
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			if (date.getHours() <= 7) {
				date.setTime(date.getTime() - 24 * 60 * 60 * 1000);
				basefolder = Env.getEnv().WorkingDir + "/data/weather/hour/" + df.format(date) + "20";
			} else if (date.getHours() < 20) {
				basefolder = Env.getEnv().WorkingDir + "/data/weather/hour/" + df.format(date) + "08";
			} else if (date.getHours() == 20 && date.getMinutes() <= 15) {
				basefolder = Env.getEnv().WorkingDir + "/data/weather/hour/" + df.format(date) + "08";
			} else {
				basefolder = Env.getEnv().WorkingDir + "/data/weather/hour/" + df.format(date) + "20";
			}
		}
		return basefolder;
	}

	private void loadContent(String basefolder) {
		StringBuilder notification = new StringBuilder();
		notification.append("load 24h on " + new Date() + ". Issue loading: ");
		TwentyFourHourForcast tfhf = null;

		File f = new File(basefolder);
		if (f.isDirectory()) {
			String[] filelist = f.list();
			for (int i = 0; i < filelist.length; i++) {
				try {
					tfhf = new TwentyFourHourForcast(basefolder + "/" + filelist[i], Long.parseLong(filelist[i]));
					_all24h.put(tfhf.code, tfhf);
				} catch (Exception e) {
					logger.error("Load 24h for " + filelist[i] + " failed", e);
					notification.append(filelist[i] + " ");
				}
			}
		}
		
		SMS.sendSafe(new WeatherDataShortMessage(notification.toString()), WeatherAdmins.getSubscribers());
	}

	HashMap<Long, TwentyFourHourForcast> _all24h;

	public TwentyFourHourForcast get24h(Long areaId) {
		return _all24h.get(areaId);
	}

	// Support finer check.
	@Override
	public String latestUpdate() {
		TwentyFourHourForcast tfhf = this._all24h.get(101050407);
		if (tfhf != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmm");
			return df.format(tfhf.timeStamp);
		} else
			return "No data found. Check whether initialize failed.";
	}

	public String updateLocation(long monitorLocationId) {
		GetLive24h gl24 = new GetLive24h();
		gl24.setAreaId("" + monitorLocationId);
		TwentyFourHourForcast tfhf = gl24.getLiveWeather();
		if (tfhf != null) {
			this._all24h.put(tfhf.code, tfhf);
			return "update " + monitorLocationId + " success";
		} else
			return "update " + monitorLocationId + " failed";
	}

	public static void main(String[] args)
			throws IOException, ParseException, NumberFormatException, ParserConfigurationException, SAXException {
		/*
		 * System.out.println(System.getProperty("OS"));
		 * 
		 * Iterator iter= All24h.get24h()._all24h.entrySet().iterator();
		 * while(iter.hasNext()){ Map.Entry entry = (Map.Entry) iter.next();
		 * System.out.print(entry.getKey()+": ");
		 * System.out.println(((TwentyFourHourForcast)
		 * entry.getValue()).info[1].temperature);
		 * System.out.println(((TwentyFourHourForcast)
		 * entry.getValue()).info[2].temperature);
		 * System.out.println(((TwentyFourHourForcast)
		 * entry.getValue()).info[3].windDirection_10m); }
		 */

		System.out.println(All24h.get24h().updateLocation(101010100));
	}

}
