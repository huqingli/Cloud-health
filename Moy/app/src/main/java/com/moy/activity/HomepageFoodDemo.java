package com.moy.activity;
/*created by Zeyu 2.1*/
/* 现在这个页面现在出现了bug，会直接导致程序崩溃 Zeyu 2.5*/
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HomepageFoodDemo extends AppCompatActivity {

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage_food_demo);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		//使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
		CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
		mCollapsingToolbarLayout.setTitle("健康美丽吃啥呦");
		//通过CollapsingToolbarLayout修改字体颜色
		mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
		mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
		//toolbar navigationicon 改变返回按钮颜色


		final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
		upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
		getSupportActionBar().setHomeAsUpIndicator(upArrow);


		mWebView = (WebView) findViewById(R.id.webview);
		//设置支持js
		mWebView.getSettings().setJavaScriptEnabled(true);
		//!!设置跳转的页面始终在当前WebView打开
		mWebView.setWebViewClient(new WebViewClient());

		mWebView.loadUrl("http://mp.weixin.qq.com/s/Qnsp44UXyiX1NxWwHu-cqQ\n");

	}
}
