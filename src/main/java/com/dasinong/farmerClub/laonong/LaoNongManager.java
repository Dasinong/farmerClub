package com.dasinong.farmerClub.laonong;

import java.util.ArrayList;
import java.util.List;

import com.dasinong.farmerClub.model.User;

public class LaoNongManager {
	
	private List<ILaoNongDataSource> sources = new ArrayList<ILaoNongDataSource>();
	
	public void addSource(ILaoNongDataSource source) {
		sources.add(source);
	}

	public List<LaoNong> genLaoNongs(User user, Long areaId) {
		ArrayList<LaoNong> laonongs = new ArrayList<LaoNong>();
		
		for (ILaoNongDataSource dataSource : this.sources) {
			List<LaoNong> lns = dataSource.genLaoNongs(user, areaId);
			if (lns != null && lns.size() > 0) {
				laonongs.addAll(lns);
			}
		}
		
		return laonongs;
	}
	
}
