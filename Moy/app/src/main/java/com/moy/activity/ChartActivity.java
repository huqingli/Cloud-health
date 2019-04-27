package com.moy.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moy.handler.GetdataHandler;

import com.moy.util.ChartView;
import com.moy.util.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChartActivity extends Activity {
	

	private String str;
	private JSONArray array;
    private int[] data;
	
	private MyApplication application;
	
	private Button re;
	
	@Override
	   public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.chartview);
	  
	   application = (MyApplication)getApplication();
	   
	   str = GetdataHandler.getdata(application.getUser().getUserName()); 
	   try{
		   array = new JSONArray(str);
		   data = new int [array.length()];
		   for(int i=0;i<array.length();i++){		
				JSONObject json = array.getJSONObject(i);
				data[array.length()-1-i] = Integer.parseInt(json.getString("heartrate"));
						/*json.getInt("rate");*/

		}

	} catch (JSONException e) {
		e.printStackTrace();
	}
	   //int data[]={90,100,130,95,89,120,80};
	   
		   
		   ChartView myView = (ChartView) this.findViewById(R.id.myView);
		   System.out.println("1");
		    myView.SetInfo(new String[] { "第1次", "第2次", "第3次", "第4次", "第5次",
		     "第6次", "第7次" }, // X轴刻度
		      new String[] { "", "40", "80", "120", "160", "200" }, // Y轴刻度
		     data, // 数据
		       "心率");
		 

	   
	    
	    
	    re = (Button)findViewById(R.id.re);
	    re.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChartActivity.this, MenuActivity.class);
				startActivity(intent);
			}
		});
	    
	   }

}
