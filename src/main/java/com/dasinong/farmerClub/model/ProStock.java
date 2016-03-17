package com.dasinong.farmerClub.model;

import java.util.Date;

public class ProStock {
	private long id;
	private long userid;
	private long prodid;
	private String prodname;
	private String prospecial;
	private Date scanat;
	private String boxcode;
	public ProStock(){}
	
	public ProStock(long userid, long prodid, String prodname, String prospecial, Date scanat,
			String boxcode) {
		super();
		this.userid = userid;
		this.prodid = prodid;
		this.prodname = prodname;
		this.prospecial = prospecial;
		this.scanat = scanat;
		this.boxcode = boxcode;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getProdid() {
		return prodid;
	}
	public void setProdid(long prodid) {
		this.prodid = prodid;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public String getProspecial() {
		return prospecial;
	}
	public void setProspecial(String prospecial) {
		this.prospecial = prospecial;
	}
	
	public Date getScanat() {
		return scanat;
	}
	public void setScanat(Date scanat) {
		this.scanat = scanat;
	}
	public String getBoxcode() {
		return boxcode;
	}
	public void setBoxcode(String boxcode) {
		this.boxcode = boxcode;
	}

}
