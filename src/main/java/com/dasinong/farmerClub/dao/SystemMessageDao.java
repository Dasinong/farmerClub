package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dasinong.farmerClub.model.SystemMessage;

public class SystemMessageDao extends EntityHibernateDao<SystemMessage> implements ISystemMessageDao {

	@Override
	public List<SystemMessage> findAllValid() {
		Date cur = (new Date());
		List<SystemMessage> sms = this.getHibernateTemplate().findByNamedParam(
				"from SystemMessage where endTime >= :cur and startTime <= :cur", "cur", cur);
		if (sms == null || sms.size() == 0) {
			return new ArrayList<SystemMessage>();
		}
		
		return sms;
	}

	
}
