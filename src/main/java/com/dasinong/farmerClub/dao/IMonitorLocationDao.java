package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.MonitorLocation;

public interface IMonitorLocationDao extends IEntityDao<MonitorLocation> {

	public abstract MonitorLocation findByCode(long code);

}
