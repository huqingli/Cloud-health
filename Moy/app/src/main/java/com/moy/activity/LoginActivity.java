package com.moy.activity;


import java.io.IOException;
import java.util.Map;
import java.util.Set;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import com.moy.database.MySqliteOpenHelper;
import com.moy.database.UserDao;
import com.moy.handler.LoginHandler;
import com.moy.handler.RegisterHandler;
import com.moy.pojo.UserPOJO;
import com.moy.synchonization.Syncfromcloud;
import com.moy.util.ConnectUtil;
import com.moy.util.MyApplication;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static com.moy.util.MyApplication.setUser;

public class LoginActivity extends Activity implements OnClickListener {

    private Context mContext;

    private MyApplication application;
    SQLiteDatabase db;

    private Button loginBtn;
    private Button registerBtn;
    private Button forgotBtn;
    private Button otherway;
    private EditText et_username;
    private EditText et_password;
    private String userName;
    private String password;
    private CheckBox cb_save;

    SharedPreferences sharedPreference;
    SharedPreferences.Editor myEditor;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        mContext = this;
        et_username = (EditText) super.findViewById(R.id.username);
        et_password = (EditText) super.findViewById(R.id.password);
        loginBtn = (Button) super.findViewById(R.id.login);
        registerBtn = (Button) super.findViewById(R.id.register);
        forgotBtn = (Button) super.findViewById(R.id.forgot);
        otherway = (Button) findViewById(R.id.otherway);
        cb_save = (CheckBox) findViewById(R.id.boxsave);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        forgotBtn.setOnClickListener(this);
        otherway.setOnClickListener(this);
        application = (MyApplication)getApplication();

         sharedPreference = getSharedPreferences("user",Context.MODE_PRIVATE);

         et_username.setText(sharedPreference.getString("username",""));
         et_password.setText(sharedPreference.getString("password",""));
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                break;
            case R.id.forgot:
                intent = new Intent(LoginActivity.this, ForgotpwdActivity.class);
                LoginActivity.this.startActivity(intent);
                break;
            case R.id.otherway:
                intent = new Intent(LoginActivity.this, LoginPopupActivity.class);
                startActivity(intent);

                break;
            default:
                return;
        }

    }

    private void login() {

        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MenuActivity.class);
        userName = LoginActivity.this.et_username.getText().toString();
        password = LoginActivity.this.et_password.getText().toString();
        if (userName.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, "用户名或密码不能为空！", Toast.LENGTH_LONG).show();
        } else {
            String result = LoginHandler.login(userName, password);
            try {

                JSONObject json = new JSONObject(result);
                //JSON一种轻量级的数据交换格式
                if (json.getString("message").equals("fail")) {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                    //Toast.LENGTH_LONG).show()表示提示框显示时间
                } else if (json.getString("message").equals("success")) {

                    boolean info = Syncfromcloud.Download(result, mContext);
                    Log.i("LOGIN RESULT",result);
                    UserPOJO user = new UserPOJO();
                    user.setUserId(json.getInt("userId"));
                    if(!json.getString("userName").equals(""))
                        user.setUserName(json.getString("userName"));
                    user.setPassword(json.getString("password"));
                    user.setPhoneNum(json.getString("phonenum"));
                    if((json.getString("sex").equals("1"))){
                        user.setSex("男");
                    }else if((json.getString("sex").equals("0"))){
                        user.setSex("女");
                    }
                    if(!json.getString("age").equals(""))
                        user.setAge(json.getString("age"));
                    if(!json.getString("height").equals(""))
                        user.setHeight(json.getString("height"));
                    if(!json.getString("weight").equals(""))
                        user.setWeight(json.getString("weight"));
                    application.setUser(user);
                    Log.i("LOGIN RESULT","-------------");
                    if (info) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        if(cb_save.isChecked()) {
                            myEditor = sharedPreference.edit();
                            myEditor.putString("username",userName);
                            myEditor.putString("password",password);
                            myEditor.commit();
                        }
                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

	