package com.dasinong.farmerClub.daotest.utils;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Random;

import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.store.StoreStatus;

public class TestDataUtils {

	public static User genRandomUser() {
		Random rnd = new Random();
		User user = new User();
		user.setUserName("赵日天" + rnd.nextInt(99999));
		user.setAddress("断罪小学风云堂");
		user.setCreateAt(TestDataUtils.getCurrentTimeInSeconds());
		user.setAuthenticated(true);
		user.setIsPassSet(false);
		user.setCellPhone("139192" + rnd.nextInt(99999));
		return user;
	}
	
	private static Timestamp getCurrentTimeInSeconds() {
		// % 1000 is needed because mysql stores timestamp in seconds
		// while System.currentTimeMillis returns milli seconds
		Long mseconds = System.currentTimeMillis();
		mseconds = mseconds - mseconds % 1000;
		return new Timestamp(mseconds);
	}
	
	
}
