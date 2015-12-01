package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.DisasterReport;

public interface IDisasterReportDao extends IEntityDao<DisasterReport> {

	public abstract DisasterReport findByDisasterName(String disasterName);

}