package com.dasinong.farmerClub.facade;

import java.util.Date;

import com.dasinong.farmerClub.model.Field;

public interface ISoilFacade {

	public abstract Object loadSoilHome(Field f);

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
	public abstract Object insertSoil(Long uId, Long fId, String type, String color, String fertility, double humidityv,
			Date date, double phValuev, String organic, double anv, double qnv, double pv, double qKv, double sKv,
			double fev, double mnv, double cuv, double znv, double bv, double mov, double cav, double sv, double siv,
			double mgv);

	Object loadSoilReportsByFid(Long fid);

	Object loadSoilReportsByUid(Long uid);

	Object updateSoil(Long reportId, String type, String color, String fertility, double humidityv, double phValuev,
			String organic, double anv, double qnv, double pv, double qKv, double sKv, double fev, double mnv,
			double cuv, double znv, double bv, double mov, double cav, double sv, double siv, double mgv);

	Object loadSoilReportsByRid(Long rid);

}