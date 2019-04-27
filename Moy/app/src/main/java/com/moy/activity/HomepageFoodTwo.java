package com.moy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class HomepageFoodTwo extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_food_two);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzIxNDE0NTk1NQ==&mid=2651306926&idx=2&sn=b7a602e929131c1caa661decd5eeaa42&chksm=8c5f1562bb289c74608d043c2e298d2b7f1bb1d89f836c88b0d86d6f64bcbe2ee5cab82379f5&mpshare=1&scene=23&srcid=0215FY3YTBmUTUgYmQ8QoKKV#rd");
	}

}
