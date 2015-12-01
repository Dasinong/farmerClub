package com.dasinong.farmerClub.model;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IDasinongAppDao;
import com.dasinong.farmerClub.dao.IUserAccessTokenDao;
import com.dasinong.farmerClub.exceptions.InvalidUserAccessTokenException;
import com.dasinong.farmerClub.util.AES;

/**
 * 
 * @author xiahonggao
 *
 *         UserAccessToken is the most commonly used type of token. This kind of
 *         access token is needed any time dasinong app calls an API to
 *         manipulate user's data on their behalf
 * 
 *         UserAccessToken is generated using user's Id, app's Id, creation time
 *         and pre-defined SALT. The rule to generate a user access token is:
 * 
 *         AES.encrypt(userId+","+appId+","+timestamp, SALT)
 * 
 *         User access token expires in 60 days and is renewed when app calls
 *         any API on user's behalf.
 *
 *         User access token is stored in mysql database with the following
 *         fields: - userId - appId - createdAt - expiredAt - token
 * 
 *         Example code to generate/parse user access token:
 * 
 *         UserAccessToken token = UserAccessToken.generate(userId, appId);
 *         UserAccessToken token = UserAccessToken.parse(tokenStr);
 * 
 */
public class UserAccessToken {

	public static final String SALT = Base64.encodeBase64String("woShiZhaoRiTian!".getBytes());

	private Long id;
	private Long userId;
	private Long appId;
	private Timestamp createdAt;
	private Timestamp expiredAt;
	private String token;

	public UserAccessToken() {
	}

	public UserAccessToken(Long id, Long userId, Long appId, String token, Timestamp createdAt, Timestamp expiredAt) {
		this.id = id;
		this.userId = userId;
		this.appId = appId;
		this.token = token;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setExpiredAt(Timestamp expiredAt) {
		this.expiredAt = expiredAt;
	}

	public Long getId() {
		return this.id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public Long getAppId() {
		return this.appId;
	}

	public String getToken() {
		return this.token;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public Timestamp getExpiredAt() {
		return this.expiredAt;
	}

	public boolean isExpired() {
		return this.expiredAt.before(new Date());
	}
}
