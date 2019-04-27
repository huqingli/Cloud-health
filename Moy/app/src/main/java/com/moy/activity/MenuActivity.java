package com.moy.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends Activity implements View.OnClickListener{
		//UI
		private LinearLayout homepage;
		private LinearLayout secondpage;
		private LinearLayout thirdpage;
		private LinearLayout fourthpage;
		//Fragment
		private HomepageFragment fg1;
		private SecondpageFragment fg2;
		private ThirdpageFragment fg3;
		private FourthpageFragment fg4;
		private FragmentManager fManager;



		@Override
		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.bottom_menu);
			fManager = getFragmentManager();
			bindViews();
			homepage.performClick();
		}

		//UI初始化与事件的绑定
		private void bindViews(){
			homepage = (LinearLayout)findViewById(R.id.homepage);
			secondpage = (LinearLayout)findViewById(R.id.secondpage);
			thirdpage = (LinearLayout)findViewById(R.id.thirdpage);
			fourthpage = (LinearLayout)findViewById(R.id.fourthpage);
			
			homepage.setOnClickListener(this);
			secondpage.setOnClickListener(this);
			thirdpage.setOnClickListener(this);
			fourthpage.setOnClickListener(this);
		}
		private void setSelected(){
			ImageView v1 = (ImageView)findViewById(R.id.tab_imgview1);
			ImageView v2 = (ImageView)findViewById(R.id.tab_imgview2);
			ImageView v3 = (ImageView)findViewById(R.id.tab_imgview3);
			ImageView v4 = (ImageView)findViewById(R.id.tab_imgview4);
			v1.setBackgroundResource(R.mipmap.home1);
			v2.setBackgroundResource(R.mipmap.forest1);
			v3.setBackgroundResource(R.mipmap.manage1);
			v4.setBackgroundResource(R.mipmap.me1);
			TextView t1 = (TextView)findViewById(R.id.tab_text1);
			TextView t2 = (TextView)findViewById(R.id.tab_text2);
			TextView t3 = (TextView)findViewById(R.id.tab_text3);
			TextView t4 = (TextView)findViewById(R.id.tab_text4);

			t1.setAlpha((float) 0.5);
			t2.setAlpha((float) 0.5);
			t3.setAlpha((float) 0.5);
			t4.setAlpha((float) 0.5);


		}
		
		//隐藏Fragment
		private void hideAllFragment(FragmentTransaction fragmentTransaction){
			if(fg1 != null)fragmentTransaction.hide(fg1);
			if(fg2 != null)fragmentTransaction.hide(fg2);
			if(fg3 != null)fragmentTransaction.hide(fg3);
			if(fg4 != null)fragmentTransaction.hide(fg4);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FragmentTransaction fTransaction = fManager.beginTransaction();
			hideAllFragment(fTransaction);
			switch(v.getId()){
			case R.id.homepage:
				setSelected();
				ImageView v1 = (ImageView)findViewById(R.id.tab_imgview1);
				v1.setBackgroundResource(R.mipmap.home);
				TextView t1 = (TextView)findViewById(R.id.tab_text1);

				t1.setAlpha(1);

				if(fg1 == null){
					fg1 = new HomepageFragment();
					fTransaction.add(R.id.ly_content, fg1);
				}else{
					fTransaction.show(fg1);
				}
				break;
			case R.id.secondpage:
				setSelected();
				ImageView v2 = (ImageView)findViewById(R.id.tab_imgview2);
				v2.setBackgroundResource(R.mipmap.forecast);
				TextView t2 = (TextView)findViewById(R.id.tab_text2);
			    t2.setAlpha(1);
				if(fg2 == null){
					fg2 = new SecondpageFragment();
					fTransaction.add(R.id.ly_content, fg2);
				}else{
					fTransaction.show(fg2);
				}
				break;
			case R.id.thirdpage:
				setSelected();
				ImageView v3 = (ImageView)findViewById(R.id.tab_imgview3);
				v3.setBackgroundResource(R.mipmap.manage);
				TextView t3 = (TextView)findViewById(R.id.tab_text3);
				t3.setAlpha(1);
				if(fg3 == null){
					fg3 = new ThirdpageFragment();
					fTransaction.add(R.id.ly_content, fg3);
				}else{
					fTransaction.show(fg3);
				}
				break;
			case R.id.fourthpage:
				setSelected();
				ImageView v4 = (ImageView)findViewById(R.id.tab_imgview4);
				v4.setBackgroundResource(R.mipmap.me);
				TextView t4 = (TextView)findViewById(R.id.tab_text4);
				t4.setAlpha(1);

				if(fg4 == null){
					fg4 = new FourthpageFragment();
					fTransaction.add(R.id.ly_content, fg4);
				}else{
					fTransaction.show(fg4);
				}
				break;
			}
			fTransaction.commit();
		}

	/*****双击返回键退出*******/
	public boolean isExit = false;

	//重写onKeyDown方法,
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
			//调用exit()方法
			exit();
		}
		return false;
	}

	//被调用的exit()方法
	private void exit() {
		Timer timer;//声明一个定时器
		if (!isExit) {  //如果isExit为false,执行下面代码
			isExit = true;  //改变值为true
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
			//Toast.makeText(MenuActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();  //弹出提示
			timer = new Timer();  //得到定时器对象
			//执行定时任务,两秒内如果没有再次按下,把isExit值恢复为false,再次按下返回键时依然会进入if这段代码
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false;
				}
			}, 2000);
		} else {//如果两秒内再次按下了返回键,这时isExit的值已经在第一次按下时赋值为true了,因此不会进入if后的代码,直接执行下面的代码
			finish();
		}
	}
}
/*
v1 = (ImageView)findViewById(R.id.tab_imgview1);
		v2 = (ImageView)findViewById(R.id.tab_imgview2);
		v3 = (ImageView)findViewById(R.id.tab_imgview3);
		v4 = (ImageView)findViewById(R.id.tab_imgview4);*/