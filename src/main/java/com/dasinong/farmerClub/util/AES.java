package com.dasinong.farmerClub.util;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// TODO: org.apache.commons.codec.binary.Base64的加密最后是回车，这个是不对的
import org.apache.commons.codec.binary.Base64;

/**
 * @author xiahonggao
 *
 *         AES 双向加密算法，可用来生成UserAccessToken或者AppAccessTOken
 *         
 */
public class AES {

	/**
	 * Encrypt message using given secret
	 * 
	 * @param message
	 *            (message to be encrypted)
	 * @param secret
	 *            (base64 encoded secret, must be 16 bytes)
	 * @return base64 encrypted message
	 * @throws Exception
	 */
	public static final String encrypt(final String message, final String secret) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec spec = new SecretKeySpec(Base64.decodeBase64(secret), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, spec);
		byte[] stringBytes = message.getBytes();
		byte[] raw = cipher.doFinal(stringBytes);
		return Base64.encodeBase64String(raw).trim();
	}

	/**
	 * Decrypt message using given secret
	 * 
	 * @param encrypted
	 *            (base64 encrypted message)
	 * @param secret
	 *            (base64 encoded secret, must be 16 bytes)
	 * @return decrypted message
	 * @throws Exception
	 */
	public static final String decrypt(final String encrypted, final String secret) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec spec = new SecretKeySpec(Base64.decodeBase64(secret), "AES");
		cipher.init(Cipher.DECRYPT_MODE, spec);

		byte[] raw = Base64.decodeBase64(encrypted);
		byte[] stringBytes = cipher.doFinal(raw);
		String clearText = new String(stringBytes, "UTF8");
		return clearText;
	}

	public static void main(String[] args) throws Exception {
		String key = Base64.encodeBase64String("woShiZhaoRiTian!".getBytes());

		String clearText = "12345,2,1445966485";
		System.out.println("Clear Text:" + clearText);
		String encryptedString = AES.encrypt(clearText, key);
		System.out.println("Encrypted String:" + encryptedString);
		String decryptedString = AES.decrypt(encryptedString, key);
		System.out.println("Decrypted String:" + decryptedString);
	}

}