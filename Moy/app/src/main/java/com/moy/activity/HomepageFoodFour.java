package com.moy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class HomepageFoodFour extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_food_four);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzA3Mzg5MzgzMw==&mid=2652115578&idx=1&sn=92851967a97d91f9e0df7bff37820bd0&chksm=84e8a0dcb39f29ca043806b931d704e8a546f191a78921fbb9cf39c465cd891953b81b5a821b&mpshare=1&scene=23&srcid=0215hsiBcrwCv63wwAGoy8Y6#rd");
	}

}
