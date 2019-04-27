package com.moy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class HomepageSportTwo extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage_sport_two);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzIwMTU5MjE5OQ==&mid=2247485464&idx=2&sn=03605b5b6220b45758863932c40d8666&chksm=96ead4f0a19d5de6a0e626352b97863ecf4eff74a872c58ea108de96c5384343008687407ad6&mpshare=1&scene=23&srcid=0215woyXcJEG5J9hmAplWnbq#rd");
	}

}
