package com.moy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moy.handler.CheckUserNameHandler;
import com.moy.handler.ModifyUsernameHandler;
import com.moy.synchonization.Synchronize;
import com.moy.util.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyusernameActivity extends AppCompatActivity {

    private Button confirmBtn;
    private Button returnBtn;

    private EditText et_userName;

    private String userName;
    private String phoneNum;
    private int userid;
    private String password;
    private Context mContext;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState){
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
        setContentView(R.layout.modifyusername);


        application = (MyApplication) getApplication();

        returnBtn = (Button)findViewById(R.id.return_btn_username);
        confirmBtn = (Button)findViewById(R.id.confirm_username);

        et_userName = (EditText)findViewById(R.id.username_change);
        mContext = this;


        returnBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ModifyusernameActivity.this.finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                userName = et_userName.getText().toString();
                phoneNum = application.getUser().getPhoneNum();
                password = application.getUser().getPassword();
                userid = application.getUser().getUserId();
                if(userName.equals("")){
                    Toast.makeText(ModifyusernameActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    String results = CheckUserNameHandler.CheckUserName(userName,userid);
                    try {
                        JSONObject json = new JSONObject(results);
                        if (json.getString("message").equals("false")) {
                            String result = ModifyUsernameHandler.modifyUsername(userName, phoneNum);
                            Intent intent = new Intent(ModifyusernameActivity.this, MenuActivity.class);
                            Synchronize.Syn(phoneNum, password, mContext);
                            startActivity(intent);
                            Toast.makeText(ModifyusernameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            ModifyusernameActivity.this.finish();
                        }
                        else {
                            Toast.makeText(ModifyusernameActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

        });

    }

}