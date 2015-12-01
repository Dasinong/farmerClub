package com.dasinong.farmerClub.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.IMonitorLocationDao;
import com.dasinong.farmerClub.model.MonitorLocation;

public class LoadMonitorLocation {
	public static void main(String[] args) throws IOException {
		// This is the same with @ContextConfiguration
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		// This is the same with autowired.
		IMonitorLocationDao monitorLocationDao = (IMonitorLocationDao) applicationContext.getBean("monitorLocationDao");

		String fullpath = "/Users/jiangsean/git/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/MonitorLocation.txt";
		MonitorLocation m;
		File f = new File(fullpath);
		FileInputStream fr = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fr, "UTF-8"));
		String line;
		while ((line = br.readLine()) != null) {
			try {
				line = line.trim();
				String units[] = line.split(" ");
				if (units.length == 6) {
					String city = units[0];
					int postCode = 0;
					try {
						postCode = Integer.parseInt(units[1]);
					} catch (Exception e) {
					}
					String cityDetail = units[2];
					double latitude = Double.parseDouble(units[3]);
					double longitudu = Double.parseDouble(units[4]);
					int code = Integer.parseInt(units[5]);
					m = new MonitorLocation(city, postCode, cityDetail, latitude, longitudu, code);
					monitorLocationDao.save(m);
				}
			} catch (Exception e) {
				System.out.println("Error happend while inserting monitor location " + line);
			}
		}
		br.close();
		fr.close();
	}
}
