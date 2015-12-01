package com.dasinong.farmerClub.dao;

import java.sql.Timestamp;

import com.dasinong.farmerClub.exceptions.GenerateUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.InvalidUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.MultipleUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.UserAccessTokenExpiredException;
import com.dasinong.farmerClub.exceptions.UserAccessTokenNotFoundException;
import com.dasinong.farmerClub.model.UserAccessToken;

public interface IUserAccessTokenDao extends IEntityDao<UserAccessToken> {

	abstract public void deleteByUserIdAndAppId(Long userId, Long appId);

	abstract public UserAccessToken findByToken(String token);

	abstract public UserAccessToken findLiveByToken(String token) throws UserAccessTokenExpiredException;

	abstract public UserAccessToken findByUserIdAndAppId(Long userId, Long appId);

	abstract public UserAccessToken findLiveByUserIdAndAppId(Long userId, Long appId)
			throws UserAccessTokenExpiredException;

}
