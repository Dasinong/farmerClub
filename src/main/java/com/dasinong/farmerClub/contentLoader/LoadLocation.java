package com.dasinong.farmerClub.contentLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.model.Location;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class LoadLocation {

	@Transactional
	public void loadLocation() throws FileNotFoundException, IOException {
		ILocationDao ld = (ILocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");

		File file = new File("C:\\Data\\lo1.txt");

		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

		String temp = "";
		while ((temp = br.readLine()) != null) {
			String[] contents = temp.split(",");
			if (contents.length == 10) {

				double lat = Double.parseDouble(contents[7]);
				double lon = Double.parseDouble(contents[8]);
				Location lo = new Location(contents[1], contents[2], contents[3], contents[4], contents[5], contents[6],
						lat, lon);
				// lo.setLocationId(Long.parseLong(contents[0]));
				ld.save(lo);
			}
		}
		br.close();
		fis.close();
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

		String url = "jdbc:MySQL://localhost/farmerClub?useUnicode=true&characterEncoding=UTF-8";
		String user = "root";
		String password = "11111111";

		File file = new File("C:\\Data\\lo.txt");

		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

		String temp = "";
		Connection con = (Connection) DriverManager.getConnection(url, user, password);
		try {

			while ((temp = br.readLine()) != null) {
				String[] contents = temp.split(",");
				Statement statement = (Statement) con.createStatement();
				StringBuilder query = new StringBuilder();
				if (contents.length == 10) {
					query.append("insert into location (locationId,region,province,city,country,district,"
							+ "community,latitude,longtitude) " + "values (" + contents[0]);
					for (int i = 1; i < 9; i++) {
						query.append(",'" + contents[i] + "'");
					}
					// query.deleteCharAt(query.length()-1);
					query.append(")");
				}
				System.out.println(query);
				statement.executeUpdate(query.toString());
				statement.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			con.close();
		}
		br.close();
		fis.close();
	}

}
