package com.dasinong.farmerClub.model;

import java.sql.Timestamp;
import java.util.Random;

import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IDasinongAppDao;
import com.dasinong.farmerClub.dao.ISecurityCodeDao;

public class SecurityCodeManager {

	private ISecurityCodeDao codeDao;

	public SecurityCodeManager(ISecurityCodeDao codeDao) {
		this.codeDao = codeDao;
	}

	public SecurityCodeManager() {
		this.codeDao = (ISecurityCodeDao) ContextLoader.getCurrentWebApplicationContext().getBean("securityCodeDao");
	}

	public SecurityCode generate() throws Exception {
		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		Timestamp expiredAt = new Timestamp(createdAt.getTime() + 1000 * 24 * 3600);

		SecurityCode securityCode = new SecurityCode();
		securityCode.setCode(this.generateSecurityCode(6));
		securityCode.setCreatedAt(createdAt);
		securityCode.setExpiredAt(expiredAt);

		this.codeDao.save(securityCode);

		return securityCode;
	}
	
	private String generateSecurityCode(int numberOfDigits) {
		String securityCode = "";
		Random generator = new Random();
		for (int i = 0; i < numberOfDigits; i++) {
			securityCode = securityCode + generator.nextInt(10);
		}
		return securityCode;
	}
}
