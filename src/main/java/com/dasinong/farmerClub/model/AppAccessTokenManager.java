package com.dasinong.farmerClub.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IDasinongAppDao;
import com.dasinong.farmerClub.exceptions.GenerateAppAccessTokenException;
import com.dasinong.farmerClub.exceptions.InvalidAppAccessTokenException;
import com.dasinong.farmerClub.util.AES;

public class AppAccessTokenManager {

	private IDasinongAppDao appDao;

	public AppAccessTokenManager() {
		this.appDao = (IDasinongAppDao) ContextLoader.getCurrentWebApplicationContext().getBean("dasinongAppDao");
	}

	public AppAccessTokenManager(IDasinongAppDao appDao) {
		this.appDao = appDao;
	}

	public AppAccessToken generate(Long appId) throws GenerateAppAccessTokenException {
		String appSecret;
		DasinongApp app;
		String token;

		try {
			app = this.appDao.findById(appId);
			appSecret = app.getAppSecret();
			String encryptedAppId = AES.encrypt(appId.toString(), appSecret);
			token = appId + "|" + encryptedAppId;
		} catch (Exception ex) {
			// if token can't be generated for whatever reason,
			// just throw it!
			throw new GenerateAppAccessTokenException(appId);
		}

		return new AppAccessToken(appId, appSecret, token);
	}

	public AppAccessToken parse(String token) throws InvalidAppAccessTokenException {
		Long appId;
		String appSecret;
		DasinongApp app;
		Long decryptedAppId;

		try {
			String[] tokens = token.split("\\|");
			appId = Long.valueOf(tokens[0]);
			app = this.appDao.findById(appId);
			appSecret = app.getAppSecret();
			decryptedAppId = Long.valueOf(AES.decrypt(tokens[1], appSecret));
		} catch (Exception ex) {
			// If token can't be parsed correctly for whatever reason,
			// just throw it!
			throw new InvalidAppAccessTokenException(token);
		}

		if (!appId.equals(decryptedAppId)) {
			throw new InvalidAppAccessTokenException(token);
		}

		return new AppAccessToken(appId, appSecret, token);
	}
}
