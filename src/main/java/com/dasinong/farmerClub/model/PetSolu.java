package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//PestDisasterSolution
public class PetSolu implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long petSoluId;
	private String petSoluDes = "";
	private String providedBy = "";
	private PetDisSpec petDisSpec;
	private Set<CPProduct> cPProducts = new HashSet<CPProduct>();

	private boolean isRemedy = true; // true for cure, false for prevent
	private boolean isCPSolu = true;
	private int rank = 0;
	private String subStageId;

	public PetSolu(String petSoluDes, PetDisSpec petDisSpec, boolean isRemedy, boolean isCPSolu) {
		super();
		this.petSoluDes = petSoluDes;
		this.petDisSpec = petDisSpec;
		this.isRemedy = isRemedy;
		this.isCPSolu = isCPSolu;
	}

	public PetSolu() {
	}

	public PetSolu(String petSoluName, PetDisSpec petDisSpec) {
		this.setPetSoluDes(petSoluName);
		this.petDisSpec = petDisSpec;
	}

	public Long getPetSoluId() {
		return petSoluId;
	}

	public void setPetSoluId(Long petSoluId) {
		this.petSoluId = petSoluId;
	}

	public PetDisSpec getPetDisSpec() {
		return petDisSpec;
	}

	public void setPetDisSpec(PetDisSpec petDisSpec) {
		this.petDisSpec = petDisSpec;
	}

	public Set<CPProduct> getcPProducts() {
		return cPProducts;
	}

	public void setcPProducts(Set<CPProduct> cPProducts) {
		this.cPProducts = cPProducts;
	}

	public boolean getIsCPSolu() {
		return isCPSolu;
	}

	public void setIsCPSolu(boolean isCPSolu) {
		this.isCPSolu = isCPSolu;
	}

	public String getPetSoluDes() {
		return petSoluDes;
	}

	public void setPetSoluDes(String petSoluDes) {
		this.petSoluDes = petSoluDes;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public boolean getIsRemedy() {
		return isRemedy;
	}

	public void setIsRemedy(boolean isRemedy) {
		this.isRemedy = isRemedy;
	}

	public String getProvidedBy() {
		return providedBy;
	}

	public void setProvidedBy(String providedBy) {
		this.providedBy = providedBy;
	}

	public String getSubStageId() {
		return subStageId;
	}

	public void setSubStageId(String subStageId) {
		this.subStageId = subStageId;
	}
}
