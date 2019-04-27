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

public class ModifyHandler {

	public static String modify(String phoneNum,String userName, String sex, String age, String height,
			String weight, String merPhone) {
		String result = "";//注册返回的结果
		System.out.println("USERNAME" +userName);
		System.out.println("SEX" +sex);
		System.out.println("AGE" +age);
		System.out.println("HEIGHT" +height);
		System.out.println("WEIGHT" +weight);
		System.out.println("MERPHONE" +merPhone);
		System.out.println("PHONENUM" +phoneNum);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "modifyServlet?userName=" + userName + "&sex=" + sex + "&age=" + age + "&height=" + height + "&weight=" + weight + "&phoneNum" + phoneNum + "&merPhone" + merPhone);
		try {
			HttpResponse response = httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity,"utf-8");
				System.out.println("结果！！！！"+ result);
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}


}