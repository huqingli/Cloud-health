package com.moy.handler;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.moy.activity.LoginActivity;
import com.moy.activity.MenuActivity;
import com.moy.database.UserDao;
import com.moy.pojo.UserPOJO;
import com.moy.util.ConnectUtil;

import static com.moy.util.MyApplication.setUser;

public class LoginHandler extends Activity{

	public static String login(String userName, String password) {
		String result = "";//注册返回的结果
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "loginServlet?phoneNum=" + userName
				+ "&password=" + password);
	//	HttpGet request = new HttpGet("http://localhost:8080/moy/loginServlet?userName=moymoy&password=123123");		
			try {
				//!!!
			HttpResponse response = httpclient.execute(request);
			System.out.print(response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "utf-8");
				Log.i("Login.result:",result);
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String login(String CountryCode,String PhoneNum,String VerificationCode){
		String result = "";

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL+ "loginServlet?countryCode="+CountryCode+"&phoneNum="+PhoneNum+"&verificationCode="+VerificationCode);
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