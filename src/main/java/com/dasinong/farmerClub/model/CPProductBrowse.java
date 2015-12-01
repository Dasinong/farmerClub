package com.dasinong.farmerClub.model;

import java.io.Serializable;

public class CPProductBrowse implements Serializable {

	private static final long serialVersionUID = 1L;
	private long cPProductId;
	private String model;
	private String activeIngredient;
	private String activeIngredientPY;

	public long getcPProductId() {
		return cPProductId;
	}

	public void setcPProductId(long cPProductId) {
		this.cPProductId = cPProductId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getActiveIngredient() {
		return activeIngredient;
	}

	public void setActiveIngredient(String activeIngredient) {
		this.activeIngredient = activeIngredient;
	}

	public String getActiveIngredientPY() {
		return activeIngredientPY;
	}

	public void setActiveIngredientPY(String activeIngredientPY) {
		this.activeIngredientPY = activeIngredientPY;
	}
}
