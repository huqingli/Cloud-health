package com.moy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class HomepageSportThree extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_sport_three);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MjM5NzI3MTYyMA==&mid=2652251474&idx=2&sn=1cbffa3107e73332cbaac5c8ca135a2d&chksm=bd3e04798a498d6fa3abb4e74c7e7618f13f3bdbbcc1139fb9701b6b9caad578b74517ebff70&mpshare=1&scene=23&srcid=0215a5Rlkgubn1nUxvzG49mJ#rd");
	}

}
