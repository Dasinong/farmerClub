package com.dasinong.farmerClub.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.dasinong.farmerClub.model.ProStock;

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
}
