package com.dasinong.farmerClub.datapool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IMonitorLocationDao;
import com.dasinong.farmerClub.model.MonitorLocation;

//Used for easy access, read only and does not support dynamic DB change.
public class AllMonitorLocation {
	private IMonitorLocationDao monitorLocationDao;
	private Logger logger = LoggerFactory.getLogger(AllMonitorLocation.class);

	private static Object SynRoot = new Object();

	@SuppressWarnings("unchecked")
	private AllMonitorLocation() {
		_allLocation = new HashMap<Long, MonitorLocation>();
		monitorLocationDao = (IMonitorLocationDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("monitorLocationDao");

		List<MonitorLocation> mls = monitorLocationDao.findAll();
		for (MonitorLocation ml : mls) {
			_allLocation.put(ml.getCode(), ml);
		}
	}

	private AllMonitorLocation(IMonitorLocationDao monitorLocationDao) {
		_allLocation = new HashMap<Long, MonitorLocation>();
		if (monitorLocationDao == null) {
			monitorLocationDao = (IMonitorLocationDao) ContextLoader.getCurrentWebApplicationContext()
					.getBean("monitorLocationDao");
		}
		List<MonitorLocation> mls = monitorLocationDao.findAll();
		for (MonitorLocation ml : mls) {
			_allLocation.put(ml.getCode(), ml);
		}
	}

	public static AllMonitorLocation getInstance() {
		synchronized (SynRoot) {
			if (null == allLocation) {
				{
					allLocation = new AllMonitorLocation();
				}
			}
		}
		return allLocation;
	}

	public static AllMonitorLocation getInstance(IMonitorLocationDao monitorLocationDao) {
		synchronized (SynRoot) {
			if (null == allLocation) {
				{
					allLocation = new AllMonitorLocation(monitorLocationDao);
				}
			}
		}
		return allLocation;
	}

	private static AllMonitorLocation allLocation;

	public HashMap<Long, MonitorLocation> _allLocation;

	public Long getNearest(double lat, double lon) {
		MonitorLocation target = null;
		double minDis = 100;
		Iterator iter = _allLocation.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, MonitorLocation> entry = (Map.Entry<Integer, MonitorLocation>) iter.next();
			MonitorLocation ml = entry.getValue();
			if ((ml.getLatitude() - lat) * (ml.getLatitude() - lat)
					+ (ml.getLongitude() - lon) * (ml.getLongitude() - lon) < minDis) {
				minDis = (ml.getLatitude() - lat) * (ml.getLatitude() - lat)
						+ (ml.getLongitude() - lon) * (ml.getLongitude() - lon);
				target = ml;
			}
		}
		
		if (target == null) {
			logger.error("incorrect location <" + lat + "," + lon + ">");
		}
		
		return target.getCode();
	}

	public MonitorLocation getMonitorLocation(long areaid) {
		return _allLocation.get(areaid);
	}

	public static void main(String[] args) {

		Iterator iter = AllMonitorLocation.getInstance()._allLocation.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			System.out.print(entry.getKey() + ": ");
			System.out.println(entry.getValue());
		}
		System.out.print(AllMonitorLocation.getInstance().getNearest(39, 116));
	}
}
