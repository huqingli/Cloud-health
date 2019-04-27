package com.moy.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomepageFragment extends Fragment {
	
	private ImageButton food;
	private ImageButton sport;
	private ImageButton result;
	private ImageButton assistance;
	private ScrollerLayout scroller1;
	private ScrollerLayout2 scroller2;

	private ImageView HomepageFoodOne;
	private ImageView HomepageFoodTwo;
	private ImageView HomepageFoodThree;
	private ImageView HomepageFoodFour;

	private ImageView HomepageSportOne;
	private ImageView HomepageSportTwo;
	private ImageView HomepageSportThree;
	private ImageView HomepageSportFour;

	public static HomepageFragment instance  = null;

	public  static TextView number1;
	public  static TextView number2;

	public HomepageFragment(){

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.homepage, container, false);
		//sport = (ImageButton)view.findViewById(R.id.sport); //运动的图
		//result = (ImageButton)view.findViewById(R.id.result); //链接的是测试数据的表
		//food = (ImageButton)view.findViewById(R.id.food);//食物
		//assistance=(ImageButton)view.findViewById(R.id.news);


		HomepageFoodOne = (ImageView)view.findViewById(R.id.HomepageFoodOne);
		HomepageFoodOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HomepageFoodOne.class);
				startActivity(intent);
			}
		});

		HomepageFoodTwo = (ImageView)view.findViewById(R.id.HomepageFoodTwo);
		HomepageFoodTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HomepageFoodTwo.class);
				startActivity(intent);
			}
		});

		HomepageFoodThree = (ImageView)view.findViewById(R.id.HomepageFoodThree);
		HomepageFoodThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HomepageFoodThree.class);
				startActivity(intent);
			}
		});

		HomepageFoodFour = (ImageView)view.findViewById(R.id.HomepageFoodFour);
		HomepageFoodFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HomepageFoodFour.class);
				startActivity(intent);
			}
		});

		HomepageSportOne = (ImageView)view.findViewById(R.id.HomepageSportOne);
		HomepageSportOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HomepageSportOne.class);
				startActivity(intent);
			}
		});

		HomepageSportTwo = (ImageView)view.findViewById(R.id.HomepageSportTwo);
		HomepageSportTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HomepageSportTwo.class);
				startActivity(intent);
			}
		});

		HomepageSportThree = (ImageView)view.findViewById(R.id.HomepageSportThree);
		HomepageSportThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HomepageSportThree.class);
				startActivity(intent);
			}
		});

		HomepageSportFour = (ImageView)view.findViewById(R.id.HomepageSportFour);
		HomepageSportFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HomepageSportFour.class);
				startActivity(intent);
			}
		});

		number1 = (TextView)view.findViewById(R.id.textView1) ;
		scroller1 = (ScrollerLayout)view.findViewById(R.id.scroller1);
		int a = scroller1.getChildCount();

		number2 = (TextView)view.findViewById(R.id.textView2) ;
		scroller2 = (ScrollerLayout2)view.findViewById(R.id.scroller2);
		int b = scroller2.getChildCount();

		String sAgeFormat = getResources().getString(R.string.childcout);
		sAgeFormat = String.format(sAgeFormat,1,a);
		number1.setText(sAgeFormat);
		sAgeFormat = String.format(sAgeFormat,1,b);
		number2.setText(sAgeFormat);

		/*result.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ResultAboutTestActivity.class);
				startActivity(intent);
			}
		});*/
		
		/*food.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), FoodActivity.class);
				startActivity(intent);
		
			}
		});*/
		
		/*sport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SportActivity.class);
				startActivity(intent);
			}
		});*/
		
		/*assistance.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), AssistanceActivity.class);
				startActivity(intent);
			}
		});*/
		return view;
	}

}