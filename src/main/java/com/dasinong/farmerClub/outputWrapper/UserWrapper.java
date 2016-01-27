package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.User;

public class UserWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String userName = "";
	private String cellPhone = "";
	private String address = "";
	private boolean isPassSet = false;
	private boolean authenticated = false;
	private String pictureId;
	private String telephone;
	private String qqtoken;
	private String weixintoken;
	private String channel;
	private String refcode;
	private Long refuid;
	private Long institutionId;
	private String userType;
	private Boolean isEmployee = false;
	private int memberPoints;
	private int memberLevel;

	private List<Long> fields = new ArrayList<Long>();
	private List<Long> monitorLocationId = new ArrayList<Long>();

	public UserWrapper(User user) {
		this.setIsPassSet(user.getIsPassSet());
		this.userId = user.getUserId();
		this.userName = (user.getUserName() == null) ? "" : user.getUserName();
		this.address = (user.getAddress() == null) ? "" : user.getAddress();
		this.cellPhone = (user.getCellPhone() == null) ? "" : user.getCellPhone();
		if (user.getFields() != null) {
			for (Field f : user.getFields()) {
				getFields().add(f.getFieldId());
				this.getMonitorLocationId().add(f.getMonitorLocationId());
			}
		}

		this.setAuthenticated(user.getAuthenticated());
		this.pictureId = (user.getPictureId() == null) ? "" : user.getPictureId();
		this.telephone = (user.getTelephone() == null) ? "" : user.getTelephone();
		this.qqtoken = user.getQqtoken();
		this.weixintoken = user.getWeixintoken();
		this.channel = user.getChannel();
		this.setRefcode(user.getRefcode());
		this.refuid = user.getRefuid() == null ? -1 : user.getRefuid();
		this.institutionId = user.getInstitutionId();
		this.isEmployee = user.getIsEmployee();
		this.userType = user.getUserType();
		this.memberPoints = user.getMemberPoints();
		this.memberLevel = user.getMemberLevel();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Long> getFields() {
		return fields;
	}

	public void setFields(List<Long> fields) {
		this.fields = fields;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Long> getMonitorLocationId() {
		return monitorLocationId;
	}

	public void setMonitorLocationId(List<Long> monitorLocationId) {
		this.monitorLocationId = monitorLocationId;
	}

	public boolean getIsPassSet() {
		return isPassSet;
	}

	public void setIsPassSet(boolean isPassSet) {
		this.isPassSet = isPassSet;
	}

	public String getQqtoken() {
		return qqtoken;
	}

	public void setQqtoken(String qqtoken) {
		this.qqtoken = qqtoken;
	}

	public String getWeixintoken() {
		return weixintoken;
	}

	public void setWeixintoken(String weixintoken) {
		this.weixintoken = weixintoken;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRefcode() {
		return refcode;
	}

	public void setRefcode(String refcode) {
		this.refcode = refcode;
	}

	public Long getRefuid() {
		return refuid;
	}

	public void setRefuid(Long refuid) {
		this.refuid = refuid;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	
	public Boolean getIsEmployee() {
		return this.isEmployee;
	}
	
	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	
	public String getUserType() {
		return this.userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getMemberPoints() {
		return memberPoints;
	}

	public void setMemberPoints(int memberPoints) {
		this.memberPoints = memberPoints;
	}

	public int getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}

}
