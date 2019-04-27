package com.moy.activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moy.handler.CheckSecpwdHandler;
import com.moy.handler.LoginHandler;
import com.moy.handler.ModifyUsernameHandler;
import com.moy.util.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *@Author Zlq
 *@Date 2018/11/29 19:32
 *@Description: 验证二级密码
 *
 * */
public class ConfirmSecpasswordActivity extends AppCompatActivity {

    private Button returnBtn;
    private Button confirmBtn;
    private EditText etSecpwd;
    private MyApplication application;

    private String phoneNum;
    private String Secpassword;

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
        setContentView(R.layout.activity_confirm_secpassword);


        returnBtn = (Button)findViewById(R.id.return_btn_secpwd);
        confirmBtn = (Button)findViewById(R.id.confirm_secpwd);
        etSecpwd = (EditText)findViewById(R.id.et_secpwd);




        returnBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ConfirmSecpasswordActivity.this.finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ConfirmSecpasswordActivity.this,ModifyActivity.class);
                Intent intent2 = new Intent(ConfirmSecpasswordActivity.this,ResetpwdActivity.class);
                // TODO Auto-generated method stub
                switch (application.getChk()) {
                    case 1: check(intent1); break;
                    case 2: check(intent2); break;
                }
//                if (application.getChk() == 1){
//                    check(intent1);
//                }
//                if (application.getChk() == 2){
//                    check(intent2);
//                }
            }
        });
    }
    public void check(Intent intent){

        Secpassword = ConfirmSecpasswordActivity.this.etSecpwd.getText().toString();
        phoneNum = ConfirmSecpasswordActivity.this.application.getUser().getPhoneNum();
        if (Secpassword.equals("")) {
            Toast.makeText(ConfirmSecpasswordActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
        }
        else {
            String result = CheckSecpwdHandler.CheckSecpwd(phoneNum, Secpassword);
            try {
                JSONObject json = new JSONObject(result);
                //JSON一种轻量级的数据交换格式
                if (json.getString("message").equals("true")) {
                    Toast.makeText(ConfirmSecpasswordActivity.this, "验证成功", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    ConfirmSecpasswordActivity.this.finish();
                }
                else {
                    Toast.makeText(ConfirmSecpasswordActivity.this, "二级密码错误", Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
