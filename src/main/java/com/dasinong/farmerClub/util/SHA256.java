package com.dasinong.farmerClub.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author xiahonggao
 * 
 *         SHA256 单向加密算法，比如加密密码
 */
public class SHA256 {

	public static String encrypt(String encryptText, String encryptKey) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(encryptKey.getBytes());
		byte[] bytes = md.digest(encryptText.getBytes());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		String encryptedPassword = SHA256.encrypt("wozaiLianpu81", "woshizhaoritian");
		System.out.println(encryptedPassword);
	}
}
