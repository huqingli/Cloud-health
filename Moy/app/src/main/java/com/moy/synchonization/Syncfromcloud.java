package com.moy.synchonization;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.Toast;

import com.moy.activity.LoginActivity;
import com.moy.database.UserDao;
import com.moy.pojo.UserPOJO;
import com.moy.util.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import static com.moy.util.MyApplication.setUser;

/**
 * Created by Administrator on 2017/10/7.
 */

public class Syncfromcloud {


    public static boolean Download(String jsonresult,Context mContext){
        boolean result = false;
        try {
            JSONObject json = new JSONObject(jsonresult);
            UserPOJO user = new UserPOJO();
            ContentValues values = new ContentValues();
            user.setUserId(json.getInt("userId"));
            if(!json.getString("userName").equals("")) {
                values.put("username",user.getUserName());
                user.setUserName(json.getString("userName"));
            }
            user.setPassword(json.getString("password"));
            user.setPhoneNum(json.getString("phonenum"));
            if((json.getString("sex").equals("1"))){
                user.setSex("男");
            }else if((json.getString("sex").equals("0"))){
                user.setSex("女");
            }else{
                user.setSex("");
            }

            if(!json.getString("age").equals("")) {
                user.setAge(json.getString("age"));
                values.put("age", user.getAge());
            }
            if(!json.getString("height").equals("")) {
                user.setHeight(json.getString("height"));
                values.put("height",user.getHeight());
            }
            if(!json.getString("weight").equals("")) {
                user.setWeight(json.getString("weight"));
                values.put("weight", user.getWeight());
            }



            values.put("password",user.getPassword());
            values.put("phonenum",user.getPhoneNum());
            if(!user.getSex().equals(""))
                values.put("sex",user.getSex());



            UserDao userDao = new UserDao(mContext);
            Cursor cursor = userDao.quard("users",new String[]{"phonenum"},user.getPhoneNum());
            if(cursor != null && cursor.getCount()>0){
                long UpdateResult = userDao.update("users",values,user.getPhoneNum());
                if(UpdateResult != 1){
                    Toast.makeText(mContext, "错误代码:"+UpdateResult, Toast.LENGTH_SHORT).show();
                    cursor.close();
                }else{
                    cursor.close();
                    result = true;
                }
            }else{
                boolean AddResult = userDao.add("users",values);
                if(!AddResult){
                    cursor.close();
                    Toast.makeText(mContext, "错误代码:0", Toast.LENGTH_SHORT).show();
                }else{
                    cursor.close();
                   result = true;
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    public static boolean Upload(){
        boolean result = false;
        return result;
    }


}
