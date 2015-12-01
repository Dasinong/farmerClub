package com.dasinong.farmerClub.model;

import java.io.Serializable;

public class PetDis implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long petDisId;
	private Long petDisSpecId;
	private Long fieldId;
	private boolean petDisStatus;

	public PetDis() {
	}

	public PetDis(boolean petDisStatus) {
		this.petDisStatus = petDisStatus;
	}

	public PetDis(Long petDisSpecId, boolean petDisStatus) {
		this.petDisSpecId = petDisSpecId;
		this.petDisStatus = petDisStatus;
	}

	public PetDis(PetDisSpec petDisSpec, boolean petDisStatus) {
		this.petDisSpecId = petDisSpec.getPetDisSpecId();
		this.petDisStatus = petDisStatus;
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

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public Boolean getPetDisStatus() {
		return petDisStatus;
	}

	public void setPetDisStatus(Boolean petDisStatus) {
		this.petDisStatus = petDisStatus;
	}

	public Long getKey() {
		return this.petDisSpecId;
	}

	public boolean getValue() {
		return this.petDisStatus;
	}
}
