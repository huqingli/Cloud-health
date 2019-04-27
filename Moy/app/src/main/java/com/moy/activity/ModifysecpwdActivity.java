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

import com.moy.handler.ModifySecpwdHandler;
import com.moy.synchonization.Synchronize;
import com.moy.util.MyApplication;

/**
 *
 *@Author Zlq
 *@Date 2018/11/30 17:41
 *@Description:
 *
 * */
public class ModifysecpwdActivity extends AppCompatActivity {
    private Button retuenbtn;
    private Button confirmbtn;
    private EditText et_secpwd;
    private EditText et_phonenum;

    private String phoneNum;
    private String phoneNum_chk;
    private String secpwd;
    private String password;
    private Context mContext;

    private MyApplication application;
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
        setContentView(R.layout.modifysecpwd);

        mContext = this;
        retuenbtn = (Button)findViewById(R.id.return_btn_modsecpwd);
        confirmbtn = (Button)findViewById(R.id.confirm_modsecpwd);
        et_phonenum = (EditText)findViewById(R.id.chk_phone);
        et_secpwd = (EditText)findViewById(R.id.secpwd_change);

        retuenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifysecpwdActivity.this.finish();
            }
        });
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(ModifysecpwdActivity.this,MenuActivity.class);
            @Override
            public void onClick(View view) {
                phoneNum_chk = et_phonenum.getText().toString();
                secpwd = et_secpwd.getText().toString();
                phoneNum = application.getUser().getPhoneNum();
                password = application.getUser().getPassword();
                if (phoneNum_chk.equals("")||secpwd.equals("")){
                    Toast.makeText(ModifysecpwdActivity.this,"输入不能为空",Toast.LENGTH_LONG).show();
                }
                else if (!phoneNum.equals(phoneNum_chk)){
                    Toast.makeText(ModifysecpwdActivity.this,"手机号错误",Toast.LENGTH_LONG).show();
                }
                else {
                    String result = ModifySecpwdHandler.modifysecpwd(phoneNum,secpwd);
                    Toast.makeText(ModifysecpwdActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    Synchronize.Syn(phoneNum,password,mContext);
                    startActivity(intent);
                    ModifysecpwdActivity.this.finish();
                }
            }
        });
    }
}
