package com.dasinong.farmerClub.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.dao.ICropSubscriptionDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.CropSubscription;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.CropDetailsWrapper;
import com.dasinong.farmerClub.outputWrapper.CropSubscriptionWrapper;

@Transactional
public class CropFacade implements ICropFacade {

	@Override
	public CropDetailsWrapper getCropDetailsById(long cropId) {
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		Crop crop = cropDao.findById(cropId);
		return new CropDetailsWrapper(crop);
	}
	
	@Override
	public CropDetailsWrapper getCropDetailsByIdAndSubStageId(long cropId, long subStageId) {
		ICropDao cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		Crop crop = cropDao.findById(cropId);
		return new CropDetailsWrapper(crop, subStageId);
	}

	@Override
	public List<CropSubscriptionWrapper> getCropSubscriptions(long userId) {
		ICropSubscriptionDao cropSubsDao = (ICropSubscriptionDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("cropSubscriptionDao");
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		
		User user = userDao.findById(userId);
		List<CropSubscription> subs = cropSubsDao.findByUserId(userId);
		
		Set<Field> fields = user.getFields();
		HashMap<Long, HashMap<Long, String>> cropToFields = new HashMap<Long, HashMap<Long, String>>();
		for (Field field : fields) {
			long cropId = field.getCrop().getCropId();
			long fieldId = field.getFieldId();
			if (!cropToFields.containsKey(cropId)) {
				cropToFields.put(cropId, new HashMap<Long, String>());
			}
			cropToFields.get(cropId).put(fieldId, field.getFieldName());
		}
		
		List<CropSubscriptionWrapper> subWrappers = new ArrayList<CropSubscriptionWrapper>();
		for (CropSubscription sub : subs) {
			HashMap<Long, String> relatedFields = cropToFields.get(sub.getCropId());
			CropSubscriptionWrapper wrapper = new CropSubscriptionWrapper(sub, relatedFields);
			subWrappers.add(wrapper);
		}
		
		return subWrappers;
	}

}
