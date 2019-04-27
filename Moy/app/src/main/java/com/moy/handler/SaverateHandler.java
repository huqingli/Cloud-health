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

public class SaverateHandler {
	
	public static String saverate(String userName, int average_rate){
		String result = "";
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "saverateServlet?userName=" + userName + "&rate=" +
		average_rate);
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
		
	

}
