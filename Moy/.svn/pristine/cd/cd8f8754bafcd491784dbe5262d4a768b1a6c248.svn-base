package com.moy.activity;

import com.moy.util.ConnectUtil;
import com.moy.util.MyApplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class AssistanceActivity extends Activity {
	
	private MyApplication application;
	private TextView tv_good;
	private TextView tv_bad;
	
	private int rate = 0;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.assistance);
		
		application = (MyApplication)getApplication();
		
		/*tv_good = (TextView)findViewById(R.id.good);
		tv_bad = (TextView)findViewById(R.id.bad);
				*/
		
		rate = application.getRate();
		
		//////////////
		WebView webview=(WebView)findViewById(R.id.webviewassistance);
		webview.loadUrl(ConnectUtil.BASE_URL+"assistancemain.html");
			
		
	}

}
