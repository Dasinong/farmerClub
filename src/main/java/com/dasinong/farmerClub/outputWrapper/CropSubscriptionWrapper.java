package com.dasinong.farmerClub.outputWrapper;

import java.sql.Timestamp;
import java.util.HashMap;

import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.CropSubscription;

public class CropSubscriptionWrapper {
	
	public Long id;
	public CropWrapper crop;
	public Long userId;
	public HashMap<Long, String> fields;
	public Timestamp createdAt;
	
	public CropSubscriptionWrapper(CropSubscription subs, HashMap<Long, String> fields) {
		this.id = subs.getId();
		
		Long cropId = subs.getCropId();
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		Crop crop = cropDao.findById(cropId);
		this.crop = new CropWrapper(crop);
		
		this.userId = subs.getUserId();
		this.createdAt = subs.getCreatedAt();
		this.fields = fields;
	}
}
