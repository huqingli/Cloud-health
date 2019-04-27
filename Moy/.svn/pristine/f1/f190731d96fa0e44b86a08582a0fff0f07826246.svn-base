package com.moy.activity;

import com.moy.util.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends Activity {
	
	private Button returnBtn;
	private MyApplication application;
	
	private TextView current_rate;
	private TextView current_state;
	private TextView advice;
	
	private ImageView state;
	
	private int rate;
	
	private String str_rate;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result);
		
		Intent intent = getIntent();
		str_rate = intent.getStringExtra("rate");
		application = (MyApplication)getApplication();
		
		returnBtn = (Button)findViewById(R.id.return_home);
		current_rate = (TextView)findViewById(R.id.current_rate);
		current_state = (TextView)findViewById(R.id.current_state);
		advice = (TextView)findViewById(R.id.advice);
		state = (ImageView)findViewById(R.id.health_state);
		
		current_rate.setText("您当前的心率为：" + str_rate);
		
		rate = Integer.parseInt(str_rate);
		
		application.setRate(rate);
		if(rate > 160 || rate < 40){
			current_state.setText("您当前的健康状况为：较差");
			advice.setText("建议您及时到医院进行详细检查!");
			state.setImageResource(R.drawable.homepage_state_bad);
		}else if((rate >= 40&& rate<=60) || (rate >= 100 && rate <= 160)){
			current_state.setText("您当前的健康状况为：一般");
			advice.setText("建议您时刻关注自己的身体情况！");
			state.setImageResource(R.drawable.homepage_state_normal);
		}else if(rate > 60&& rate<100){
			current_state.setText("您当前的健康状况为：良好");
			advice.setText("请继续保持！");
			state.setImageResource(R.drawable.homepage_state_good);
		}
		
		returnBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
				startActivity(intent);
			}
		});
	}
	

}
