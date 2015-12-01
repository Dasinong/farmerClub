package com.dasinong.farmerClub.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.dao.ISoilTestReportDao;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.SoilTestReport;
import com.dasinong.farmerClub.outputWrapper.SoilTestReportWrapper;

@Transactional
public class SoilFacade implements ISoilFacade {

	// @Autowired
	private IFieldDao fieldDao;

	// @Autowired
	private ISoilTestReportDao soilTestReportDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ISoilFacade#loadSoilHome(com.dasinong.
	 * farmerClub.model.Field)
	 */
	@Override
	public Object loadSoilHome(Field f) {

		HashMap<String, Object> result = new HashMap<String, Object>();

		return result;

	}

	/*
	 * Long uId = Long.parseLong(userId); Long fId = Long.parseLong(fieldId);
	 * type = (type==null) ? "":type; color = (color==null) ? "":color;
	 * fertility = (fertility==null) ? "":fertility; double humidityv =
	 * Double.parseDouble(humidity); Date date = new Date(testDate); double
	 * phValuev = Double.parseDouble(phValue); organic = (organic==null) ?
	 * "":organic; double anv = Double.parseDouble(an); double qnv =
	 * Double.parseDouble(qn); double pv = Double.parseDouble(p); double qKv =
	 * Double.parseDouble(qK); double sKv = Double.parseDouble(sK); double fev =
	 * Double.parseDouble(fe); double mnv = Double.parseDouble(mn); double cuv =
	 * Double.parseDouble(cu); double znv = Double.parseDouble(zn); double bv =
	 * Double.parseDouble(b); double mov = Double.parseDouble(mo); double cav =
	 * Double.parseDouble(ca); double sv = Double.parseDouble(s); double siv =
	 * Double.parseDouble(si); double mgv = Double.parseDouble(mg);
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ISoilFacade#insertSoilHome(java.lang.
	 * Long, java.lang.Long, java.lang.String, java.lang.String,
	 * java.lang.String, double, java.util.Date, double, java.lang.String,
	 * double, double, double, double, double, double, double, double, double,
	 * double, double, double, double, double, double)
	 */
	@Override
	public SoilTestReportWrapper insertSoil(Long uId, Long fId, String type, String color, String fertility,
			double humidityv, Date date, double phValuev, String organic, double anv, double qnv, double pv, double qKv,
			double sKv, double fev, double mnv, double cuv, double znv, double bv, double mov, double cav, double sv,
			double siv, double mgv) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		soilTestReportDao = (ISoilTestReportDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("soilTestReportDao");
		Field field = fieldDao.findById(fId);

		SoilTestReport str = new SoilTestReport(uId, field, type, color, fertility, humidityv, date, phValuev, organic,
				anv, qnv, pv, qKv, sKv, fev, mnv, cuv, znv, bv, mov, cav, sv, siv, mgv);
		soilTestReportDao.save(str);
		field.getSoilTestReports().add(str);
		SoilTestReportWrapper strw = new SoilTestReportWrapper(str);
		return strw;
	}

	@Override
	public Object updateSoil(Long reportId, String type, String color, String fertility, double humidityv,
			double phValuev, String organic, double anv, double qnv, double pv, double qKv, double sKv, double fev,
			double mnv, double cuv, double znv, double bv, double mov, double cav, double sv, double siv, double mgv) {
		soilTestReportDao = (ISoilTestReportDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("soilTestReportDao");
		SoilTestReport str = soilTestReportDao.findById(reportId);
		str.setType(type);
		str.setColor(color);
		str.setFertility(fertility);
		str.setHumidity(humidityv);
		str.setPhValue(phValuev);
		str.setOrganic(organic);
		str.setAn(anv);
		str.setQn(qnv);
		str.setP(pv);
		str.setqK(qKv);
		str.setsK(sKv);
		str.setFe(fev);
		str.setMn(mnv);
		str.setCu(cuv);
		str.setZn(znv);
		str.setB(bv);
		str.setMo(mov);
		str.setCa(cav);
		str.setS(sv);
		str.setSi(siv);
		str.setMg(mgv);
		soilTestReportDao.update(str);

		SoilTestReportWrapper strw = new SoilTestReportWrapper(str);
		return strw;
	}

	@Override
	public List<SoilTestReportWrapper> loadSoilReportsByFid(Long fid) {
		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		soilTestReportDao = (ISoilTestReportDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("soilTestReportDao");
		List<SoilTestReport> soilReports = soilTestReportDao.findByFieldId(fid);

		List<SoilTestReportWrapper> soilReportsw = new ArrayList<SoilTestReportWrapper>();
		for (SoilTestReport str : soilReports) {
			soilReportsw.add(new SoilTestReportWrapper(str));
		}
		return soilReportsw;
	}

	@Override
	public SoilTestReportWrapper loadSoilReportsByRid(Long rid) {

		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		soilTestReportDao = (ISoilTestReportDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("soilTestReportDao");
		SoilTestReport soilReport = soilTestReportDao.findById(rid);

		SoilTestReportWrapper soilReportw = new SoilTestReportWrapper(soilReport);
		return soilReportw;
	}

	@Override
	public Object loadSoilReportsByUid(Long uid) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		fieldDao = (IFieldDao) ContextLoader.getCurrentWebApplicationContext().getBean("fieldDao");
		soilTestReportDao = (ISoilTestReportDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("soilTestReportDao");
		List<SoilTestReport> soilReports = soilTestReportDao.findByUserId(uid);

		List<SoilTestReportWrapper> soilReportsw = new ArrayList<SoilTestReportWrapper>();
		for (SoilTestReport str : soilReports) {
			soilReportsw.add(new SoilTestReportWrapper(str));
		}

		result.put("respCode", 200);
		result.put("message", "读取成功");
		result.put("data", soilReportsw);
		return result;
	}

}
