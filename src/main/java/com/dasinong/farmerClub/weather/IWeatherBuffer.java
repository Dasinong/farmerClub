package com.dasinong.farmerClub.weather;

import java.util.Map;

public interface IWeatherBuffer {
	// 自动更新
	public void updateContent();

	// 强制更新
	public void updateContent(String source);

	// 检测最近更新
	public String latestUpdate();

}
