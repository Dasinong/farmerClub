package com.dasinong.farmerClub.sms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dasinong.farmerClub.dao.IDasinongAppDao;
import com.dasinong.farmerClub.dao.IShortMessageRecordDao;
import com.dasinong.farmerClub.exceptions.SmsProviderException;
import com.dasinong.farmerClub.exceptions.SmsProviderResponseParseException;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.ShortMessageRecord;
import com.dasinong.farmerClub.model.ShortMessageStatus;
import com.dasinong.farmerClub.util.StringUtils;

/**
 * 
 * @author xiahonggao
 *
 *         SMS是Short Message Service的缩写，用来发送短信。如果短信实现了IPersistentShortMessage
 *         结构，SMS会将短信保存在数据库中，方便以后分析追踪，也方便后台重发失败的短信
 */
public class SMS {

	public static final String SIGNATURE = "【大户俱乐部】";
	public static final String ACCOUNT_NAME = "dldasi01";
	public static final String PASSWORD = "TJDz75Ec";
	public static final int maxLength = 120;

	private static Logger logger = LoggerFactory.getLogger(SMS.class);

	public static void send(IShortMessage message) throws Exception {
		String productId = message.getSmsProductId();

		if (SmsProductId.canSendMultiple(productId)) {
			sendMultiple(message);
		} else {
			sendOneByOne(message);
		}
	}

	private static void sendMultiple(IShortMessage message) throws Exception {
		IShortMessageRecordDao smrDao = (IShortMessageRecordDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("shortMessageRecordDao");

		String productId = message.getSmsProductId();
		String content = message.getContent();
		String[] receivers = message.getReceivers();

		ShortMessageRecord smr = new ShortMessageRecord();

		if (message instanceof IPersistentShortMessage) {
			String data = ((IPersistentShortMessage) message).getSerializedPersistentData();
			smr.setData(data);
			smr.setStatus(ShortMessageStatus.PENDING);
			smr.setSenderId(message.getSenderId());
			smr.setReceivers(StringUtils.join(",", message.getReceivers()));
			smrDao.save(smr);
		}

		String externalId = null;
		try {
			externalId = doSend(content, productId, receivers);
		} catch (SmsProviderResponseParseException ex) {
			logger.error("fail to send short message", ex);
			smr.setStatus(ShortMessageStatus.SHOULD_RETRY);
			smr.setErrorResponse(ex.getResponse());
			smrDao.save(smr);
		} catch (Exception ex) {
			logger.error("fail to send short message", ex);
			smr.setStatus(ShortMessageStatus.SHOULD_RETRY);
			smr.setErrorResponse(ex.getMessage());
			smrDao.save(smr);
		}
		
		if (externalId == null) {
			return;
		}

		if (message instanceof IPersistentShortMessage) {
			smr.setStatus(ShortMessageStatus.SUCCESS);
			smr.setExternalId(externalId);
			smrDao.update(smr);
		}
	}

	private static void sendOneByOne(IShortMessage message) throws Exception {
		IShortMessageRecordDao smrDao = (IShortMessageRecordDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("shortMessageRecordDao");

		String productId = message.getSmsProductId();
		String content = message.getContent();
		String[] receivers = message.getReceivers();

		for (String receiver : receivers) {
			try {
				ShortMessageRecord smr = new ShortMessageRecord();

				if (message instanceof IPersistentShortMessage) {
					String data = ((IPersistentShortMessage) message).getSerializedPersistentData();
					smr.setData(data);
					smr.setStatus(ShortMessageStatus.PENDING);
					smr.setSenderId(message.getSenderId());
					smr.setReceivers(receiver);
					smrDao.save(smr);
				}

				String externalId = null;
				try {
					externalId = doSend(content, productId, receivers);
				} catch (SmsProviderResponseParseException ex) {
					logger.error("fail to send short message", ex);
					
					smr.setStatus(ShortMessageStatus.SHOULD_RETRY);
					smr.setErrorResponse(ex.getResponse());
					smrDao.save(smr);
				} catch (Exception ex) {
					logger.error("fail to send short message", ex);
					smr.setStatus(ShortMessageStatus.SHOULD_RETRY);
					smr.setErrorResponse(ex.getMessage());
					smrDao.save(smr);
				}
				
				if (externalId == null) {
					continue;
				}

				if (message instanceof IPersistentShortMessage) {
					smr.setStatus(ShortMessageStatus.SUCCESS);
					smr.setExternalId(externalId);
					smrDao.update(smr);
				}
			} catch (Exception ex) {
				logger.error("fail to send short message", ex);
				// ignore the exception and try to send the remainings
			}
		}
	}

	public static void sendSafe(IShortMessage message) {
		try {
			send(message);
		} catch (Exception ex) {
			// ignore
		}
	}

	private static String doSend(String content, String productId, String[] cellphones) throws Exception {
		String cellphone = StringUtils.join(",", cellphones);
		return doSend(content, productId, cellphone);
	}

	private static String doSend(String content, String productId, String cellphone) throws Exception {
		if (content.length() > maxLength) {
			content = content.substring(0, maxLength);
		}

		// 签名必须要有！
		content += SMS.SIGNATURE;

		String postData = "sname=" + ACCOUNT_NAME + "&spwd=" + PASSWORD + "&scorpid=&sprdid=" + productId + "&sdst="
				+ cellphone + "&smsg=" + URLEncoder.encode(content, "UTF-8");

		// 发送POST请求
		URL url = new URL("http://cf.51welink.com/submitdata/Service.asmx/g_Submit");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setUseCaches(false);
		conn.setDoOutput(true);

		conn.setRequestProperty("Content-Length", "" + postData.length());
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		out.write(postData);
		out.flush();
		out.close();

		// 获取响应状态
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new SmsProviderException(conn.getResponseCode(), conn.getResponseMessage());
		}

		// 获取响应内容体
		String line, result = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		while ((line = in.readLine()) != null) {
			result += line + "\n";
		}
		in.close();

		// 解析响应内容
		Integer state = null;
		String msgId = null;
		try {
			ByteArrayInputStream input = new ByteArrayInputStream(result.getBytes("UTF-8"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(input);
			NodeList nodeList = document.getElementsByTagName("State");
			Node stateNode = nodeList.item(0);
			nodeList = document.getElementsByTagName("MsgID");
			Node idNode = nodeList.item(0);
			state = Integer.parseInt(stateNode.getFirstChild().getNodeValue());
			msgId = idNode.getFirstChild().getNodeValue();
		} catch (Exception ex) {
			logger.error("Cannot parse SMS response", ex);
			throw new SmsProviderResponseParseException(result);
		}

		if (state != 0) {
			Exception ex = new SmsProviderResponseParseException(result);
			logger.error("SMS response state non-zero", ex);
			throw ex;
		}

		if ("0".equals(msgId)) {
			Exception ex = new SmsProviderResponseParseException(result);
			logger.error("SMS response msgId zero", ex);
			throw ex;
		}

		return msgId;
	}

	public static void main(String[] args) throws Exception {
		String cellphone = "13681634981";
		String cellphone2 = "13137736397";
		String[] cellphones = { cellphone, cellphone2 };
		// RefAppShortMessage message1 = new RefAppShortMessage();
		// SecurityCodeShortMessage message2 = new
		// SecurityCodeShortMessage("123456");
		// SMS.send(message1, cellphone);
		// SMS.send(message1, cellphones);
		// SMS.send(message2, cellphone);
		// SMS.send(message2, cellphones);
		CouponWarningShortMessage cwsm = new CouponWarningShortMessage(10,100);
		SMS.send(cwsm);
		//SMS.doSend("10号活动还剩500张卷","1012818","13162881998");
	}
}
