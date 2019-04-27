package com.moy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moy.handler.ModifyHandler;
import com.moy.handler.ModifyPasswordHandler;
import com.moy.util.MyApplication;

public class ResetpwdActivity extends Activity {
	private EditText et_Password1;
	private EditText et_Password2;

	private Button returnbtn;
	private Button confirmbtn;

	private String password;
	private String userName;
	private String password1;
	private String password2;
	private MyApplication application;
	/**
	 *
	 *@Author Zlq
	 *@Date 2018/11/26 21:29
	 *@Description: 修改密码
	 *
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		///在Android2.2以后必须添加以下代码
		//本应用采用的Android4.0
		//设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads()
				.detectDiskWrites()
				.detectNetwork()   // or .detectAll() for all detectable problems
				.penaltyLog()
				.build());
		//设置虚拟机的策略
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects()
				//.detectLeakedClosableObjects()
				.penaltyLog()
				.penaltyDeath()
				.build());
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.resetpwd);


		application = (MyApplication) getApplication();
		returnbtn = (Button)findViewById(R.id.return_btn);
		confirmbtn = (Button)findViewById(R.id.confirm);

		et_Password1 = (EditText) findViewById(R.id.password1);
		et_Password2 = (EditText) findViewById(R.id.password2);

		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ResetpwdActivity.this.finish();
			}
		});

		confirmbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				password1 = et_Password1.getText().toString();
				password2 = et_Password2.getText().toString();
				userName = application.getUser().getUserName();
				if(password1.equals("")||password2.equals("")){
					Toast.makeText(ResetpwdActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
				}
				else if(!password1.equals(password2)){
					Toast.makeText(ResetpwdActivity.this, "输入不一致", Toast.LENGTH_LONG).show();
				}
				else{
					password = password1;
					String result = ModifyPasswordHandler.modifyPassword(userName, password);
					Toast.makeText(ResetpwdActivity.this, "修改成功", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(ResetpwdActivity.this, LoginActivity.class);
					startActivity(intent);
					ResetpwdActivity.this.finish();
				}
			}
		});
	}

}

