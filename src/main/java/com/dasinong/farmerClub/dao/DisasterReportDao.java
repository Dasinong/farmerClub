/**
 * 
 */
package com.dasinong.farmerClub.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.DisasterReport;

public class DisasterReportDao extends EntityHibernateDao<DisasterReport>implements IDisasterReportDao {

	@Override
	public DisasterReport findByDisasterName(String disasterName) {
		// TODO Auto-generated method stub
		return null;
	}

}
