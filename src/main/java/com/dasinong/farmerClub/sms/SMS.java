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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dasinong.farmerClub.exceptions.SmsProviderException;
import com.dasinong.farmerClub.exceptions.SmsProviderResponseParseException;
import com.dasinong.farmerClub.model.Institution;

/**
 * 
 * @author xiahonggao
 *
 * SMS是Short Message Service的缩写，用来发送短信。如果短信实现了IPersistentShortMessage
 * 结构，SMS会将短信保存在数据库中，方便以后分析追踪，也方便后台重发失败的短信 
 */
public class SMS {
	
	public static final String SIGNATURE = "【今日农事】";
	public static final String ACCOUNT_NAME = "dldasi00";
	public static final String PASSWORD = "MF3o9AFn";
	public static final int maxLength = 120;
	
	private static Logger logger = LoggerFactory.getLogger(SMS.class);

	public static void send(IShortMessage message, String cellphone) throws Exception {
		String content = message.getContent();
		
		if (message instanceof IPersistentShortMessage) {
			// TODO (xiahonggao): save <message, cellphone> to db with status=PENDING
		}

		doSend(message, cellphone);

		if (message instanceof IPersistentShortMessage) {
			// TODO (xiahonggao): update <message, cellphone> in db with status=SUCCESS
		}
	}
	
	public static void sendSafe(IShortMessage message, String cellphone) {
		try {
			send(message, cellphone);
		} catch (Exception ex) {
			// ignore
		}
	}
	
	public static void send(IShortMessage message, String[] cellphones) throws Exception {
		String productId = message.getSmsProductId();
		
		// 如果产品id支持群发，直接发送
		if (SmsProductId.canSendMultiple(productId)) {
			ArrayList<String> numbers = new ArrayList<String>();
			for (String cellphone : cellphones) {
				numbers.add(cellphone);
			}
			send(message, convertNumbers(numbers));
			return;
		}
		
		// 如果不支持，则一条条发
		if (message instanceof IPersistentShortMessage) {
			// TODO (xiahonggao): save all the <message, cellphone> pairs to db with status=PENDING
		}
		
		for (String cellphone : cellphones) {
			try {
				send(message, cellphone);
				if (message instanceof IPersistentShortMessage) {
					// TODO (xiahonggao): update <message, cellphone> in db with status=SUCCESS
				}
			} catch (Exception ex) {
				// ignore the exception and try the remaining
			}
		}
	}
	
	public static void sendSafe(IShortMessage message, String[] cellphones) {
		try {
			send(message, cellphones);
		} catch (Exception ex) {
			// ignore
		}
	}
	
	public static void send(IShortMessage message, List<String> cellphones) throws Exception {
		send(message, convertNumbers(cellphones));
	}
	
	public static void sendSafe(IShortMessage message, List<String> cellphones) {
		try {
			send(message, cellphones);
		} catch (Exception ex) {
			// ignore
		}
	}
	
	public static String convertNumbers(List<String> numbers) {
		String numbersString = "";
		if (numbers.size() > 0) {
			for (int i = 0; i < numbers.size() - 1; i++) {
				numbersString += numbers.get(i).trim() + ",";
			}
			numbersString += numbers.get(numbers.size() - 1).trim();
		}

		return numbersString;
	}

	private static void doSend(IShortMessage message, String cellphone) throws Exception {
		String content = message.getContent();
		if (content.length() > maxLength) {
			content = content.substring(0, maxLength);
		}
		
		// 签名必须要有！
		content += SMS.SIGNATURE;
		
		String productId = message.getSmsProductId();
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
	}
	
	public static void main(String[] args) throws Exception {
		String cellphone = "13681634981";
		String cellphone2 = "13137736397";
		String[] cellphones = {cellphone, cellphone2};
		RefAppShortMessage message1 = new RefAppShortMessage(Institution.DOWS);
		SecurityCodeShortMessage message2 = new SecurityCodeShortMessage("123456");
		SMS.send(message1, cellphone);
		SMS.send(message1, cellphones);
		SMS.send(message2, cellphone);
		SMS.send(message2, cellphones);
	}
}
