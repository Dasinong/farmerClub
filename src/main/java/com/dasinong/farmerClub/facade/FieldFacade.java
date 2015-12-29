package com.dasinong.farmerClub.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.ISubStageDao;
import com.dasinong.farmerClub.dao.ITaskRegionDao;
import com.dasinong.farmerClub.dao.ITaskSpecDao;
import com.dasinong.farmerClub.dao.IWeatherSubscriptionDao;
import com.dasinong.farmerClub.datapool.AllMonitorLocation;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.WeatherSubscription;
import com.dasinong.farmerClub.model.WeatherSubscriptionType;
import com.dasinong.farmerClub.outputWrapper.FieldWrapper;
import com.dasinong.farmerClub.outputWrapper.SubStageWrapper;

import java.util.Collections;

@Transactional
public class FieldFacade implements IFieldFacade {

	IFieldDao fd;
	ITaskSpecDao taskSpecDao;
	ILocationDao ldDao;
	ICropDao cropDao;
	ISubStageDao subStageDao;
	IPetDisSpecDao petDisSpecDao;
	ITaskRegionDao taskRegionDao;
	IWeatherSubscriptionDao weatherSubscriptionDao;
	Logger logger = LoggerFactory.getLogger(FieldFacade.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.IFieldFacade#createField(com.dasinong.
	 * farmerClub.model.User,
	 * com.dasinong.farmerClub.inputParser.FieldParser)
	 */
	@Override
	public FieldWrapper createField(User user, String fieldName, double area, long locationId, long cropId, Long currentStageId)
					throws Exception {
		fd = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		ldDao = (ILocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
		cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		subStageDao = (ISubStageDao) ContextLoader.getCurrentWebApplicationContext().getBean("subStageDao");
		taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskSpecDao");
		petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("petDisSpecDao");
		taskRegionDao = (ITaskRegionDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskRegionDao");
		weatherSubscriptionDao = (IWeatherSubscriptionDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("weatherSubscriptionDao");

		Map<String, Object> result = new HashMap<String, Object>();

		Location location = ldDao.findById(locationId);
		Crop crop = cropDao.findById(cropId);
		if (location == null || crop == null) {
			throw new Exception("locationId或cropId无效");
		}
		if (currentStageId == 0L) {
			Set<SubStage> subStages = crop.getSubStages();
			if (subStages != null && subStages.size() != 0) {
				currentStageId = subStages.iterator().next().getSubStageId();
			}
		}

		if (fieldName == null || fieldName.equals("")) {
			fieldName = location.getCommunity() + crop.getCropName();
		}
		Field field = new Field();
		// Following part is to remove duplicated field name for same user. Be
		// careful of performance impact.
		List<String> fieldNames = new ArrayList<String>();
		for (Field f : user.getFields()) {
			fieldNames.add(f.getFieldName());
		}

		int fc = 2;
		String newName = fieldName;
		while (fieldNames.contains(newName)) {
			newName = fieldName + fc;
			fc++;
		}
		field.setFieldName(newName);
		field.setArea(area);
		field.setLocation(location);
		field.setCrop(crop);
		field.setCurrentStageID(currentStageId);
		field.setUser(user);

		Date date = new Date();

		double lat = location.getLatitude();
		double lon = location.getLongtitude();
		long monitorLocationId = AllMonitorLocation.getInstance().getNearest(lat, lon);
		field.setMonitorLocationId(monitorLocationId);
		fd.save(field);

		if (user.getFields() != null) {
			user.getFields().add(field);
		} else {
			user.setFields(new HashSet<Field>());
			user.getFields().add(field);
		}

		// Create WeatherSubscription Object
		// If creation fails, do NOT block the request
		WeatherSubscription ws = new WeatherSubscription();
		ws.setUserId(user.getUserId());
		ws.setLocationId(location.getLocationId());
		ws.setLocationName(location.toString());
		ws.setMonitorLocationId(monitorLocationId);
		ws.setType(WeatherSubscriptionType.FIELD);
		weatherSubscriptionDao.save(ws);

		FieldWrapper fw = new FieldWrapper(field);
		return fw;
	}

	@Override
	public FieldWrapper changeField(Long fieldId, Long subStageId) {
		fd = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		Field f = fd.findById(fieldId);
		f.setCurrentStageID(subStageId);
		fd.update(f);
		FieldWrapper fw = new FieldWrapper(f);
		return fw;
	}

	@Override
	public List<SubStageWrapper> getStages(long cropId) {
		cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		Crop c = cropDao.findById(cropId);

		List<SubStage> subStages = new ArrayList<SubStage>();
		for (SubStage subStage : c.getSubStages()) {
			subStages.add(subStage);
		}

		Collections.sort(subStages);
		List<SubStageWrapper> ssw = new ArrayList<SubStageWrapper>();
		for (SubStage subStage : subStages) {
			ssw.add(new SubStageWrapper(subStage));
		}
		return ssw;
	}

	@Override
	public FieldWrapper findById(long fieldId) {
		IFieldDao fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		Field field = fieldDao.findById(fieldId);
		return new FieldWrapper(field);
	}
}
