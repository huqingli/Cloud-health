package com.moy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moy.handler.RegisterHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2017/10/4.
 */

public class LoginMessageStep1 extends Activity implements View.OnClickListener{

    private Button bt_next;
    private EditText et_phonenum;
    private Button bt_return;
    private Button bt_clear;
    private Button bt_choosecountrycode;
    private TextView tv_countrycode;
    private View line_2;

    private String CountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_message_step1);

        bt_next = (Button)findViewById(R.id.bt_login_message_step1_next);
        bt_choosecountrycode = (Button)findViewById(R.id.bt_login_message_step1_choosecountrycode);
        bt_clear = (Button)findViewById(R.id.bt_login_message_step1_clear);
        bt_return = (Button)findViewById(R.id.bt_login_message_step1_return);
        et_phonenum = (EditText)findViewById(R.id.et_login_message_step1_phonenum);
        tv_countrycode = (TextView)findViewById(R.id.tv_login_message_step1_countrycode);
        line_2 = findViewById(R.id.line_login_message_step1_line2);

        bt_next.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
        bt_return.setOnClickListener(this);
        bt_choosecountrycode.setOnClickListener(this);

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(tv_countrycode.getText().toString());
        CountryCode = m.replaceAll("").trim();

        final Drawable onFocus = this.getResources().getDrawable(R.drawable.light_blue);
        final Drawable noFocus = this.getResources().getDrawable(R.drawable.gray);

        bt_next.setEnabled(false);
        bt_clear.setVisibility(View.INVISIBLE);

        et_phonenum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    line_2.setBackgroundDrawable(onFocus);
                }
                else{
                    line_2.setBackgroundDrawable(noFocus);
                }
            }
        });

        et_phonenum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()==0){
                    bt_next.setEnabled(false);
                    bt_clear.setVisibility(View.INVISIBLE);
                }else{
                    bt_next.setEnabled(true);
                    bt_clear.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.bt_login_message_step1_return:
                finish();
                break;
            case R.id.bt_login_message_step1_clear:
                et_phonenum.setText("");
                break;
            case R.id.bt_login_message_step1_next:
                String PhoneNum = et_phonenum.getText().toString().trim();
                String result = RegisterHandler.checkphonenum(PhoneNum);
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("message").equals("false")) {
                        intent=new Intent(this,LoginMessageStep2.class);
                        intent.putExtra("PhoneNum",PhoneNum);
                        intent.putExtra("CountryCode",CountryCode);
                        startActivity(intent);
                    }else if(json.getString("message").equals("true")){
                        Toast.makeText(this, "该手机号暂未注册，请先注册", Toast.LENGTH_SHORT).show();
                        intent = new Intent(this,RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


        }

    }
}
