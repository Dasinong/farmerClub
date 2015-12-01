package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;
import java.util.Random;

import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.model.PetDis;
import com.dasinong.farmerClub.model.PetDisSpec;

public class PetDisWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long petDisId;
	private Long petDisSpecId;
	private String petDisSpecName;
	private Long fieldId;
	private boolean petDisStatus;

	private boolean alerttype;
	private String type;
	private String description;

	public PetDisWrapper(PetDis pd) {
		this.petDisId = pd.getPetDisId();
		this.petDisSpecId = pd.getPetDisSpecId();
		this.fieldId = pd.getFieldId();
		this.petDisStatus = pd.getPetDisStatus();
		IPetDisSpecDao pdsd = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("petDisSpecDao");
		PetDisSpec pds = pdsd.findById(petDisSpecId);
		this.petDisSpecName = pds.getPetDisSpecName();
		this.type = pds.getType();
		this.description = pds.getSympthon();
		Random rnd = new Random();
		if (rnd.nextInt(5) > 2) {
			setAlerttype(true);
		} else
			setAlerttype(false);
	}

	public Long getPetDisId() {
		return petDisId;
	}

	public void setPetDisId(Long petDisId) {
		this.petDisId = petDisId;
	}

	public Long getPetDisSpecId() {
		return petDisSpecId;
	}

	public void setPetDisSpecId(Long petDisSpecId) {
		this.petDisSpecId = petDisSpecId;
	}

	public String getPetDisSpecName() {
		return petDisSpecName;
	}

	public void setPetDisSpecName(String petDisSpecName) {
		this.petDisSpecName = petDisSpecName;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public boolean isPetDisStatus() {
		return petDisStatus;
	}

	public void setPetDisStatus(boolean petDisStatus) {
		this.petDisStatus = petDisStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAlerttype() {
		return alerttype;
	}

	public void setAlerttype(boolean alerttype) {
		this.alerttype = alerttype;
	}

}
