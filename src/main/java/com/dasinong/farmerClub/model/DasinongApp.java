package com.dasinong.farmerClub.model;

import java.sql.Timestamp;

public class DasinongApp {

	public static Long ANDROID_FARM_LOG = 1L;
	public static Long IOS_FARM_LOG = 2L;
	public static Long NONG_SHI_TIAN_DI_COM = 3L;

	private Long appId;
	private String appName;
	private String appSecret;
	private Timestamp createdAt;

	public DasinongApp() {
	}

	public DasinongApp(Long appId, String appName, String appSecret, Timestamp createdAt) {
		this.appId = appId;
		this.appName = appName;
		this.appSecret = appSecret;
		this.createdAt = createdAt;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Long getAppId() {
		return this.appId;
	}

	public String getAppName() {
		return this.appName;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}
}
