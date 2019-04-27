package com.moy.fancychart.demo;

import com.moy.activity.R;
import com.moy.fancychart.FancyChart;
import com.moy.fancychart.FancyChartPointListener;
import com.moy.fancychart.data.ChartData;
import com.moy.fancychart.data.Point;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;



public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fancychart);
		
		FancyChart chart = (FancyChart) findViewById(R.id.chart);
		chart.setOnPointClickListener(new FancyChartPointListener() {
			
			@Override
			public void onClick(Point point) {
				Toast.makeText(MainActivity.this, "I clicked point " + point, Toast.LENGTH_LONG).show();
			}
		});
		
		ChartData data = new ChartData(ChartData.LINE_COLOR_BLUE);
		int[] yValues = new int[]{153, 122, 138, 118, 150, 130, 133, 122};
		for(int i = 0; i <8; i++) {
			data.addPoint(i, yValues[i]);
			data.addXValue(i, (i+1)+"次");
		}
		chart.addData(data);
		
		/*ChartData data2 = new ChartData(ChartData.LINE_COLOR_RED);
		int[] yValues2 = new int[]{0, 5, 9, 23, 15, 35, 45, 50, 41, 45, 32, 24};
		for(int i = 1; i <= 8; i++) {
			data.addPoint(i, yValues[i-1]);
			data.addXValue(i, "第"+i+"次");
		}*/
	}

}