package com.moy.activity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moy.fancychart.FancyChart;
import com.moy.fancychart.FancyChartPointListener;
import com.moy.fancychart.data.ChartData;
import com.moy.fancychart.data.Point;
import com.moy.handler.GetdataHandler;
import com.moy.util.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;



public class FancychartActivity extends Activity {
	
	private String str="";
	private JSONArray array;
    private int[] yValues;
    private MyApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		setContentView(R.layout.fancychart);
		FancyChart chart = (FancyChart) findViewById(R.id.chart);
		chart.setOnPointClickListener(new FancyChartPointListener() {
			
			@Override
			public void onClick(Point point) {
				Toast.makeText(FancychartActivity.this, "I clicked point " + point, Toast.LENGTH_LONG).show();
				ResultAboutTestActivity.rate=point.y;
				Intent intent = new Intent(FancychartActivity.this, ResultAboutTestActivity.class);
				startActivity(intent);
			}
		});
		
		 str = GetdataHandler.getdata(application.getUser().getUserName()); 
		 
		   try{
			   array = new JSONArray(str);
			   if(array.length()==0)
			   {
				   
				   ChartData data2 = new ChartData(ChartData.LINE_COLOR_RED);
					int[] yValues2 = new int[]{0,0, 0, 0, 0, 0,0};
					for(int i = 0; i <7; i++) {
						data2.addPoint(i, yValues2[i]);
						data2.addXValue(i, "第"+i+"次");
					}
					chart.addData(data2);
					Toast.makeText(FancychartActivity.this, "请先去测试！",Toast.LENGTH_LONG).show();
			   }
			   else
			   {
				   int i;
				   yValues = new int []{0,0, 0, 0, 0, 0,0};
				   for(i=0;i<array.length();i++)
				   {	
					   JSONObject json = array.getJSONObject(i);
						yValues[array.length()-1-i] = Integer.parseInt(json.getString("heartrate"));
					}
				
			   }

		} catch (JSONException e) {
			e.printStackTrace();
		}
	   if(array.length()==0)
			   ;
		 else
		{
			 ChartData data = new ChartData(ChartData.LINE_COLOR_BLUE);
		    for(int i = 0; i <7; i++) 
		    {
				data.addPoint(i, yValues[i]);
				data.addXValue(i, "第"+(i+1)+"次");
		     }
		    chart.addData(data);
		//第二次的数据
			/*ChartData data2 = new ChartData(ChartData.LINE_COLOR_RED);
			int[] yValues2 = new int[]{100,115, 120, 105, 115, 135,120};
			for(int i = 0; i <7; i++) 
			{
				data2.addPoint(i, yValues2[i]);
				data2.addXValue(i, "第"+(i+1)+"次");
		    }
		    chart.addData(data2);*/
		
	    }
	}
	
}