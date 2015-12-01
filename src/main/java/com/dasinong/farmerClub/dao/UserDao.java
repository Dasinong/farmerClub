package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.Step;
import com.dasinong.farmerClub.model.User;

public class UserDao extends EntityHibernateDao<User>implements IUserDao {

	@Override
	public User findByUserName(String userName) {
		List list = getHibernateTemplate().find("from User where userName=?", userName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (User) list.get(0);
	}

	@Override
	public User findByCellphone(String cellphone) {
		List list = getHibernateTemplate().find("from User where cellphone=?", cellphone);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (User) list.get(0);
	}

	@Override
	public User findByQQ(String qqtoken) {
		List list = getHibernateTemplate().find("from User where qqtoken=?", qqtoken);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (User) list.get(0);
	}

	@Override
	public User findByWeixin(String weixintoken) {
		List list = getHibernateTemplate().find("from User where weixintoken=?", weixintoken);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (User) list.get(0);
	}

	@Override
	public long getUIDbyRef(String refcode) {
		List list = getHibernateTemplate().find("from User where refcode=?", refcode);
		if (list.size() > 0)
			return ((User) list.get(0)).getUserId();
		else
			return -1;
	}

	@Override
	public List<User> getAllUsersWithEmptyRefCode() {
		List<User> list = getHibernateTemplate().find("from User where refcode IS NULL");
		return list;
	}

	@Override
	public List<User> getAllUsersWithPassword() {
		List<User> list = this.getHibernateTemplate().find("from User where password is not NULL");
		return list;
	}
}
