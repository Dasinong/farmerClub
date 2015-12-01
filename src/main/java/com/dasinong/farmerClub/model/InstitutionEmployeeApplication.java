package com.dasinong.farmerClub.model;

public class InstitutionEmployeeApplication {

	private Long id;
	private String contactName;
	private Long institutionId;
	private String cellphone;
	private String title;
	private String region;

	public InstitutionEmployeeApplication() {
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getContactName() {
		return this.contactName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setInstitutionId(Long id) {
		this.institutionId = id;
	}

	public Long getInstitutionId() {
		return this.institutionId;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion() {
		return this.region;
	}
}
