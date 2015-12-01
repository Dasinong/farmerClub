package com.dasinong.farmerClub.outputWrapper;

import java.io.Serializable;
import java.util.Date;

import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.SoilTestReport;

public class SoilTestReportWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	public Long soilTestReportId;
	public Long userId;

	public Long fieldId;
	public String type;
	public String color;
	public String fertility;
	public double humidity;
	public Date testDate;
	public double phValue;
	public String organic;

	public double an; // 全氮
	public double qn; // 碱解氮
	public double p;
	public double qK;
	public double sK;

	public double fe;
	public double mn;
	public double cu;
	public double zn;
	public double b;
	public double mo;

	public double ca;
	public double s;
	public double si;
	public double mg;

	public SoilTestReportWrapper(SoilTestReport stp) {
		this.soilTestReportId = stp.getSoilTestReportId();
		this.userId = stp.getUserId();
		this.fieldId = stp.getField().getFieldId();
		this.type = stp.getType();
		this.color = stp.getColor();
		this.fertility = stp.getFertility();
		this.humidity = stp.getHumidity();
		this.testDate = stp.getTestDate();
		this.phValue = stp.getPhValue();
		this.organic = stp.getOrganic();
		this.an = stp.getAn();
		this.qn = stp.getQn();
		this.p = stp.getP();
		this.qK = stp.getqK();
		this.sK = stp.getsK();
		this.fe = stp.getFe();
		this.mn = stp.getMn();
		this.cu = stp.getCu();
		this.zn = stp.getZn();
		this.b = stp.getB();
		this.mo = stp.getMo();
		this.ca = stp.getCa();
		this.s = stp.getS();
		this.si = stp.getSi();
		this.mg = stp.getMg();
	}

	public Long getSoilTestReportId() {
		return soilTestReportId;
	}

	public void setSoilTestReportId(Long soilTestReportId) {
		this.soilTestReportId = soilTestReportId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFertility() {
		return fertility;
	}

	public void setFertility(String fertility) {
		this.fertility = fertility;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public double getPhValue() {
		return phValue;
	}

	public void setPhValue(double phValue) {
		this.phValue = phValue;
	}

	public String getOrganic() {
		return organic;
	}

	public void setOrganic(String organic) {
		this.organic = organic;
	}

	public double getAn() {
		return an;
	}

	public void setAn(double an) {
		this.an = an;
	}

	public double getQn() {
		return qn;
	}

	public void setQn(double qn) {
		this.qn = qn;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getqK() {
		return qK;
	}

	public void setqK(double qK) {
		this.qK = qK;
	}

	public double getsK() {
		return sK;
	}

	public void setsK(double sK) {
		this.sK = sK;
	}

	public double getFe() {
		return fe;
	}

	public void setFe(double fe) {
		this.fe = fe;
	}

	public double getMn() {
		return mn;
	}

	public void setMn(double mn) {
		this.mn = mn;
	}

	public double getCu() {
		return cu;
	}

	public void setCu(double cu) {
		this.cu = cu;
	}

	public double getZn() {
		return zn;
	}

	public void setZn(double zn) {
		this.zn = zn;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getMo() {
		return mo;
	}

	public void setMo(double mo) {
		this.mo = mo;
	}

	public double getCa() {
		return ca;
	}

	public void setCa(double ca) {
		this.ca = ca;
	}

	public double getS() {
		return s;
	}

	public void setS(double s) {
		this.s = s;
	}

	public double getSi() {
		return si;
	}

	public void setSi(double si) {
		this.si = si;
	}

	public double getMg() {
		return mg;
	}

	public void setMg(double mg) {
		this.mg = mg;
	}

}
