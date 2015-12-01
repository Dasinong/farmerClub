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

import com.dasinong.farmerClub.BaiKeController;
import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.ISubStageDao;
import com.dasinong.farmerClub.dao.ITaskDao;
import com.dasinong.farmerClub.dao.ITaskRegionDao;
import com.dasinong.farmerClub.dao.ITaskSpecDao;
import com.dasinong.farmerClub.dao.IVarietyDao;
import com.dasinong.farmerClub.dao.IWeatherSubscriptionDao;
import com.dasinong.farmerClub.datapool.AllMonitorLocation;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.NatDis;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.Task;
import com.dasinong.farmerClub.model.TaskRegion;
import com.dasinong.farmerClub.model.TaskSpec;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.Variety;
import com.dasinong.farmerClub.model.WeatherSubscription;
import com.dasinong.farmerClub.model.WeatherSubscriptionType;
import com.dasinong.farmerClub.outputWrapper.FieldWrapper;
import com.dasinong.farmerClub.outputWrapper.SubStageWrapper;

import java.util.Collections;

@Transactional
public class FieldFacade implements IFieldFacade {

	IFieldDao fd;
	ITaskSpecDao taskSpecDao;
	ITaskDao taskDao;
	ILocationDao ldDao;
	IVarietyDao varietyDao;
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
	public FieldWrapper createField(User user, String fieldName, Date startDate, boolean isActive,
			boolean seedingortransplant, double area, long locationId, long varietyId, Long currentStageId, Long yield)
					throws Exception {
		fd = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		ldDao = (ILocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
		varietyDao = (IVarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		subStageDao = (ISubStageDao) ContextLoader.getCurrentWebApplicationContext().getBean("subStageDao");
		taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskSpecDao");
		taskDao = (ITaskDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskDao");
		petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("petDisSpecDao");
		taskRegionDao = (ITaskRegionDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskRegionDao");
		weatherSubscriptionDao = (IWeatherSubscriptionDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("weatherSubscriptionDao");

		Map<String, Object> result = new HashMap<String, Object>();

		Location location = ldDao.findById(locationId);
		Variety variety = varietyDao.findById(varietyId);
		if (location == null || variety == null) {
			Exception e = new Exception("locationId或varietyId无效");
			throw e;
		}
		if (currentStageId == 0L) {
			if (variety.getSubStages() != null && variety.getSubStages().size() != 0) {
				currentStageId = variety.getSubStages().iterator().next().getSubStageId();
			}
		}

		if (fieldName == null || fieldName.equals("")) {
			fieldName = location.getCommunity() + variety.getVarietyName();
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
		field.setIsActive(isActive);
		field.setSeedortrans(seedingortransplant);
		field.setArea(area);
		field.setStartDate(startDate);
		field.setLocation(location);
		field.setVariety(variety);
		field.setCurrentStageID(currentStageId);
		field.setUser(user);
		field.setYield(yield);

		Date date = new Date();
		field.setLastForceSet(date);
		field.setDayOffset(0);
		field.setIsJustSet(true);
		computeStage(field);

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

		// 初始化所有常见任务
		if (variety.getSubStages() != null) {
			List<TaskRegion> trl = taskRegionDao.findByTaskRegion(field.getLocation().getRegion());
			Set<Long> tasks = new HashSet<Long>();
			for (TaskRegion tr : trl) {
				tasks.add(tr.getTaskSpecId());
			}
			for (SubStage ss : variety.getSubStages()) {
				if (ss.getTaskSpecs() != null) {
					for (TaskSpec ts : ss.getTaskSpecs()) {
						// if
						// (ts.getFitRegion().contains(field.getLocation().getRegion())){
						if (tasks.contains(ts.getTaskSpecId())) {
							Task t = new Task(ts, false);
							t.setFieldId(field.getFieldId());
							taskDao.save(t);
							if (field.getTasks() == null)
								field.setTasks(new HashMap<Long, Task>());
							field.getTasks().put(t.getTaskId(), t);
						}
					}
				}
			}
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

		FieldWrapper fw = new FieldWrapper(field, taskSpecDao, 1);
		return fw;
	}

	@Override
	public Object addWeatherAlert(NatDis natdis) {

		Map<String, Object> result = new HashMap<String, Object>();
		return result;

	}

	@Override
	public FieldWrapper changeField(Long fieldId, Long currentStageId) {
		fd = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskSpecDao");
		Field f = fd.findById(fieldId);
		f.setCurrentStageID(currentStageId);
		f.setLastForceSet(new Date());
		f.setIsJustSet(true);
		computeStage(f);
		fd.update(f);
		FieldWrapper fw = new FieldWrapper(f, taskSpecDao, 2);
		return fw;
	}

	private long computeStage(Field field) {
		long currentStage = field.getCurrentStageID();
		if (field.getVariety().getFullCycleDuration() == 0)
			return currentStage;
		boolean foundStage = false;
		long computedStage = 0;
		Date date = new Date();
		double pastDay = (date.getTime() - field.getStartDate().getTime()) / 24 / 60 / 60 / 1000;
		double act_Exp_Cycle = (pastDay - field.getDayOffset()) / field.getVariety().getFullCycleDuration() * 100;
		double sel_Cycle_min = 0;
		double cycle_count = 0;
		double cur_Cycle = 0;
		// Compute currentStage
		if (field.getVariety().getCrop().getCropId() == 223L) {
			// subStage must iterator follow stageId
			List<SubStage> subStages = new ArrayList<SubStage>();
			for (SubStage subStage : field.getVariety().getSubStages()) {
				subStages.add(subStage);
			}

			Collections.sort(subStages);
			//
			for (SubStage subStage : subStages) {
				if (subStage.getSubStageId() < currentStage) {
					if (field.getVariety().getFullCycleDuration() < 125) {
						sel_Cycle_min += subStage.getDurationLow();
					} else if (field.getVariety().getFullCycleDuration() < 150) {
						sel_Cycle_min += subStage.getDurationMid();
					} else {
						sel_Cycle_min += subStage.getDurationHigh();
					}
				}
				if (subStage.getSubStageId() == currentStage) {
					if (field.getVariety().getFullCycleDuration() < 125)
						cur_Cycle = subStage.getDurationLow();
					else if (field.getVariety().getFullCycleDuration() < 150)
						cur_Cycle = subStage.getDurationMid();
					else
						cur_Cycle = subStage.getDurationHigh();
				}
				if (field.getVariety().getFullCycleDuration() < 125)
					cycle_count += subStage.getDurationLow();
				else if (field.getVariety().getFullCycleDuration() < 150)
					cycle_count += subStage.getDurationMid();
				else
					cycle_count += subStage.getDurationHigh();
				if (cycle_count > act_Exp_Cycle && !foundStage) {
					computedStage = subStage.getSubStageId();
					foundStage = true;
				}
			}
		}

		// 用户当天是否强制设置过阶段
		if (field.getLastForceSet().getDate() == date.getDate()) {
			// 第一次设定会重新计算offset
			if (field.getIsJustSet()) {
				if (computedStage < currentStage) {
					double newOffset = pastDay - field.getVariety().getFullCycleDuration() * (sel_Cycle_min) / 100;
					field.setDayOffset(newOffset * 0.8 + field.getDayOffset() * 0.2);
				} else if (computedStage > currentStage) {
					double newOffset = pastDay
							- field.getVariety().getFullCycleDuration() * (sel_Cycle_min + cur_Cycle) / 100;
					field.setDayOffset(newOffset * 0.8 + field.getDayOffset() * 0.2);
				}
				field.setIsJustSet(false);
			}
			return currentStage;
		} else {
			if (foundStage) {
				if (computedStage < field.getCurrentStageID()) {
					logger.warn("Field" + field.getFieldId() + "'s computedStage is less than currentStage");
				} else {
					field.setCurrentStageID(computedStage);
				}
			}
			return field.getCurrentStageID();
		}
	}

	@Override
	public List<SubStageWrapper> getStages(long varietyId) {
		varietyDao = (IVarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		Variety v = varietyDao.findById(varietyId);

		List<SubStage> subStages = new ArrayList<SubStage>();
		for (SubStage subStage : v.getSubStages()) {
			subStages.add(subStage);
		}

		Collections.sort(subStages);
		List<SubStageWrapper> ssw = new ArrayList<SubStageWrapper>();
		for (SubStage subStage : subStages) {
			ssw.add(new SubStageWrapper(subStage));
		}
		return ssw;
	}
}
