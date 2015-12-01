package com.dasinong.farmerClub.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SunShine {
	private static SunShine sunShine;

	public static SunShine getSunS() throws IOException, ParseException {
		if (sunShine == null) {
			sunShine = new SunShine();
			return sunShine;
		} else {
			return sunShine;
		}
	}

	private SunShine() throws IOException, ParseException {
		loadContent();
	}

	private void loadContent() throws IOException, ParseException {
		SevenDayHumidity sdh = null;
		// File f = new
		// File("/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/MonitorLocation.txt");
		File f = new File("./src/main/java/com/dasinong/ploughHelper/weather/sunshine_20150618.txt");
		FileInputStream fr = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fr, "UTF-8"));
		String line;
		br.readLine();
		int i = 0;
		while ((line = br.readLine()) != null) {
			line = line.trim();
		}
		br.close();
		fr.close();
	}

	public static void main(String[] arsgs) throws IOException, ParseException {

	}
}
