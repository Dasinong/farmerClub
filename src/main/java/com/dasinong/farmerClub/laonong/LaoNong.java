package com.dasinong.farmerClub.laonong;

public class LaoNong {
	public Long id; // DEPRECATED: 但是android还在使用
	public int type; // type = 3 为 农谚， =2 为天气预警， =1 为广告
	public String picName; // 图片名称，老农头
	public String title;
	public String content;
	public String url;

	public LaoNong(Long id, int type, String picName, String title, String content, String url) {
		super();
		this.id = id;
		this.type = type;
		this.picName = picName;
		this.title = title;
		this.content = content;
		this.url = url;

	}
}
