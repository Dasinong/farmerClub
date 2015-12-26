package com.dasinong.farmerClub.outputWrapper;

import com.dasinong.farmerClub.model.Institution;

public class InstitutionWrapper {

	public long id;
	public String name;
	public String code;
	
	public InstitutionWrapper(Institution inst) {
		this.id = inst.getId();
		this.name = inst.getName();
		this.code = inst.getCode();
	}
}
