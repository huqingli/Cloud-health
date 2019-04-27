package com.moy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class HomepageSportFour extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_sport_four);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzAwNjY5NzIyMA==&mid=2651957632&idx=1&sn=f67e3bfca7379b2eb6cf07c4418732e8&mpshare=1&scene=23&srcid=0215X9cTE6OM8VnW1owlhIXK#rd");
	}

}
