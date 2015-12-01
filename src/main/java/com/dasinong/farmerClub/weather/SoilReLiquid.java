package com.dasinong.farmerClub.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dasinong.farmerClub.util.Env;

public class SoilReLiquid {
	private static SoilReLiquid soilReLiquid;
	
	private Logger logger = LoggerFactory.getLogger(SoilReLiquid.class);

	public static SoilReLiquid getAllSoilLi() {
		if (soilReLiquid == null) {
			soilReLiquid = new SoilReLiquid();
			return soilReLiquid;
		} else {
			return soilReLiquid;
		}
	}

	private SoilReLiquid() {
		reLiquid = new HashMap<Integer, Integer>();
		loadContent();
	}

	private void loadContent() {
		// Area is subset of
		String fullpath = "";
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			fullpath = Env.getEnv().WorkingDir
					+ "/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/aws_trsd_20150619.txt";
		} else {
			Date date = new Date();
			String filename = "";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			filename = "aws_trsd_" + df.format(date) + "09.csv";
			fullpath = Env.getEnv().WorkingDir + "/data/ftp/trsd/" + filename;
		}
		try {
			File f = new File(fullpath);
			FileInputStream fr = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fr, "UTF-8"));
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				try {
					String units[] = line.split(",");
					if (units.length == 5) {
						Integer code = Integer.parseInt(units[0]);
						Integer hum = Integer.parseInt(units[4]);
						reLiquid.put(code, hum);
					}
				} catch (Exception e) {
					logger.error("Error happend while loading relative soil liquid " + line, e);
				}
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			logger.error("Process relative soil liquid file faild", e);
		}

	}

	public Integer getReLiquid(int areaid) {
		try {
			return reLiquid.get(areaid);
		} catch (Exception e) {
			return null;
		}
	}

	private HashMap<Integer, Integer> reLiquid;

	public static void main(String args[]) {
		SoilReLiquid.getAllSoilLi().getReLiquid(101010100);
	}

}
