package com.moy.activity;
import org.json.JSONException;
import org.json.JSONObject;

import com.moy.handler.ForgotHandler;
import com.moy.handler.RegisterHandler;

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


public class  ForgotpwdActivity extends Activity {
	
	private Button returnBtn;
	private Button confirmBtn;
	private Button forgot;
	
	private EditText et_username;
	private EditText et_secpassword;
	private EditText et_password;
	private EditText et_repassword;
	
	private String userName;
	private String secpassword;
	private String password;
	private String repassword;
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
		setContentView(R.layout.forgotpwd);
		
		returnBtn = (Button)findViewById(R.id.return_btn);
		confirmBtn = (Button)findViewById(R.id.confirm);
		forgot = (Button)findViewById(R.id.forgot);
		
		et_username = (EditText)findViewById(R.id.username);
		et_secpassword = (EditText)findViewById(R.id.secpassword);
		et_password = (EditText)findViewById(R.id.password);
		et_repassword = (EditText)findViewById(R.id.repassword);

		forgot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ForgotpwdActivity.this,Changepwdbynote.class);
				startActivity(intent);
				finish();
			}
		});

		returnBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ForgotpwdActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		confirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = et_username.getText().toString();
				secpassword = et_secpassword.getText().toString();
				password = et_password.getText().toString();
				repassword = et_repassword.getText().toString();
				
				if(userName.equals("") || secpassword.equals(""))
				{
					Toast.makeText(ForgotpwdActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
				}
				else if(!userName.matches("^[a-z0-9]{6,12}$")){
					Toast.makeText(ForgotpwdActivity.this, "用户名格式不正确", Toast.LENGTH_LONG).show();
				}
				else if(!secpassword.matches("^[0-9]{6,6}$")){
					Toast.makeText(ForgotpwdActivity.this, "二级密码格式不正确", Toast.LENGTH_LONG).show();
				}
				else if(!password.equals(repassword)){
					Toast.makeText(ForgotpwdActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
				}
				else if(!password.matches("^[a-z0-9]{6,12}$")){
					Toast.makeText(ForgotpwdActivity.this, "密码格式不正确", Toast.LENGTH_LONG).show();
				}
				else{
					String result = ForgotHandler.forgot(userName, secpassword, password);
					try{
						JSONObject json = new JSONObject(result);
						if(json.getString("message").equals("aaa")){
							Toast.makeText(ForgotpwdActivity.this, "用户名不存在", Toast.LENGTH_LONG).show();
						}else if(json.getString("message").equals("bbb")){
							Toast.makeText(ForgotpwdActivity.this, "二级密码错误", Toast.LENGTH_LONG).show();
						}else if(json.getString("message").equals("ccc")){
							Toast.makeText(ForgotpwdActivity.this, "修改成功", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(ForgotpwdActivity.this, LoginActivity.class);
							startActivity(intent);
							finish();
						}
					}catch (JSONException e) {
						e.printStackTrace();
					}	
				}
			}
		});
	}

}

