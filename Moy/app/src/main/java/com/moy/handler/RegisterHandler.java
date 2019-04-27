package com.moy.handler;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mob.MobSDK;
import com.moy.pojo.UserPOJO;
import com.moy.util.ConnectUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterHandler {

	public static String register(final Context mContext, String phoneNum, String password) {
		String result = "";//注册返回的结果
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "registerServlet?phoneNum=" + phoneNum
				+ "&password=" + password);
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
	public static String insertR(String phoneNum) {
		String result = "";//注册返回的结果
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "insertServlet?phoneNum=" + phoneNum);
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

	public static String register(String userName, String password,
			String secpassword, String sex, String age, String height, String weight, String telephone) {
		// TODO Auto-generated method stub
        String result = "";//注册返回的结果
		//String users=JSON.stringify(user); 
		HttpClient httpclient = new DefaultHttpClient();//为了避免需要证书，所以用一个类继承DefaultHttpClient类，忽略校验过程。
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "registerServlet?userName=" + userName 
				+ "&password=" + password + "&secpassword=" + secpassword+"&sex="+
				sex+"&age="+age+"&height="+height
				+"&weight="+weight+"&telephone="+telephone);
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

	public static String checkphonenum(String phonenum){
		System.out.println("检查手机号"+phonenum);
		String result = "";
		HttpClient httpclient = new DefaultHttpClient();//为了避免需要证书，所以用一个类继承DefaultHttpClient类，忽略校验过程。
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "checkServlet?phoneNum=" + phonenum);
		try {
			HttpResponse response = httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity,"utf-8");
				System.out.println("返回的结果"+result);
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