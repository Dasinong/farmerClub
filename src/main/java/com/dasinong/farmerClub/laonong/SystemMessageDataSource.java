package com.dasinong.farmerClub.laonong;

import java.util.ArrayList;
import java.util.List;

import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.dao.ISystemMessageDao;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.SystemMessage;
import com.dasinong.farmerClub.model.User;

public class SystemMessageDataSource implements ILaoNongDataSource {

	private ISystemMessageDao messageDao;
	private ILocationDao locDao;
	
	public SystemMessageDataSource(ISystemMessageDao messageDao, ILocationDao locDao) {
		this.messageDao = messageDao;
		this.locDao = locDao;
	}
	
	@Override
	public List<LaoNong> genLaoNongs(User user, Long areaId) {
		List<SystemMessage> messages = this.messageDao.findAllValid();
		Location loc = this.locDao.findById(areaId);
		ArrayList<LaoNong> laonongs = new ArrayList<LaoNong>();
		
		for (SystemMessage message : messages) {
			if (!this.matchLocation(message, loc)) {
				continue;
			}
			
			if (!this.matchChannel(message, user)) {
				continue;
			}
			
			if (!this.matchInstitution(message, user)) {
				continue;
			}
			
			// Ok, user is able to see this message!
			LaoNong ln = new LaoNong(message.getId(), LaoNongType.SYSTEM_MESSAGE, message.getPicUrl(), "系统广告", message.getContent(),
					message.getLandingUrl());
			laonongs.add(ln);
		}
		
		return laonongs;
	}

	private boolean matchLocation(SystemMessage message, Location loc) {
		String province = message.getProvince();
		String city = message.getCity();
		String county = message.getCounty();
		String community = message.getCommunity();
		String district = message.getDistrict();
		
		if (province == null || "".equals(province)) {
			return true;
		}
		
		if (!province.equals(loc.getProvince())) {
			return false;
		}
		
		if (city == null || "".equals(city)) {
			return true;
		}
		
		if (!city.equals(loc.getCity())) {
			return false;
		}
		
		if (county == null || "".equals(county)) {
			return true;
		}
		
		if (!county.equals(loc.getCountry())) {
			return false;
		}
		
		if (district == null || "".equals(district)) {
			return true;
		}
		
		if (!district.equals(loc.getDistrict())) {
			return false;
		}
		
		if (community == null || "".equals(community)) {
			return true;
		}
		
		if (!community.equals(loc.getCommunity())) {
			return false;
		}
		
		return true;
	}
	
	private boolean matchChannel(SystemMessage message, User user) {
		String channel = message.getChannel();
		
		if (channel == null || "".equals(channel)) {
			return true;
		}
		
		return channel.equals(user.getChannel());
	}
	
	private boolean matchInstitution(SystemMessage message, User user) {
		Long instId = message.getInstitutionId();
		
		if (instId == null || instId <= 0) {
			return true;
		}
		
		return message.getInstitutionId() == user.getInstitutionId();
	}
	
}
