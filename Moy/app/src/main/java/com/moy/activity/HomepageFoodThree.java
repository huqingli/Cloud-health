package com.moy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class HomepageFoodThree extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_food_three);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzIxNDE0NTk1NQ==&mid=2651306897&idx=2&sn=6ec599a16c8dfc233339228a688f41a5&chksm=8c5f155dbb289c4b08071cd9d587c4fcee8032fb287ddf6f5c3271b851307f44f994eea200b9&mpshare=1&scene=23&srcid=0215SrLNHuWAkYXxkbrvM8xg#rd");
	}

}
