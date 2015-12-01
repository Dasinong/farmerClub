package com.dasinong.farmerClub.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class MailHelper {

	public MailHelper() {
	}

	public void sendMail(String target, String subject, String content) {
		// final String url = "http://sendcloud.sohu.com/webapi/mail.send.json";

		final String host = "sendcloud.sohu.com";
		HttpClient hc = new HttpClient();
		hc.getHostConfiguration().setHost(host, 80, "http");
		try {
			HttpMethod method = postMethod(target, subject, content);
			hc.executeMethod(method);
			String response = new String(method.getResponseBodyAsString().getBytes("ISO-8859-1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static HttpMethod postMethod(String target, String subject, String content) throws IOException {
		final String url = "/webapi/mail.send.json";
		PostMethod post = new PostMethod(url);

		post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		final String apiUser = "cdntot_test_T1oxK1";
		final String apiKey = "hXXuY2TkMSJnuhZW";

		NameValuePair[] param = { new NameValuePair("api_user", apiUser), new NameValuePair("api_key", apiKey),
				new NameValuePair("from", "service@sendcloud.im"), new NameValuePair("fromname", "今日农事"),
				new NameValuePair("to", target), new NameValuePair("subject", subject),
				new NameValuePair("html", content), new NameValuePair("resp_email_id", "true") };
		post.setRequestBody(param);
		post.releaseConnection();
		return post;
	}

	public static void main(String[] args) {
		MailHelper mh = new MailHelper();
		mh.sendMail("coordinate6467@hotmail.com", "来自SendCloud的第一封邮件！",
				"你太棒了！你已成功的从SendCloud发送了一封测试邮件，接下来快登录前台去完善账户信息吧！");
	}
}
