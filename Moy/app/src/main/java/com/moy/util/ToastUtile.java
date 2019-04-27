package com.moy.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kongqw on 2015/9/28.
 */
public class ToastUtile {

    // 构造方法私有化 不允许new对象
    private ToastUtile() {
    }

    // Toast对象
    private static Toast toast = null;

    /**
     * 显示Toast
     */
    public static void showText(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}