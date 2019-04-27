package com.moy.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.moy.pojo.ResultPOJO;
import com.moy.util.ConnectUtil;


import android.widget.Toast;

public class ExaminationHandler {


	/*public static void setresult(ResultPOJO result) {
		// TODO Auto-generated method stub
		
		//首先声明一下Url
		String urlPath = ConnectUtil.BASE_URL;
		URL url;
		try {
		url = new URL(urlPath);

		// 然后我们使用httpPost的方式把lientKey封装成Json数据的形式传递给服务器
		// 在这里呢我们要封装的时这样的数据
		// {"Person":{"username":"zhangsan","age":"12"}}
		// 我们首先使用的是JsonObject封装 {"username":"zhangsan","age":"12"}
		JSONObject ClientKey = new JSONObject();
		ClientKey.put("userName", "zhangsan");
		ClientKey.put("rate", "12");
		// 接着我们使用JsonObject封装{"Person":{"username":"zhangsan","age":"12"}}
		JSONObject Authorization = new JSONObject();
		Authorization.put("Result", ClientKey);
		//我们把JSON数据转换成String类型使用输出流向服务器写
		String content = String.valueOf(Authorization);
		// 现在呢我们已经封装好了数据,接着呢我们要把封装好的数据传递过去
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		// 设置允许输出
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		// 设置User-Agent: Fiddler
		conn.setRequestProperty("ser-Agent", "Fiddler");
		// 设置contentType
		conn.setRequestProperty("Content-Type", "application/json");
		OutputStream os = conn.getOutputStream();
		os.write(content.getBytes());
		os.close();
		//服务器返回的响应码
		int code = conn.getResponseCode();
		if (code == 200) {
		// 等于200了,下面呢我们就可以获取服务器的数据了
		//Toast.makeText(this, "Post完成", 1).show();
		// 在这里我们已经连接上了，也获得了服务器的数据了，
		// 那么我们接着就是解析服务器传递过来的数据，
		// 现在我们开始解析服务器传递过来的参数,
		//假设服务器返回的是{"Person":{"username":"zhangsan","age":"12"}}
		InputStream is = conn.getInputStream();
		//下面的json就已经是{"Person":{"username":"zhangsan","age":"12"}} //这个形式了,只不过是String类型
		String json = NetUtils.readString(is);
		//然后我们把json转换成JSONObject类型得到{"Person": //{"username":"zhangsan","age":"12"}}
		JSONObject jsonObject = new JSONObject(json)
		//然后下面这一步是得到{{"username":"zhangsan","age":"12"}
		.getJSONObject("username");
		//下面的Person是一个bean,里面有username,和 age
		ResultPOJO p= new ResultPOJO();
		String username =jsonObject.getString("username");
		String age = jsonObject.getString("age");
		//上面已经完整地做出了JSON的传递和解析
		} else {
		//Toast.makeText(getApplicationContext(), "数据提交失败", 1).show();
		}
		} catch (Exception e) {
		e.printStackTrace();

		}*/
		
		

		
		
/*	}*/

	public static String loadresultH(String state,String heartRate, String heartAge, String relaxationLevel, String respirationRate,
			String fmHeartAge,String date,String rrInterval, int userid) {
		// TODO Auto-generated method stub
		 String result = "";//数据上传后返回的结果
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "resultServlet?state=" + state + "heartrate=" + heartRate
					+ "&heartage=" + heartAge + "&relaxationlevel=" + relaxationLevel + "&respirationrate=" + respirationRate 
					+ "&fmheartrate=" + fmHeartAge+ "&date=" + date+"&rrinterval=" + rrInterval+"&userid="+userid);
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
	public static String loadresultB(String state,String highpressure,String lowpressure, int userid) {
		// TODO Auto-generated method stub
		String result = "";//数据上传后返回的结果
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "resultServlet?state=" + state
				+ "&highpressure=" + highpressure + "&lowpressure=" + lowpressure +"&userid="+userid);
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