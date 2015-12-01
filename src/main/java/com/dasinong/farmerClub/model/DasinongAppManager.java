package com.dasinong.farmerClub.model;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IDasinongAppDao;
import com.dasinong.farmerClub.dao.IUserAccessTokenDao;

public class DasinongAppManager {

	private IDasinongAppDao appDao;

	public DasinongAppManager(IDasinongAppDao appDao) {
		this.appDao = appDao;
	}

	public DasinongAppManager() {
		this.appDao = (IDasinongAppDao) ContextLoader.getCurrentWebApplicationContext().getBean("dasinongAppDao");
	}

	public DasinongApp generate(String appName) throws Exception {
		DasinongApp app = new DasinongApp();
		app.setAppName(appName);

		// App secret is used as encrypt key of AES algorithm.
		// Thus, it has to be 16 bytes.
		SecureRandom rand = new SecureRandom();
		String appSecret = Base64.getEncoder().encodeToString(rand.generateSeed(16));
		app.setAppSecret(appSecret);

		this.appDao.save(app);

		return app;
	}
}
