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
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.sms.SMS;
import com.dasinong.farmerClub.sms.WeatherDataShortMessage;
import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.WeatherAdmins;

public class AllCurrentJiwen implements IWeatherBuffer {
	private static AllCurrentJiwen allCurrentJiwen;
	private Logger logger = LoggerFactory.getLogger(AllCurrentJiwen.class);

	Date timeStamp = new Date(10000000);

	public static AllCurrentJiwen getCurJiwen() {
		if (allCurrentJiwen == null) {
			// allCurrentJiwen = new AllCurrentJiwen();
			allCurrentJiwen = (AllCurrentJiwen) ContextLoader.getCurrentWebApplicationContext()
					.getBean("allCurrentJiwen");
			return allCurrentJiwen;
		} else {
			return allCurrentJiwen;
		}
	}

	private AllCurrentJiwen() {
		_allCurrentJiwen = new HashMap<Integer, Integer>();
		try {
			loadContent(latestSourceFile());
		} catch (Exception e) {
			logger.error("Initialize current Jiwen failed", e);
			String content = "Initialize jiwen failed on " + new Date() + " with file " + latestSourceFile();
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
		HashMap<Integer, Integer> oldJiwen = _allCurrentJiwen;
		_allCurrentJiwen = new HashMap<Integer, Integer>();
		try {
			loadContent(sourceFile);
		} catch (Exception e) {
			logger.error("update jiwen failed", e);
			String content = "Update jiwen failed on " + new Date() + " with file " + sourceFile;
			WeatherDataShortMessage message = new WeatherDataShortMessage(0L, WeatherAdmins.getSubscribers(), content);
			SMS.sendSafe(message);
			_allCurrentJiwen = oldJiwen;
		}
	}

	private String latestSourceFile() {
		String sourceFile;
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			sourceFile = Env.getEnv().WorkingDir
					+ "/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/jw_2015-6-17.csv";
		} else {
			Date date = new Date();
			date.setTime(date.getTime() - 24 * 60 * 60 * 1000);
			String filename = "";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
			filename = "jw_" + df.format(date) + ".csv";
			sourceFile = Env.getEnv().WorkingDir + "/data/ftp/jiwen/" + filename;
		}
		return sourceFile;
	}

	private void loadContent(String sourceFile) throws IOException, ParseException {

		File f = new File(sourceFile);
		FileInputStream fr = new FileInputStream(f);

		StringBuilder notification = new StringBuilder();
		notification.append("load jiwen on " + new Date() + ". Issue loading: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(fr, "UTF-8"));
		String line;
		line = br.readLine();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
		String[] units = line.split(",");

		_allCurrentJiwen.put(Integer.parseInt(units[0]), Integer.parseInt(units[2]));
		timeStamp = df.parse(units[1]);
		while ((line = br.readLine()) != null) {
			line = line.trim();
			try {
				units = line.split(",");
				_allCurrentJiwen.put(Integer.parseInt(units[0]), Integer.parseInt(units[2]));
			} catch (Exception e) {
				logger.error("Error happend while inserting jiwen " + line);
				notification.append(line.substring(0, Math.min(line.length(), 10)) + " ");
			}
		}
		
		WeatherDataShortMessage message = new WeatherDataShortMessage(0L, WeatherAdmins.getSubscribers(), notification.toString());
		SMS.sendSafe(message);
		br.close();
		fr.close();
	}

	private HashMap<Integer, Integer> _allCurrentJiwen;

	@Override
	public String latestUpdate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmm");
		return df.format(timeStamp);
	}

	public static void main(String[] args) throws IOException, ParseException {
		Iterator iter = AllCurrentJiwen.getCurJiwen()._allCurrentJiwen.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			System.out.print(entry.getKey() + ": ");
			System.out.println(entry.getValue());
		}
	}
}
