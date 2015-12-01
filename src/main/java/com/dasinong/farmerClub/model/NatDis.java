package com.dasinong.farmerClub.model;

import java.io.Serializable;

public class NatDis implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long natDisId;
	private Long natDisSpecId;
	private Long fieldId;
	private boolean natDisStatus;

	public NatDis() {
	}

	public NatDis(boolean natDisStatus) {
		this.natDisStatus = natDisStatus;
	}

	public NatDis(NatDisSpec natDisSpec, boolean natDisStatus) {
		this.natDisSpecId = natDisSpec.getNatDisSpecId();
		this.natDisStatus = natDisStatus;
	}

	public NatDis(Long natDisSpecId, boolean natDisStatus) {
		this.natDisSpecId = natDisSpecId;
		this.natDisStatus = natDisStatus;
	}

	public Long getNatDisId() {
		return natDisId;
	}

	public void setNatDisId(Long natDisId) {
		this.natDisId = natDisId;
	}

	public Long getNatDisSpecId() {
		return natDisSpecId;
	}

	public void setNatDisSpecId(Long natDisSpecId) {
		this.natDisSpecId = natDisSpecId;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public Boolean getNatDisStatus() {
		return natDisStatus;
	}

	public void setNatDisStatus(Boolean natDisStatus) {
		this.natDisStatus = natDisStatus;
	}

	public Long getKey() {
		return this.natDisSpecId;
	}

	public boolean getValue() {
		return this.natDisStatus;
	}
}
