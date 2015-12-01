package com.dasinong.farmerClub.facade;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.dao.IStepDao;
import com.dasinong.farmerClub.dao.IStepRegionDao;
import com.dasinong.farmerClub.dao.ITaskRegionDao;
import com.dasinong.farmerClub.dao.ITaskSpecDao;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.Step;
import com.dasinong.farmerClub.model.StepRegion;
import com.dasinong.farmerClub.model.TaskRegion;
import com.dasinong.farmerClub.model.TaskSpec;
import com.dasinong.farmerClub.outputWrapper.StepWrapper;
import com.dasinong.farmerClub.outputWrapper.TaskSpecWrapper;

@Transactional
public class TaskSpecFacade implements ITaskSpecFacade {

	ITaskSpecDao taskSpecDao;
	IFieldDao fieldDao;
	IStepDao stepDao;
	IStepRegionDao stepRegionDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ITaskSpecFacade#getTaskSpec(java.lang.
	 * Long)
	 */
	@Override
	public Object getTaskSpec(Long taskSpecId) {

		// TODO: issue with this path. a.Too slow. b.Empty substage.
		taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskSpecDao");
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			TaskSpec taskspec = taskSpecDao.findById(taskSpecId);
			TaskSpecWrapper tsw = new TaskSpecWrapper(taskspec);
			result.put("respCode", 200);
			result.put("message", "获得任务描述");
			result.put("data", tsw);
			return result;
		} catch (Exception e) {
			result.put("respCode", 500);
			result.put("message", e.getMessage());
			return result;
		}
	}

	@Override
	public List<StepWrapper> getSteps(Long taskSpecId, Long fieldId) {
		stepDao = (IStepDao) ContextLoader.getCurrentWebApplicationContext().getBean("stepDao");
		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		stepRegionDao = (IStepRegionDao) ContextLoader.getCurrentWebApplicationContext().getBean("stepRegionDao");
		HashMap<String, Object> result = new HashMap<String, Object>();

		List<Step> steps = stepDao.findByTaskSpecId(taskSpecId);
		List<StepWrapper> vaildS = new ArrayList<StepWrapper>();
		Set<String> pictures = new HashSet<String>();
		if (fieldId == 0) {
			for (Step s : steps) {
				StepWrapper sw = new StepWrapper(s);
				String[] pictureNames = s.getPicture().split(",");
				if (pictureNames != null && pictureNames.length >= 1) {
					if (pictures.contains(pictureNames[0])) {
						sw.setPicture("");
					} else {
						sw.setPicture(pictureNames[0]);
						pictures.add(pictureNames[0]);
					}
				}
				vaildS.add(sw);
			}
		} else {
			Field field = fieldDao.findById(fieldId);
			if (steps == null) {
				return vaildS;
			}

			List<StepRegion> srl = stepRegionDao.findByStepRegion(field.getLocation().getRegion());
			Set<Long> sIds = new HashSet<Long>();
			for (StepRegion sr : srl) {
				sIds.add(sr.getStepId());
			}

			for (Step s : steps) {
				// if (s.getFitRegion().contains(region)){
				if (sIds.contains(s.getStepId())) {
					StepWrapper sw = new StepWrapper(s);
					String[] pictureNames = s.getPicture().split(",");
					if (pictureNames != null && pictureNames.length >= 1) {
						if (pictures.contains(pictureNames[0])) {
							sw.setPicture("");
						} else {
							sw.setPicture(pictureNames[0]);
							pictures.add(pictureNames[0]);
						}
					}
					vaildS.add(sw);
				}
			}
		}
		return vaildS;
	}

}
