package com.dasinong.farmerClub.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.dasinong.farmerClub.exceptions.GenerateUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.InvalidUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.MultipleUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.UserAccessTokenExpiredException;
import com.dasinong.farmerClub.exceptions.UserAccessTokenNotFoundException;
import com.dasinong.farmerClub.model.UserAccessToken;
import com.dasinong.farmerClub.util.AES;
import com.dasinong.farmerClub.util.Env;

// TODO
public class UserAccessTokenDao extends EntityHibernateDao<UserAccessToken>implements IUserAccessTokenDao {

	@Override
	@Transactional
	public void deleteByUserIdAndAppId(Long userId, Long appId) {
		Query query = this.getSessionFactory().getCurrentSession()
				.createQuery("delete from UserAccessToken where userId = :userId and appId = :appId");
		query.setParameter("userId", userId);
		query.setParameter("appId", appId);
		query.executeUpdate();
		
	}

	@Override
	public UserAccessToken findByToken(String token) {
		List<UserAccessToken> tokens = this.getHibernateTemplate().find("from UserAccessToken where token = ?", token);

		if (tokens == null || tokens.size() == 0) {
			return null;
		}

		return tokens.get(0);
	}

	@Override
	public UserAccessToken findLiveByToken(String token) throws UserAccessTokenExpiredException {
		UserAccessToken accessToken = this.findByToken(token);
		if (accessToken != null && accessToken.isExpired()) {
			throw new UserAccessTokenExpiredException(token);
		}

		return accessToken;
	}

	@Override
	public UserAccessToken findByUserIdAndAppId(Long userId, Long appId) {
		List<UserAccessToken> tokens = this.getHibernateTemplate()
				.find("from UserAccessToken where userId = ? and appId = ?", userId, appId);

		if (tokens == null || tokens.size() == 0) {
			return null;
		}

		return tokens.get(0);
	}

	@Override
	public UserAccessToken findLiveByUserIdAndAppId(Long userId, Long appId) throws UserAccessTokenExpiredException {
		UserAccessToken accessToken = this.findByUserIdAndAppId(userId, appId);
		if (accessToken != null && accessToken.isExpired()) {
			throw new UserAccessTokenExpiredException(accessToken.getToken());
		}

		return accessToken;
	}
}
