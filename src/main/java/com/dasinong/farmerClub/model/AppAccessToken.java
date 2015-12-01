package com.dasinong.farmerClub.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IDasinongAppDao;
import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.exceptions.GenerateAppAccessTokenException;
import com.dasinong.farmerClub.exceptions.InvalidAppAccessTokenException;
import com.dasinong.farmerClub.util.AES;

/**
 * 
 * @author xiahonggao
 *
 *         This kind of access token is needed to request API on app's behalf.
 *         It is generated using a pre-agreed secret between the app and
 *         Dasinong and is then used during API calls.
 *
 *         App access token is generated using app's Id and secret which are
 *         both shared between app and Dasinong. The rule to generate an app
 *         access token is:
 *
 *         appId + "|" + AES.encrypt(appId, appSecret)
 * 
 *         App access token never expires and thus doesn't need to be persisted
 *         in mysql database. (Only app id and secret is persisted in database)
 * 
 *         Example code to generate/parse an app access token:
 * 
 *         AppAccessTokenManager manager = new AppAccessTokenManager(appDao);
 *         AppAccessToken token = manager.generate(appId); AppAccessToken token
 *         = manager.parse("13|KyeogIBfJIsDyOaH8c2IOA==");
 * 
 *         For security reason, AppAcessToken should never ever be hard coded
 *         into client side, doing so would give everyone who decompiled your
 *         app the ability to modify your app. This implies that most of the
 *         time, you will be using app access tokens only in server to server
 *         calls.
 * 
 */
public class AppAccessToken {

	private Long appId;
	private String appSecret;
	private String token;

	private AppAccessToken() {
	}

	public AppAccessToken(Long appId, String appSecret, String token) {
		this.appId = appId;
		this.appSecret = appSecret;
		this.token = token;
	}

	public Long getAppId() {
		return this.appId;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public String getToken() {
		return this.token;
	}

}
