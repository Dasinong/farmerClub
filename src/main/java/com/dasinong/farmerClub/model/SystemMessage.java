package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.Date;

public class SystemMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String province;
	private String city;
	private String county;
	private String district;
	private String community;
	private String channel;
	private String content;
	private Date startTime;
	private Date endTime;
	private String picUrl;
	private String landingUrl;
	private Long institutionId;

	public SystemMessage() {}

	public String getProvince() {
		return this.province;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getCounty() {
		return this.county;
	}
	
	public String getDistrict() {
		return this.district;
	}
	
	public String getCommunity() {
		return this.community;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	
	public void setCommunity(String community) {
		this.community = community;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getLandingUrl() {
		return landingUrl;
	}

	public void setLandingUrl(String landingUrl) {
		this.landingUrl = landingUrl;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	
	public boolean isActive() {
		Long cur = (new Date()).getTime();
		return this.getStartTime().getTime() <= cur && this.getEndTime().getTime() >= cur;
	}

}
