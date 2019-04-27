package com.moy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.moy.handler.LoginHandler;
import com.moy.handler.RegisterHandler;
import com.moy.pojo.UserPOJO;
import com.moy.synchonization.Syncfromcloud;
import com.moy.util.MyApplication;
import com.moy.util.UploadUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.moy.util.SendMessageUtil.getAppKey;
import static com.moy.util.SendMessageUtil.getAppSecret;

/**
 * Created by Administrator on 2017/10/1.
 */

public class ValidateActivity extends Activity implements View.OnClickListener {

    private Context mContext;
    private MyApplication application;
    private String appKey;
    private String appSecret;

    private EditText et_code;
    private Button bt_resend;
    private Button bt_nextstep;
    private TextView tv_phonenum;
    private static String countryCode;
    private static String phoneNum;
    private static String password;
    private String code;

    EventHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ///在Android2.2以后必须添加以下代码
        //本应用采用的Android4.0
        //设置线程的策略
        /*
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
                */
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        appKey = getAppKey();
        appSecret = getAppSecret();
        MobSDK.init(this, appKey, appSecret);
        mContext = this;
        setContentView(R.layout.validatepage);
        Intent intent = getIntent();
        countryCode = intent.getStringExtra("countryCode");
        phoneNum = intent.getStringExtra("phoneNum");
        password = intent.getStringExtra("password");


        et_code = (EditText) findViewById(R.id.et_Code);
        bt_resend = (Button) findViewById(R.id.bt_resend);
        bt_nextstep = (Button) findViewById(R.id.bt_nextstep);
        tv_phonenum = (TextView) findViewById(R.id.tv_Phonenum);

        tv_phonenum.setText("+" + countryCode + " " + phoneNum);


        bt_resend.setOnClickListener(this);
        bt_nextstep.setOnClickListener(this);

        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 == 0) {
                    bt_nextstep.setClickable(false);
                } else {
                    bt_nextstep.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        handler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        sign_up();

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "验证码已发送", Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //获取支持的国家代码
                    }
                } else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext,"提交错误信息",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        SMSSDK.registerEventHandler(handler);

        send();
    }

    private void send(){
        SMSSDK.getVerificationCode(countryCode, phoneNum);
        new MyCountdownTimer(60000, 1000).start();
    }

    private void sign_up() {
        String result = RegisterHandler.register(mContext,phoneNum, password);
        Intent intent = new Intent();
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("message").equals("false")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "注册失败", Toast.LENGTH_LONG).show();
                    }
                });

                return;
            } else if (json.getString("message").equals("true")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "注册成功", Toast.LENGTH_LONG).show();
                    }
                });
                RegisterHandler.insertR(phoneNum);
                String LoginResult = LoginHandler.login(phoneNum, password);
                JSONObject LoginJson = new JSONObject(LoginResult);
                if (LoginJson.getString("message").equals("success")) {
                    boolean SyncResult = Syncfromcloud.Download(LoginResult, mContext);
                    UserPOJO user = new UserPOJO();
                    user.setUserId(LoginJson.getInt("userId"));
                    if(!LoginJson.getString("userName").equals(""))
                        user.setUserName(LoginJson.getString("userName"));
                    user.setPassword(LoginJson.getString("password"));
                    user.setPhoneNum(LoginJson.getString("phonenum"));
                    if((LoginJson.getString("sex").equals("1"))){
                        user.setSex("男");
                    }else if((LoginJson.getString("sex").equals("0"))){
                        user.setSex("女");
                    }
                    if(!LoginJson.getString("age").equals(""))
                        user.setAge(LoginJson.getString("age"));
                    if(!LoginJson.getString("height").equals(""))
                        user.setHeight(LoginJson.getString("height"));
                    if(!LoginJson.getString("weight").equals(""))
                        user.setWeight(LoginJson.getString("weight"));
                    application.setUser(user);
                    if (SyncResult) {
                        intent.setClass(this, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        intent.setClass(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_resend:
                SMSSDK.getVerificationCode(countryCode, phoneNum);
                new MyCountdownTimer(60000, 1000).start();
                break;
            case R.id.bt_nextstep:
                code = et_code.getText().toString().trim();
                SMSSDK.submitVerificationCode(countryCode, phoneNum, code);
                break;
            //case R.id.
            default:
                return;
        }
    }


    private class MyCountdownTimer extends CountDownTimer {
        public MyCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            bt_resend.setClickable(false);
            bt_resend.setEnabled(false);
            bt_resend.setText("接收短信大约需要" + l / 1000 + "秒钟");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            bt_resend.setText("重新获取验证码");
            //设置可点击
            bt_resend.setEnabled(true);
            bt_resend.setClickable(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(handler);
    }
}
