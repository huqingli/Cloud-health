package com.moy.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.webkit.WebView;

public class HomepageFoodOne extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_food_one);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("http://mp.weixin.qq.com/s/Qnsp44UXyiX1NxWwHu-cqQ");
	}

}
