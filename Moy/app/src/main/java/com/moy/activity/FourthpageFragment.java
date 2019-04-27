package com.moy.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moy.handler.CheckSecpwdHandler;
import com.moy.handler.ModifySecpwdHandler;
import com.moy.util.MyApplication;

public class FourthpageFragment extends Fragment{
	private MyApplication application;
	
	private LinearLayout info;
	private LinearLayout repwd;
	private LinearLayout modifyname;
	private LinearLayout modifysecpwd;
	
	private String id;
	private String sex;
	private String age;
	private String height;
	private String weight;
	
	private TextView tv_id;
	private TextView tv_sex;
	private TextView tv_age;
	private TextView tv_height;
	private TextView tv_weight;
	
	private Button exit;
	
	public FourthpageFragment(){
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fourthpage, container, false);

		application = (MyApplication) getActivity().getApplication();
		info = (LinearLayout)view.findViewById(R.id.info);
		repwd = (LinearLayout)view.findViewById(R.id.repwd);
		modifyname = (LinearLayout)view.findViewById(R.id.modify_name);
		exit = (Button)view.findViewById(R.id.exit);
		modifysecpwd = (LinearLayout)view.findViewById(R.id.secpwd);


		tv_id = (TextView)view.findViewById(R.id.id);
		tv_sex = (TextView)view.findViewById(R.id.sex);
		tv_age = (TextView)view.findViewById(R.id.age);
		tv_height = (TextView)view.findViewById(R.id.height);
		tv_weight = (TextView)view.findViewById(R.id.weight);
		if (application.getUser()!=null) {
			if (!application.getUser().getUserName().equals(""))
				tv_id.setText(application.getUser().getUserName());
			if (!application.getUser().getSex().equals(""))
				tv_sex.setText(application.getUser().getSex());
			if (!application.getUser().getAge().equals(""))
				tv_age.setText(application.getUser().getAge());
			if (!application.getUser().getHeight().equals(""))
				tv_height.setText(application.getUser().getHeight() + "cm");
			if (!application.getUser().getWeight().equals(""))
				tv_weight.setText(application.getUser().getWeight() + "kg");
		}
		
		info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				application.setChk(1);
				Intent intent = new Intent(getActivity(), ConfirmSecpasswordActivity.class);
				startActivity(intent);
			}
		});

		repwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				application.setChk(2);
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ConfirmSecpasswordActivity.class);
				startActivity(intent);
			}
		});

		modifyname.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ModifyusernameActivity.class);
				startActivity(intent);
			}
		});

		modifysecpwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				application.setChk(2);
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ModifysecpwdActivity.class);
				startActivity(intent);
			}
		});

		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				application.userLoginOut();
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				getActivity().finish();
				startActivity(intent);
			}
		});

		
		return view;
	}

}
