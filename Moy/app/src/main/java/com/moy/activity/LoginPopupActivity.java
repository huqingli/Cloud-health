package com.moy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/10/3.
 */




public class LoginPopupActivity extends Activity implements View.OnClickListener {

    private Button bt_MessageLogin,bt_Cancel;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_otherway_popup);

        bt_MessageLogin = (Button)findViewById(R.id.bt_login_popup_message);
        bt_Cancel = (Button)findViewById(R.id.bt_login_popup_cancel);
        linearLayout = (LinearLayout)findViewById(R.id.layout_login_popup);

        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        linearLayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //添加按钮监听
        bt_Cancel.setOnClickListener(this);
        bt_MessageLogin.setOnClickListener(this);

    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.bt_login_popup_cancel:
                finish();
                break;
            case R.id.bt_login_popup_message:
                intent = new Intent(LoginPopupActivity.this,LoginMessageStep1.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                finish();
                break;
            default:
                return;
        }
    }
}
