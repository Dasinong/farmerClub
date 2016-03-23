package com.dasinong.farmerClub.dao;

import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.dasinong.farmerClub.model.ProStock;
import com.dasinong.farmerClub.model.Proverb;

public class ProStockDao extends EntityHibernateDao<ProStock> implements IProStockDao{

	@Override
	@Transactional
	public List<Object[]> countStockByUserid(long userId) {
		Query query = this.getSessionFactory().getCurrentSession()
				.createQuery("SELECT prodid,prodname,prospecial,count(userid) FROM ProStock where userid= :userId group by prodid");
		query.setParameter("userId", userId);
		List result = query.list();
		return result;
	}
	
	@Override
	@Transactional
	public ProStock getByBoxcode(String boxcode) {
		List list = getHibernateTemplate().find("from ProStock where boxcode=?", boxcode);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list==null || list.size()==0) return null;
		else return (ProStock) list.get(0);
	}
}
