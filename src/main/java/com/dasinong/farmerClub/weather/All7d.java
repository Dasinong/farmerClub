package com.dasinong.farmerClub.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.sms.SMS;
import com.dasinong.farmerClub.sms.WeatherDataShortMessage;
import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.WeatherAdmins;

public class All7d implements IWeatherBuffer {
	private static All7d all7d;
	private Logger logger = LoggerFactory.getLogger(All7d.class);

	public static All7d getAll7d() {
		if (all7d == null) {
			// all7d = new All7d();
			all7d = (All7d) ContextLoader.getCurrentWebApplicationContext().getBean("all7d");
			return all7d;
		} else {
			return all7d;
		}
	}

	private All7d() {
		_all7d = new HashMap<Long, SevenDayForcast>();
		try {
			loadContent(latestSourceFile());
		} catch (Exception e) {
			logger.error("Initialize 7d failed", e);
			String content = "Initialize 7d failed on " + new Date() + " with file " + latestSourceFile();
			WeatherDataShortMessage message = new WeatherDataShortMessage(0L, WeatherAdmins.getSubscribers(), content);
			SMS.sendSafe(message);
		}
	}

	// 自动更新
	@Override
	public void updateContent() {
		updateContent(latestSourceFile());
	}

	// 强制更新
	@Override
	public void updateContent(String sourceFile) {
		HashMap<Long, SevenDayForcast> old7d = _all7d;
		_all7d = new HashMap<Long, SevenDayForcast>();
		try {
			loadContent(sourceFile);
		} catch (Exception e) {
			logger.error("update 7d failed", e);
			String content = "update 7d failed on " + new Date() + " with file " + sourceFile;
			//WeatherDataShortMessage message = new WeatherDataShortMessage(0L, WeatherAdmins.getSubscribers(), content);
			//SMS.sendSafe(message);
			System.out.println(content);
			_all7d = old7d;
		}
	}

	private String latestSourceFile() {
		String sourceFile;
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")
				|| System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
			sourceFile = Env.getEnv().WorkingDir
					+ "/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/rforcast_7days_2015090710.csv";
		} else {
			Date date = new Date();
			String filename = "";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			if (date.getHours() <= 9) {
				date.setTime(date.getTime() - 24 * 60 * 60 * 1000);
				filename = "rforcast_7days_" + df.format(date) + "20.csv";
			} else if (date.getHours() <= 19) {
				filename = "rforcast_7days_" + df.format(date) + "09.csv";
			} else {
				filename = "rforcast_7days_" + df.format(date) + "20.csv";
			}
			sourceFile = Env.getEnv().WorkingDir + "/data/ftp/rforecast7days/" + filename;
		}
		return sourceFile;
	}

	private void loadContent(String sourceFile) throws IOException {
		SevenDayForcast sdf = null;
		TwentyFourHourForcast tfhf = null;
		Date curtime = new Date();
		File f = new File(sourceFile);

		FileInputStream fr;
		fr = new FileInputStream(f);
		StringBuilder notification = new StringBuilder();
		notification.append("load 7d on " + new Date() + ". Issue loading: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(fr, "UTF-8"));
		String line;
		br.readLine();
		long currentCode = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		while ((line = br.readLine()) != null) {
			line = line.trim();
			try {
				String units[] = line.split("\t");
				if (units.length == 9) {
					long code = Long.parseLong(units[0]);
					Date forcast_time = df.parse(units[1]);
					short weather = Short.parseShort(units[2]);
					double temp = Double.parseDouble(units[3]);
					double max_temp = Double.parseDouble(units[4]);
					double min_temp = Double.parseDouble(units[5]);
					short ff_level = Short.parseShort(units[6]);
					short dd_level = Short.parseShort(units[7]);
					double rain = Double.parseDouble(units[8]);
					// Use for 24h

					if (code != currentCode) {
						sdf = new SevenDayForcast(code, forcast_time);
						currentCode = code;
						if (tfhf != null)
							tfhf.padding();
						if ((forcast_time.getTime() - curtime.getTime() < 25 * 60 * 60 * 1000)
								&& !HourCity.contains(currentCode)) {
							tfhf = new TwentyFourHourForcast(currentCode);
							ForcastDInfo fdi = new ForcastDInfo(forcast_time, (int) temp, -1, wind7dto24h(ff_level),
									wins7dto24h(dd_level), rain, 0, 0, 0,
									WeatherPhenomena.getWeatherPhenomena().getIcon(weather));
							tfhf.add(fdi);
							All24h.get24h()._all24h.put(currentCode, tfhf);
						}
						sdf.addRawData(forcast_time, weather, temp, max_temp, min_temp, ff_level, dd_level, rain);
						_all7d.put(code, sdf);
					} else {
						sdf.addRawData(forcast_time, weather, temp, max_temp, min_temp, ff_level, dd_level, rain);
						if ((forcast_time.getTime() - curtime.getTime() < 25 * 60 * 60 * 1000)
								&& !HourCity.contains(currentCode)) {
							ForcastDInfo fdi = new ForcastDInfo(forcast_time, (int) temp, -1, wind7dto24h(ff_level),
									wins7dto24h(dd_level), rain, 0, 0, 0,
									WeatherPhenomena.getWeatherPhenomena().getIcon(weather));
							tfhf.add(fdi);
						}
					}
				} else {
					notification.append(units[0] + " ");
				}
			} catch (Exception e) {
				logger.error("Error happend while inserting 7 day forcast " + line, e);
				notification.append(line.substring(0, Math.min(line.length(), 10)) + " ");
			}
		}
		if (tfhf != null)
			tfhf.padding();
		WeatherDataShortMessage message = new WeatherDataShortMessage(0L, WeatherAdmins.getSubscribers(), notification.toString());
		SMS.sendSafe(message);
		br.close();
		fr.close();
	}

	private HashMap<Long, SevenDayForcast> _all7d;

	public SevenDayForcast get7d(Long aid) {
		return _all7d.get(aid);
	}

	@Override
	public String latestUpdate() {
		SevenDayForcast sdf = this._all7d.get(101010100);
		if (sdf != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmm");
			return df.format(sdf.startDate);
		} else
			return "No data found. Check whether initialize failed.";
	}

	private int wind7dto24h(short ff_level) {
		switch (ff_level) {
		case 0:
			return 0;
		case 1:
			return 45;
		case 2:
			return 90;
		case 3:
			return 135;
		case 4:
			return 180;
		case 5:
			return 225;
		case 6:
			return 270;
		case 7:
			return 315;
		case 8:
			return 360;
		default:
			return 0;
		}
	}

	private double wins7dto24h(short dd_level) {
		switch (dd_level) {
		case 0:
			return 2.2;
		case 1:
			return 5;
		case 2:
			return 6.3;
		case 3:
			return 9.1;
		case 4:
			return 12;
		case 5:
			return 15.5;
		case 6:
			return 19.0;
		case 7:
			return 22.5;
		case 8:
			return 26.9;
		case 9:
			return 30.5;
		default:
			return 0;
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		/*
		 * Iterator iter= All7d.getAll7d()._all7d.entrySet().iterator();
		 * while(iter.hasNext()){ Map.Entry entry = (Map.Entry) iter.next();
		 * System.out.print(entry.getKey()+": ");
		 * System.out.println(entry.getValue()); }
		 * All7d.getAll7d().get7d(101090301);
		 */
	}
}
