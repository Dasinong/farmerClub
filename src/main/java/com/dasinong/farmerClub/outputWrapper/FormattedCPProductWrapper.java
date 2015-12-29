package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;

import com.dasinong.farmerClub.model.CPProduct;
import com.dasinong.farmerClub.model.CPProductPriority;

/**
 * 
 * @author xiahonggao
 *
 * 新的CPProduct的wrapper，返回更加结构化的数据，用于android版本号>=15的版本
 * CPProductWrapper因为要向后兼容，并不能删除。
 */
public class FormattedCPProductWrapper {
	
	public class FormattedCPProductInstruction {
		public String disease;
		public String crop;
		public String volume;
		public String method;
		public String guideline = null; // can be null;
		
		public FormattedCPProductInstruction(String disease, String crop, String volume, String method, String guideline) {
			this.disease = disease;
			this.crop = crop;
			this.volume = volume;
			this.method = method;
			this.guideline = guideline;
		}
	}

	public Long id;
	public String name = "";
	public String type = "";
	public String guideline = "";
	public String registrationId = "";
	public String manufacturer = "";
	public String tip = "";
	public String telephone = "";
	public String slogan = "";
	public String specification = "";
	public String description = "";
	public String feature = "";
	public int priority;
	public String[] pictures = null;
	public String[] activeIngredient = null;
	public String[] activeIngredientUsage = null;
	public FormattedCPProductInstruction[] instructions = null;
	
	public FormattedCPProductWrapper(CPProduct cp) {
		super();
		this.name = cp.getcPProductName();
		this.type = cp.getType();
		this.priority = cp.getPriority();
		this.slogan = cp.getSlogan();
		this.specification = cp.getSpecification();
		this.feature = cp.getFeature();
		this.description = cp.getDescription();
		this.pictures = cp.getPictures();
		
		// TODO: 这是个不对的设计，guideline不应该有多种解析方式
		if (cp.getPriority() != CPProductPriority.BASF) {
			this.guideline = cp.getGuideline();
		} else {
			this.guideline = null;
		}
		this.registrationId = cp.getRegisterationId();
		this.manufacturer = cp.getManufacturer();
		this.tip = cp.getTip();
		this.id = cp.getcPProductId();
		this.telephone = cp.getTelephone();
		
		this.activeIngredient = cp.getActiveIngredient().split("\n");
		this.activeIngredientUsage = cp.getContent().split("\n");
		String[] diseaseArr = cp.getDisease().split("\n");
		String[] cropArr = cp.getCrop().split("\n");
		String[] volumeArr = cp.getVolume().split("\n");
		String[] methodArr = cp.getVolume().split("\n");
		String[] guidelineArr = cp.getGuideline().split("\n");
		this.instructions = new FormattedCPProductInstruction[cropArr.length];
		for (int i=0;i<cropArr.length;i++) {
			String guideline = null;
			if (cp.getPriority() == CPProductPriority.BASF) {
				guideline = guidelineArr[i];
			}
			this.instructions[i] = new FormattedCPProductInstruction(diseaseArr[i], cropArr[i], volumeArr[i], methodArr[i], guideline);
		}
	}
}
