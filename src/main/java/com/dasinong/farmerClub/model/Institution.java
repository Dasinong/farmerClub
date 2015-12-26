package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Institution implements Serializable {
	
	public static final long DOWS = 1;
	public static final long YANHUA = 2;
	public static final long BASF = 3;

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String code;
	private List<CouponCampaign> couponCampaigns = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public List<CouponCampaign> getCouponCampaigns() {
		return this.couponCampaigns;
	}
	
	public void setCouponCampaigns(List<CouponCampaign> campaigns) {
		this.couponCampaigns = campaigns;
	}
}
