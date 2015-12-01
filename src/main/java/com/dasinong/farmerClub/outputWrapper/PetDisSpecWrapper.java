package com.dasinong.farmerClub.outputWrapper;

import java.util.ArrayList;
import java.util.List;

import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.PetSolu;

public class PetDisSpecWrapper {
	Long id;
	private String petDisSpecName = "";
	String alias = "";
	String sympton = "";
	String form = "";
	String habbit = "";
	String rule = "";
	String[] imagesPath = null;
	String imagePath = null; // for backward compatibility
	private String type = "";
	private int severity;
	private List<PetSoluWrapper> solutions = null;

	public PetDisSpecWrapper(PetDisSpec p) {
		this.id = p.getPetDisSpecId();
		this.setPetDisSpecName(p.getPetDisSpecName());
		this.alias = p.getAlias();
		this.setSeverity(p.getSeverity());
		this.sympton = p.getSympthon();
		this.form = p.getForms();
		this.habbit = p.getHabits();
		this.rule = p.getRules();
		this.imagesPath = p.getPictureIdsArray();
		this.imagePath = p.getPictureIds();
		this.setType(p.getType());

		this.solutions = new ArrayList<PetSoluWrapper>();
		if (p.getPetSolus() != null) {
			for (PetSolu solution : p.getPetSolus()) {
				this.solutions.add(new PetSoluWrapper(solution));
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSympton() {
		return sympton;
	}

	public void setSympton(String sympton) {
		this.sympton = sympton;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getHabbit() {
		return habbit;
	}

	public void setHabbit(String habbit) {
		this.habbit = habbit;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String[] getImagesPath() {
		return imagesPath;
	}

	public void setImagePath(String[] imagesPath) {
		this.imagesPath = imagesPath;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getPetDisSpecName() {
		return petDisSpecName;
	}

	public void setPetDisSpecName(String petDisSpecName) {
		this.petDisSpecName = petDisSpecName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public List<PetSoluWrapper> getSolutions() {
		return this.solutions;
	}

	public void setSolutions(List<PetSoluWrapper> solutions) {
		this.solutions = solutions;
	}

}
