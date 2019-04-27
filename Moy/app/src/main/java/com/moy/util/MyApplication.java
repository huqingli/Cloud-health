package com.moy.util;

import com.moy.pojo.UserPOJO;

import android.app.Application;

public class MyApplication extends Application {
	
	private static UserPOJO user;
	private static int rate;
	public static int chk;

	public static int getRate() {
		return rate;
	}

	public static void setRate(int rate) {
		MyApplication.rate = rate;
	}

	public static UserPOJO getUser() {

		return user;
	}

	public static void setUser(UserPOJO user) {
		MyApplication.user = user;
	}
	
	public void userLoginOut(){
		user = new UserPOJO();
	}

	public static int getChk(){
		return chk;
	}
	public static void setChk(int chk){
		MyApplication.chk = chk;
	}

}
