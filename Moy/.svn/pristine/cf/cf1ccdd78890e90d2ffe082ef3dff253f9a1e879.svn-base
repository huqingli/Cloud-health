package com.moy.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.moy.handler.AddinfoHandler;
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

public class AddinfoActivity extends Activity {
	
	private Button confirmBtn;
	private Button returnBtn;
	
	private EditText et_sex;
	private EditText et_age;
	private EditText et_height;
	private EditText et_weight;
	private EditText et_heartdisease;
	
	private String userName;
	private String sex;
	private String age;
	private String height;
	private String weight;
	private String heartdisease;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		///??Android2.2????????????????
				//????ò????Android4.0
				//???????????
				 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		         .detectDiskReads()   
		         .detectDiskWrites()   
		         .detectNetwork()   // or .detectAll() for all detectable problems   
		         .penaltyLog()   
		         .build());   
				//??????????????
				  StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()   
				         .detectLeakedSqlLiteObjects()   
				         //.detectLeakedClosableObjects()   
				         .penaltyLog()   
				         .penaltyDeath()   
				         .build());
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addinfo);
		
		Intent intent =  getIntent();
		userName = intent.getStringExtra("userName");
		
		returnBtn = (Button)findViewById(R.id.return_btn);
		confirmBtn = (Button)findViewById(R.id.confirm);
		
		/*et_sex = (EditText)findViewById(R.id.sex);
		et_age = (EditText)findViewById(R.id.age);
		et_height = (EditText)findViewById(R.id.height);
		et_weight = (EditText)findViewById(R.id.weight);*/
		et_heartdisease = (EditText)findViewById(R.id.heartdisease);
		
		
		returnBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddinfoActivity.this.finish();
			}
		});
		
		confirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*sex = et_sex.getText().toString();
				age = et_age.getText().toString();
				height = et_height.getText().toString();
				weight = et_weight.getText().toString();*/
				heartdisease=et_heartdisease.getText().toString();
				/*if(sex.equals("")||age.equals("")||height.equals("")||weight.equals("")){
					Toast.makeText(AddinfoActivity.this, "?????????", Toast.LENGTH_LONG).show();
				}
				else if(!(sex != "??" && sex != "?")){
					Toast.makeText(AddinfoActivity.this, "??????????????", Toast.LENGTH_LONG).show();
				}
				else if(!age.matches("^[0-9]{1,3}$")){
					Toast.makeText(AddinfoActivity.this, "???????????????", Toast.LENGTH_LONG).show();
				}
				else if(!height.matches("^[0-9]{1,3}$")){
					Toast.makeText(AddinfoActivity.this, "??????????????", Toast.LENGTH_LONG).show();
				}
				else if(!weight.matches("^[0-9]{1,3}$")){
					Toast.makeText(AddinfoActivity.this, "???????????????", Toast.LENGTH_LONG).show();
				}
				else{
					if(sex.equals("??")){
						sex = "1";
					}else{
						sex = "0";
					}
					*/
					
					/*String result = AddinfoHandler.addinfo(userName, sex, age, height, weight);*/
						String result=AddinfoHandler.addinfo(userName,heartdisease);
					
							Toast.makeText(AddinfoActivity.this, "添加成功", Toast.LENGTH_LONG).show();
						
						Intent intent = new Intent(AddinfoActivity.this, LoginActivity.class);
						startActivity(intent);
						AddinfoActivity.this.finish();
						}
					/*}*/
				
			
		});
		
	}

}
