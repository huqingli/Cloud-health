package com.moy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;
import com.moy.handler.ForgotHandler;
import com.moy.handler.LoginHandler;
import com.moy.handler.RegisterHandler;
import com.moy.synchonization.Syncfromcloud;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.SMSSDK;

import static com.moy.util.SendMessageUtil.getAppKey;
import static com.moy.util.SendMessageUtil.getAppSecret;

/**
 * Created by SDM on 2018/11/19.
 */

public class Changepwdbynote  extends Activity{

    private Button getcode;
    private Button register;
    private Button returnBtn;

    private EditText phoneNumber;
    private EditText newpwd;
    private EditText verifypwd;
    private EditText inputcode;

    private String CountryCode;



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
        setContentView(R.layout.changepwdbynote);
        MobSDK.init(Changepwdbynote.this, getAppKey(), getAppSecret());

        getcode = (Button)findViewById(R.id.cpbn_getcode);
        register = (Button)findViewById(R.id.cpbn_register);
        returnBtn = (Button) findViewById(R.id.bt_return2);

        phoneNumber = (EditText)findViewById(R.id.cpbn_phonenumber);
        newpwd = (EditText)findViewById(R.id.cpbn_newpwd);
        verifypwd = (EditText)findViewById(R.id.cpbn_verifypwd);
        inputcode = (EditText)findViewById(R.id.cpbn_inputcode);

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("中国(+86)");
        CountryCode = m.replaceAll("").trim();

        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pn = phoneNumber.getText().toString();
                String np = newpwd.getText().toString();
                String vp = verifypwd.getText().toString();
                if(pn.equals("") || np.equals("") || vp.equals("")){
                    Toast.makeText(Changepwdbynote.this,"请输入完整信息！",Toast.LENGTH_LONG).show();
                }else if(!np.equals(vp)){
                    Toast.makeText(Changepwdbynote.this,"输入的密码不一致！",Toast.LENGTH_LONG).show();
                }else {
                    String PhoneNum = phoneNumber.getText().toString().trim();
                    String result = RegisterHandler.checkphonenum(PhoneNum);
                    try {
                        Intent intent;
                        JSONObject json = new JSONObject(result);
                        if (json.getString("message").equals("false")) {
                            SMSSDK.getVerificationCode(CountryCode, pn);
                            MyCountdownTimer myCountdownTimer = new MyCountdownTimer(60000, 1000);
                            myCountdownTimer.start();
                        }else if(json.getString("message").equals("true")){
                            Toast.makeText(Changepwdbynote.this, "该手机号暂未注册，请先注册", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Changepwdbynote.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        returnBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Changepwdbynote.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pn = phoneNumber.getText().toString();
                String np = newpwd.getText().toString();
                String code = inputcode.getText().toString();
                String result = ForgotHandler.changePwdByNote(pn, np, code);
                try {
                    JSONObject json = new JSONObject(result);
                    //JSON一种轻量级的数据交换格式
                    if (json.getString("message").equals("fail")) {
                        Toast.makeText(Changepwdbynote.this, "验证码不匹配！", Toast.LENGTH_LONG).show();
                    } else if (json.getString("message").equals("success")) {
                        Toast.makeText(Changepwdbynote.this, "密码修改成功！", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private class MyCountdownTimer extends CountDownTimer {
        public MyCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            getcode.setClickable(false);
            getcode.setEnabled(false);
            getcode.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));
            getcode.setText(l / 1000 + "秒后重新获取");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            getcode.setText("获取验证码");
            getcode.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_getcode));
            //设置可点击
            getcode.setEnabled(true);
            getcode.setClickable(true);
        }
    }



}
