package com.moy.activity;





import com.moy.util.ChartView;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class ThirdpageFragment extends Fragment {

	private Button qiujiu;
	private Button baogao;
	private Button yinshi;
	private Button yundong;

	public ThirdpageFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.thirdpage, container, false);
		qiujiu = (Button)view.findViewById(R.id.bt_thirdpage_qiujiu);
		baogao = (Button)view.findViewById(R.id.bt_thirdpage_baogao);
		yinshi = (Button)view.findViewById(R.id.bt_thirdpage_yinshi);
		yundong = (Button)view.findViewById(R.id.bt_thirdpage_yundong);
		qiujiu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Uri uri = Uri.parse("http://www.xmsmjk.com/urponline/");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		baogao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ResultAboutTestActivity.class);
				startActivity(intent);
			}
		});
		yinshi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),FoodActivity.class);
				startActivity(intent);
			}
		});
		yundong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),SportActivity.class);
				startActivity(intent);
			}
		});

		return view;

	}



}