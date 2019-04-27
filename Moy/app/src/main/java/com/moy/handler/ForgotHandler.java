package com.moy.handler;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.moy.util.ConnectUtil;

public class ForgotHandler {

	public static String forgot(String userName, String secpassword, String password) {
		String result = "";//注册返回的结果
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "forgotServlet?userName=" + userName 
				+ "&secpassword=" + secpassword + "&password=" + password);
		try {
			HttpResponse response = httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity,"utf-8");
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	2018/11/19 新增，用于实现短信验证码修改密码
	 */
	public static String changePwdByNote(String phoneNumber,String password,String VerificationCode) {
		String result = "";

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL+ "forgotServlet?phoneNumber="+phoneNumber+"&password="+password+"&verificationCode="+VerificationCode);
		try{
			HttpResponse response = httpClient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity,"utf-8");
				return result;
			}
		}catch (ClientProtocolException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return result;
	}

}