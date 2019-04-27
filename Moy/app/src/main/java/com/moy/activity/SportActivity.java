package com.moy.activity;


import org.json.JSONException;
import org.json.JSONObject;

import com.moy.handler.ResultAboutTestHandler;
import com.moy.pojo.ResultAnalysisPOJO;
import com.moy.util.MyApplication;
import android.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SportActivity extends Activity implements OnClickListener {
	private MyApplication application;
	private String userName;
	private String userAge;
	private TextView tv_good;
	private TextView tv_bad;
	private TextView tv_grade;
	private TextView tv_sport;

	private ResultAnalysisPOJO ra = new ResultAnalysisPOJO();

	@Override
	public void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sport);
		///在Android2.2以后必须添加以下代码
		//本应用采用的Android4.0
		//设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads()
				.detectDiskWrites()
				.detectNetwork()   // or .detectAll() for all detectable problems
				.penaltyLog()
				.build());
		//设置虚拟机的策略
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects()
				//.detectLeakedClosableObjects()
				.penaltyLog()
				.penaltyDeath()
				.build());


		application = (MyApplication)getApplication();
		userName = application.getUser().getUserName();
		userAge = application.getUser().getAge();
		tv_grade = (TextView)findViewById(R.id.grade);
		tv_sport = (TextView)findViewById(R.id.sport);

		String result = ResultAboutTestHandler.result(userName,userAge,"Sport"); //1:检测报告的UID

		try{
			JSONObject json = new JSONObject(result);
			if(json.getString("message").equals("fail"))
			{
				Toast.makeText(SportActivity.this, "请先进行心电检测!", Toast.LENGTH_LONG).show();
				tv_grade.setText("没有相应数据");
				tv_sport.setText("");


			}
			else if(json.getString("message").equals("success"))
			{
				Toast.makeText(SportActivity.this, "查询成功", Toast.LENGTH_LONG).show();
						/*Log.i("ResultAct", json.getInt("id")+"");
						Log.i("ResultAct", json.getString("grade"));*/

				ra.setId(json.getInt("id"));
				ra.setSportcomment(json.getString("sportcomment"));

				tv_grade.setText(ra.getGrade());
				tv_sport.setText(ra.getSportcomment().replace("\\n","\n"));
			}
		}catch(JSONException se){
			se.printStackTrace();
		}



	}
	private void setContentView(Class<layout> class1) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
