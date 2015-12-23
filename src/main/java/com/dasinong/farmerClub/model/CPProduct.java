package com.dasinong.farmerClub.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CPProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long cPProductId;
	private String cPProductName;
	private Set<PetSolu> petSolus = new HashSet<PetSolu>();

	private String registerationId;
	private String activeIngredient;
	private String type;
	private String manufacturer;
	private String crop;
	private String disease;
	private String volume;
	private String method;
	private String guideline;
	private String tip;
	private String model;
	private Integer priority;
	private String telephone;
	private String slogan;
	private String content;
	private String description;
	private String feature;
	private String specification;
	private String pictureUrl;

	public CPProduct() {
	}

	public CPProduct(String cPProductName, String activeIngredient, String type, String crop, String disease,
			String volume, String guideline, String tip, Integer priority, String telephone) {
		super();
		this.cPProductName = cPProductName;
		this.activeIngredient = activeIngredient;
		this.type = type;
		this.crop = crop;
		this.disease = disease;
		this.volume = volume;
		this.guideline = guideline;
		this.tip = tip;
		this.priority = priority;
		this.telephone = telephone;
	}

	public Long getcPProductId() {
		return cPProductId;
	}

	public void setcPProductId(Long cPProductId) {
		this.cPProductId = cPProductId;
	}

	public String getcPProductName() {
		return cPProductName;
	}

	public void setcPProductName(String cPProductName) {
		this.cPProductName = cPProductName;
	}

	public Set<PetSolu> getPetSolus() {
		return petSolus;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPetSolus(Set<PetSolu> petSolus) {
		this.petSolus = petSolus;
	}

	public String getActiveIngredient() {
		return activeIngredient;
	}

	public void setActiveIngredient(String activeIngredient) {
		this.activeIngredient = activeIngredient;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCrop() {
		return crop;
	}

	public void setCrop(String crop) {
		this.crop = crop;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getGuideline() {
		return guideline;
	}

	public void setGuideline(String guideline) {
		this.guideline = guideline;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRegisterationId() {
		return registerationId;
	}

	public void setRegisterationId(String registerationId) {
		this.registerationId = registerationId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getSlogan() {
		return this.slogan;
	}
	
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFeature() {
		return this.feature;
	}
	
	public void setFeature(String feature) {
		this.feature = feature;
	}
	
	public String getSpecification() {
		return this.specification;
	}
	
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	public String getPictureUrl() {
		return this.pictureUrl;
	}
	
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
}
