package com.dasinong.farmerClub.dao;

import java.util.List;
import java.util.Random;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.dasinong.farmerClub.model.Proverb;

@Transactional
public class ProverbDao extends EntityHibernateDao<Proverb>implements IProverbDao {

	@Override
	public Proverb findByLunarCalender(String lunar) {
		List list = getHibernateTemplate().find("from proverb where lunarCalender=?", lunar);
		if (list == null || list.isEmpty()) {
			return null;
		}
		Random r = new Random();
		return (Proverb) list.get(r.nextInt(list.size()));
	}

	@Override
	public Proverb findByWeather(String weather) {
		List list = getHibernateTemplate().find("from Proverb where weather=?", weather);
		if (list == null || list.isEmpty()) {
			return null;
		}
		Random r = new Random();
		return (Proverb) list.get(r.nextInt(list.size()));
	}

	@Override
	public Proverb findByMonth(String month) {
		List list = getHibernateTemplate().find("from Proverb where month like '%" + month + "%'");
		if (list == null || list.isEmpty()) {
			return null;
		}
		Random r = new Random();
		return (Proverb) list.get(r.nextInt(list.size()));
	}

	@Override
	public Proverb findByAccident() {
		List list = getHibernateTemplate().find("from Proverb where month='' and lunarCalender='' and weather=''");
		if (list == null || list.isEmpty()) {
			return null;
		}
		Random r = new Random();
		return (Proverb) list.get(r.nextInt(list.size()));
	}

}
