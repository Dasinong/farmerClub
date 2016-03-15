package com.dasinong.farmerClub.util;

import java.util.HashSet;

public class Env {
	private static Env env;

	public static Env getEnv() {
		if (env == null) {
			env = new Env();
			return env;
		} else
			return env;

	}

	private Env() {
		checkDevice = new HashSet<String>();
		this.checkDevice.add("ie");
		this.checkDevice.add("mozilla");
		this.checkDevice.add("chrome");
		this.checkDevice.add("firefox");
		this.checkDevice.add("applewekit");
		this.DBConnection = "jdbc:mysql://120.26.208.198:3306/farmer_Club?user=root&password=weather123";
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			WorkingDir = "E:/git";
			DataDir = "E:/index";
			LuceneDir = "E:/index";
			if (System.getProperty("user.name").equalsIgnoreCase("Jason Wu")) {
				WorkingDir = "C:/Users/Jason Wu/workspace";
				DataDir = "G:/index";
				//QRCodeDir
			} else if (System.getProperty("user.name").equalsIgnoreCase("Dell")) {
				WorkingDir = "G:/Data/workbench-0703";
				DataDir = "G:/index";
				//QRCodeDir 
			}
			BaseFTP = WorkingDir + "/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/";
			BaseDATA = WorkingDir + "/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/";
			weatherAlert = false;
		} else if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
			WorkingDir = "/Users/jiangsean/git";
			BaseFTP = WorkingDir + "/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/";
			BaseDATA = WorkingDir + "/PloughHelper/src/main/java/com/dasinong/ploughHelper/weather/";
			LuceneDir = "/Users/";
			CouponQRDir = "/Users/jiangsean/coupon"; //For testing only. Should point to where server locates
			RefcodeQRDir = "/Users/jiangsean/refcode";
			weatherAlert = false;
			StockDir = "/Users/jiangsean/stock";
		} else {
			WorkingDir = "/data";
			DataDir = "/usr/local/tomcat7/webapps/";
			BaseFTP = WorkingDir + "/data/ftp";
			BaseDATA = WorkingDir + "/data/weather";
			weatherAlert = true;
			CouponQRDir = "/usr/local/tomcat7/webapps/pic/couponCampaign/QRCode"; //For testing only. Should point to where server locates
			RefcodeQRDir = "/usr/local/tomcat7/webapps/pic/refCode";
			StockDir = "/usr/local/tomcat7/webapps/pic/winsafestock";
		}
	}

	public String BaseFTP;
	public String BaseDATA;
	public String WorkingDir;
	public String DataDir;
	public String StockDir;
	public String LuceneDir;
	public String CouponQRDir;
	public String RefcodeQRDir;
	public boolean isDebug;
	public boolean weatherAlert;
	public int sessionTimeout = 60000; // 1000 min;
	public long live7dBufferTime = 60 * 60 * 1000; // 20min;
	public long accessTokenLifeMS = 1000 * 24 * 3600 * 60; // 60 days;
	public String DBConnection;
	public HashSet<String> checkDevice;

	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("user.name"));
		System.out.println(Env.getEnv().WorkingDir);
		System.out.println(Env.getEnv().BaseFTP);
		System.out.println(Env.getEnv().BaseDATA);
	}
}
