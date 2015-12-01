package com.dasinong.farmerClub.weather;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dasinong.farmerClub.datapool.AllMonitorLocation;
import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.WISHourWeather;

public class Prepare24h {
	
	private Logger logger = LoggerFactory.getLogger(Prepare24h.class);
	
	public void update24hFile() {
		String basefolder = "";
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			// basefolder =
			// Env.getEnv().WorkingDir+"/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/current";
			basefolder = "E:/weather/" + df.format(date);
		} else {
			basefolder = Env.getEnv().WorkingDir + "/data/weather/hour/" + df.format(date);
		}
		WISHourWeather wis = new WISHourWeather("0", "hforecast");
		File file = new File(basefolder);
		file.mkdir();
		// for( int id : HourCity.HourCity){
		try {
			for (Long id : AllMonitorLocation.getInstance()._allLocation.keySet()) {
				wis.setAreaId("" + id);
				String result = wis.Commute();
				try {
					file = new File(basefolder + "/" + id);
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(result);
					bw.close();
					fw.close();
				} catch (IOException e) {
					logger.error("Prepare24h update24hFile IO exception", e);
				}
			}
		} catch (Exception e) {
			logger.error("Prepare24h update24hFile exception", e);
		}
	}

	public static void main(String[] args) {
		Prepare24h p = new Prepare24h();
		p.update24hFile();
	}
}
