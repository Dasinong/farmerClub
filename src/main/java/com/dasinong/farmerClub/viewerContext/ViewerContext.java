package com.dasinong.farmerClub.viewerContext;

import javax.servlet.http.HttpServletRequest;

import com.dasinong.farmerClub.model.DasinongApp;

public class ViewerContext {

	public final static String REQUEST_KEY = "__VIEWER_CONTEXT__";

	private Long userId = null;
	private Long appId = null;
	private String deviceId = null;
	private Integer version = null;

	public ViewerContext() {
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getUserId() {
		return this.userId;
	}

	public Long getAppId() {
		return this.appId;
	}
	
	public Integer getVersion() {
		return this.version;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public boolean isUserLogin() {
		return this.userId != null;
	}

	public boolean isAndroidFarmLog() {
		return DasinongApp.ANDROID_FARM_LOG.equals(this.appId);
	}

	public boolean isIOSFarmLog() {
		return DasinongApp.IOS_FARM_LOG.equals(this.appId);
	}
}
