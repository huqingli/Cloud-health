package com.moy.synchonization;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.moy.handler.LoginHandler;
import com.moy.pojo.UserPOJO;
import com.moy.synchonization.Syncfromcloud;
import com.moy.util.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;
/**
 *  
 *@Author Zlq
 *@Date 2018/11/28 21:14
 *@Description:  同步函数
 * 
 * */
public class Synchronize {
    private static String phoneNum;
    private static String password;
    private static Context mContext;
    private static MyApplication application;
    public static void Syn(String phoneNum,String password,Context mContext){
        String result = LoginHandler.login(phoneNum, password);
        try {
            JSONObject json = new JSONObject(result);
            boolean info = Syncfromcloud.Download(result, mContext);
            Log.i("LOGIN RESULT",result);
            UserPOJO user = new UserPOJO();
            user.setUserId(json.getInt("userId"));
            user.setUserName(json.getString("userName"));
            user.setPassword(json.getString("password"));
            user.setPhoneNum(json.getString("phonenum"));
            if((json.getString("sex").equals("1"))){
                user.setSex("男");
            }else if((json.getString("sex").equals("0"))){
                user.setSex("女");
            }
            user.setAge(json.getString("age"));
            user.setHeight(json.getString("height"));
            user.setWeight(json.getString("weight"));
            application.setUser(user);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
