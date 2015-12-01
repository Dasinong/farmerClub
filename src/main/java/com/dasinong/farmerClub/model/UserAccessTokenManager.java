package com.dasinong.farmerClub.model;

import java.net.URLEncoder;
import java.sql.Timestamp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IUserAccessTokenDao;
import com.dasinong.farmerClub.exceptions.GenerateUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.InvalidUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.UserAccessTokenExpiredException;
import com.dasinong.farmerClub.util.AES;
import com.dasinong.farmerClub.util.Env;

public class UserAccessTokenManager {

	private IUserAccessTokenDao tokenDao;

	public UserAccessTokenManager() {
		this.tokenDao = (IUserAccessTokenDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("userAccessTokenDao");
	}

	public UserAccessTokenManager(IUserAccessTokenDao tokenDao) {
		this.tokenDao = tokenDao;
	}

	public UserAccessToken generate(Long userId, Long appId) throws GenerateUserAccessTokenException {
		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		Timestamp expiredAt = new Timestamp(createdAt.getTime() + Env.getEnv().accessTokenLifeMS);

		// If token exists and is still live, renew it
		UserAccessToken accessToken = this.tokenDao.findByUserIdAndAppId(userId, appId);
		if (accessToken != null && !accessToken.isExpired()) {
			accessToken.setExpiredAt(expiredAt);
			this.tokenDao.update(accessToken);
			return accessToken;
		}

		// Otherwise, create a new access token
		accessToken = new UserAccessToken();
		accessToken.setUserId(userId);
		accessToken.setAppId(appId);
		accessToken.setCreatedAt(createdAt);
		accessToken.setExpiredAt(expiredAt);

		try {
			// generate token
			String token = AES.encrypt(userId + "|" + appId + "|" + createdAt.getTime(), UserAccessToken.SALT);
			accessToken.setToken(token);
		} catch (Exception ex) {
			// For whatever reason, just throw it
			throw new GenerateUserAccessTokenException(userId, appId);
		}

		this.tokenDao.save(accessToken);

		return accessToken;
	}

	public UserAccessToken parse(String token) throws InvalidUserAccessTokenException, UserAccessTokenExpiredException {
		UserAccessToken accessToken = this.tokenDao.findLiveByToken(token);

		if (accessToken == null) {
			throw new InvalidUserAccessTokenException(token);
		}

		String decryptedStr;
		Long userId;
		Long appId;

		try {
			decryptedStr = AES.decrypt(token, UserAccessToken.SALT);
			String[] parts = decryptedStr.split("\\|");
			userId = Long.valueOf(parts[0]);
			appId = Long.valueOf(parts[1]);
		} catch (Exception ex) {
			throw new InvalidUserAccessTokenException(token);
		}

		// This should NEVER happen..
		if (!appId.equals(accessToken.getAppId()) || !userId.equals(accessToken.getUserId())) {
			throw new InvalidUserAccessTokenException(token);
		}

		return accessToken;
	}

	public void renew(UserAccessToken accessToken) throws UserAccessTokenExpiredException {
		if (accessToken.isExpired()) {
			throw new UserAccessTokenExpiredException(accessToken.getToken());
		}

		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		Timestamp expiredAt = new Timestamp(createdAt.getTime() + Env.getEnv().accessTokenLifeMS);
		accessToken.setExpiredAt(expiredAt);
		this.tokenDao.update(accessToken);
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/LocalDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");

		IUserAccessTokenDao tokenDao = (IUserAccessTokenDao) applicationContext.getBean("userAccessTokenDao");
		System.out.println(URLEncoder.encode("TKb+kwnBHZlTO84dYGRFLXypeUjxWNmBFH6G3e6wZkA="));
		/*
		 * Long appId = DasinongApp.ANDROID_FARM_LOG; Long userId = 498L;
		 * UserAccessTokenManager manager = new
		 * UserAccessTokenManager(tokenDao); UserAccessToken token =
		 * manager.generate(userId, appId);
		 * System.out.println(token.getToken()); UserAccessToken decryptedToken
		 * = manager.parse(token.getToken());
		 * System.out.println(token.getUserId());
		 * System.out.println(token.getAppId());
		 * 
		 * tokenDao.deleteByUserIdAndAppId(userId, appId);
		 */
	}
}
