package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;
import java.util.Random;

import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.INatDisSpecDao;
import com.dasinong.farmerClub.model.NatDis;
import com.dasinong.farmerClub.model.NatDisSpec;

public class NatDisWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long natDisId;
	private Long natDisSpecId;
	private String natDisSpecName;
	private Long fieldId;
	private boolean natDisStatus;

	private boolean alerttype;
	private String description;

	public NatDisWrapper(NatDis nd) {
		this.setNatDisId(nd.getNatDisId());
		this.natDisSpecId = nd.getNatDisSpecId();
		this.fieldId = nd.getFieldId();
		this.natDisStatus = nd.getNatDisStatus();
		INatDisSpecDao ndsd = (INatDisSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("natDisSpecDao");
		NatDisSpec nds = ndsd.findById(this.natDisSpecId);
		this.natDisSpecName = nds.getNatDisSpecName();

		Random rnd = new Random();
		if (rnd.nextInt(5) > 2) {
			alerttype = true;
		} else
			alerttype = false;
		description = "这是一个很严重的自然灾害";

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

	public String getNatDisSpecName() {
		return natDisSpecName;
	}

	public void setNatDisSpecName(String natDisSpecName) {
		this.natDisSpecName = natDisSpecName;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public boolean isNatDisStatus() {
		return natDisStatus;
	}

	public void setNatDisStatus(boolean natDisStatus) {
		this.natDisStatus = natDisStatus;
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
