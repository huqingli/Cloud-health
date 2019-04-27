package com.moy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class HomepageSportOne extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_sport_one);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("https://m.youlai.cn/yyk/articlenew/89162.html");
	}

}
