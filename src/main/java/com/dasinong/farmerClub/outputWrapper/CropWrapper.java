package com.dasinong.farmerClub.outputWrapper;

import com.dasinong.farmerClub.model.Crop;

public class CropWrapper {
	
	public Long cropId;
	public String cropName;
	public String type;
	public String iconUrl;

	public CropWrapper(Crop crop) {
		this.cropId = crop.getCropId();
		this.cropName = crop.getCropName();
		this.type = crop.getType();
		this.iconUrl = crop.getIconUrl();
	}

}
