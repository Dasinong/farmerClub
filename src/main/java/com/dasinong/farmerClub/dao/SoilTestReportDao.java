package com.dasinong.farmerClub.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.dasinong.farmerClub.model.SoilTestReport;

public class SoilTestReportDao extends EntityHibernateDao<SoilTestReport>implements ISoilTestReportDao {

	@Override
	public List<SoilTestReport> findByFieldId(Long fid) {
		@SuppressWarnings("unchecked")
		List<SoilTestReport> list = this.getSessionFactory().getCurrentSession()
				.createQuery("from SoilTestReport where fieldId=:fieldId").setLong("fieldId", fid).list();
		return list;
	}

	@Override
	public List<SoilTestReport> findByUserId(Long uid) {
		@SuppressWarnings("unchecked")
		List<SoilTestReport> list = this.getSessionFactory().getCurrentSession()
				.createQuery("from SoilTestReport where userId=:userId").setLong("userId", uid).list();
		return list;
	}

}
