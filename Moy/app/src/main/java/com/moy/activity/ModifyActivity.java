package com.moy.activity;

import com.moy.handler.ModifyHandler;
import com.moy.synchonization.Synchronize;
import com.moy.util.MyApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyActivity extends Activity {
	
	private Button confirmBtn;
	private Button returnBtn;
	
	private EditText et_sex;
	private EditText et_age;
	private EditText et_height;
	private EditText et_weight;
	
	private String userName;
	private String phoneNum;
	private String sex;
	private String age;
	private String height;
	private String weight;
	private String password;
	private String merPhone;

	private Context mContext;
	private MyApplication application;
	private EditText et_username;
	private EditText et_merphone;


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
		setContentView(R.layout.modify);
		
		mContext = this;
		application = (MyApplication) getApplication();
		
		returnBtn = (Button)findViewById(R.id.return_btn);
		confirmBtn = (Button)findViewById(R.id.confirm);
		
		et_sex = (EditText)findViewById(R.id.sex);
		et_age = (EditText)findViewById(R.id.age);
		et_height = (EditText)findViewById(R.id.height);
		et_weight = (EditText)findViewById(R.id.weight);
		et_username = (EditText) findViewById(R.id.username);
		et_merphone = (EditText) findViewById(R.id.merphone);

		returnBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ModifyActivity.this.finish();
			}
		});
		
		confirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sex = et_sex.getText().toString();
				age = et_age.getText().toString();
				height = et_height.getText().toString();
				weight = et_weight.getText().toString();
				userName = et_username.getText().toString();
				merPhone = et_merphone.getText().toString();
				password = application.getUser().getPassword();
				phoneNum = application.getUser().getPhoneNum();
				if(sex.equals("")||age.equals("")||height.equals("")||weight.equals("")||userName.equals("")||merPhone.equals("")){
					Toast.makeText(ModifyActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
				}
				else if(!(sex != "男" && sex != "女")){
					Toast.makeText(ModifyActivity.this, "请输入正确的性别", Toast.LENGTH_LONG).show();
				}
				else if(!age.matches("^[0-9]{1,3}$")){
					Toast.makeText(ModifyActivity.this, "请输入正确的年龄", Toast.LENGTH_LONG).show();
				}
				else if(!height.matches("^[0-9]{1,3}$")){
					Toast.makeText(ModifyActivity.this, "请输入正确的身高", Toast.LENGTH_LONG).show();
				}
				else if(!weight.matches("^[0-9]{1,3}$")){
					Toast.makeText(ModifyActivity.this, "请输入正确的体重", Toast.LENGTH_LONG).show();
				}else if(!merPhone.matches("^[0-9]{1,3}$")&& merPhone.length()!=11){
					Toast.makeText(ModifyActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
				}
				else{
					if(sex.equals("男")){
						sex = "1";
					}else{
						sex = "0";
					}
					String result = ModifyHandler.modify(phoneNum, userName, sex, age, height, weight,merPhone);
					System.out.println("修改结果" +result);
					try {
						JSONObject json = new JSONObject(result);
						if (json.getString("message").equals("true")) {
							Toast.makeText(ModifyActivity.this, "修改成功", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(ModifyActivity.this, MenuActivity.class);
							Synchronize.Syn(phoneNum, password, mContext);
							startActivity(intent);
							ModifyActivity.this.finish();
						}else if (json.getString("message").equals("false")) {
							Toast.makeText(ModifyActivity.this, "修改失败", Toast.LENGTH_LONG).show();
						}
					} catch (Exception e){
						e.printStackTrace();
					}

                    }
				}
				
			
		});
		
	}

}
