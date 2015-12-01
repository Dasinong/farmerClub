package com.dasinong.farmerClub.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.ISubStageDao;
import com.dasinong.farmerClub.dao.IVarietyDao;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.PetSolu;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.model.Variety;
import com.dasinong.farmerClub.outputWrapper.PetDisSpecWrapper;
import com.dasinong.farmerClub.outputWrapper.PetSoluWrapper;

@Transactional
public class PetDisSpecFacade implements IPetDisSpecFacade {

	IPetDisSpecDao petDisSpecDao;
	ISubStageDao subStageDao;
	IVarietyDao varietyDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.IPetDisSpecFacade#getPetDisBySubStage(
	 * java.lang.Long)
	 */
	@Override
	public Object getPetDisBySubStage(Long subStageId, Long varietyId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("petDisSpecDao");
		subStageDao = (ISubStageDao) ContextLoader.getCurrentWebApplicationContext().getBean("subStageDao");
		varietyDao = (IVarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		SubStage sb = subStageDao.findById(subStageId);

		List<PetDisSpecWrapper> pws = new ArrayList<PetDisSpecWrapper>();
		List<PetDisSpec> pdlist = new ArrayList<PetDisSpec>();
		if (sb.getPetDisSpecs() == null || sb.getPetDisSpecs().size() == 0) {
			Variety v = varietyDao.findById(varietyId);
			if (v != null && v.getCrop() != null && v.getCrop().getPetDisSpecs() != null
					&& v.getCrop().getPetDisSpecs().size() > 0) {
				for (PetDisSpec pds : v.getCrop().getPetDisSpecs()) {
					pdlist.add(pds);
				}
			} else {
				result.put("respCode", 200);
				result.put("message", "当前阶段无常见病虫草害");
				result.put("data", pws);
				return result;
			}
		} else {
			for (PetDisSpec pds : sb.getPetDisSpecs()) {
				pdlist.add(pds);
			}
		}
		Collections.sort(pdlist);
		int count = 0;
		for (PetDisSpec pds : pdlist) {
			PetDisSpecWrapper pdsw = new PetDisSpecWrapper(pds);
			pws.add(pdsw);
			count++;
			if (count >= 4)
				break;
		}

		result.put("respCode", 200);
		result.put("message", "获取常见病虫草害成功");
		result.put("data", pws);
		return result;
	}

	@Override
	public Object getPetDisDetail(Long petDisSpecId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("petDisSpecDao");
		PetDisSpec pds = petDisSpecDao.findById(petDisSpecId);
		if (pds == null) {
			result.put("respCode", 404);
			result.put("message", "该病虫草害不存在");
			return result;
		}
		HashMap<String, Object> data = new HashMap<String, Object>();
		PetDisSpecWrapper pdsw = new PetDisSpecWrapper(pds);
		data.put("petDisSpec", pdsw);
		if (pds.getPetSolus() != null && pds.getPetSolus().size() != 0) {
			List<PetSoluWrapper> psws = new ArrayList<PetSoluWrapper>();
			for (PetSolu ps : pds.getPetSolus()) {
				PetSoluWrapper psw = new PetSoluWrapper(ps);
				psws.add(psw);
			}
			data.put("petSolutions", psws);
		}
		result.put("respCode", 200);
		result.put("message", "获取病虫草害详情成功");
		result.put("data", data);
		return result;
	}

}
