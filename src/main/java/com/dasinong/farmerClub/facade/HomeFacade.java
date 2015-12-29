package com.dasinong.farmerClub.facade;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.dao.ITaskSpecDao;
import com.dasinong.farmerClub.datapool.AllMonitorLocation;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.MonitorLocation;
import com.dasinong.farmerClub.model.SoilTestReport;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.FieldWrapper;
import com.dasinong.farmerClub.outputWrapper.SoilTestReportWrapper;
import com.dasinong.farmerClub.util.LunarHelper;
import com.dasinong.farmerClub.weather.SoilLiquid;

@Transactional
public class HomeFacade implements IHomeFacade {

	// @Autowired
	private IFieldDao fieldDao;

	// @Autowired
	private ITaskSpecDao taskSpecDao;

	private Logger logger = LoggerFactory.getLogger(HomeFacade.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dasinong.farmerClub.facade.IHomeFacade#LoadHome(com.dasinong.
	 * farmerClub.model.User, java.lang.String)
	 */
	@Override
	public Object LoadHome(User user, Long fieldId) {
		// taskType means how would you like to load task related content.
		// 1 for all
		// 2 for current stage
		// 3 for not required
		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		taskSpecDao = (ITaskSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("taskSpecDao");
		HashMap<String, Object> result = new HashMap<String, Object>();

		// Lunar Date part
		result.put("date", LunarHelper.getTodayLunar());

		// Put field List
		HashMap<String, Long> fieldList = new HashMap<String, Long>();
		for (Field f : user.getFields()) {
			if (fieldList.keySet().contains(f.getFieldName())) {
				this.logger.warn("Duplicated field for same user");
			} else {
				fieldList.put(f.getFieldName(), f.getFieldId());
			}
		}
		result.put("fieldList", fieldList);

		// Put field Detail
		Field f = null;
		f = fieldDao.findById(fieldId);
		if (f == null) {
			result.put("respCode", 120);
			result.put("message", "fieldId不存在");
			return result;
		}

		FieldWrapper fw = new FieldWrapper(f);
		result.put("currentField", fw);

		long latest = -1;
		SoilTestReport latestR = null;
		for (SoilTestReport str : f.getSoilTestReports()) {
			if (str.soilTestReportId > latest) {
				latestR = str;
				latest = str.soilTestReportId;
			}
		}
		if (latestR != null) {
			SoilTestReportWrapper strw = new SoilTestReportWrapper(latestR);
			result.put("latestReport", strw);
		}

		// Get Hum
		MonitorLocation ml = null;
		try {
			ml = AllMonitorLocation.getInstance().getMonitorLocation(f.getMonitorLocationId());
			if (ml != null) {
				double soilHum = SoilLiquid.getSoilLi().getSoil(ml.getLatitude(), ml.getLongitude());
				result.put("soilHum", soilHum);
			}
		} catch (Exception e) {
			this.logger.warn("Load monitor location field or soilLiquid failed");
		}

		result.put("respCode", 200);
		result.put("message", "读取田地成功");
		return result;
	}

	public Object LoadHome(double lat, double lon) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		// Lunar Date part
		result.put("date", LunarHelper.getTodayLunar());

		double soilHum = 0;
		try {
			soilHum = SoilLiquid.getSoilLi().getSoil(lat, lon);
		} catch (Exception e) {
			this.logger.warn("Load soilHum failed");
		}
		result.put("soilHum", soilHum);
		return result;
	}
}
