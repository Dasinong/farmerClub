package com.dasinong.farmerClub.outputWrapper;

import com.dasinong.farmerClub.model.Crop;

public class CropWrapper {
	Long cropId;
	String cropName;
	String type;

	public CropWrapper(Crop c) {
		this.cropId = c.getCropId();
		this.cropName = c.getCropName();
		this.type = c.getType();
	}

	public Long getCropId() {
		return cropId;
	}

	public void setCropId(Long cropId) {
		this.cropId = cropId;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
