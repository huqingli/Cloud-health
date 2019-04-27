package com.moy.util;

/**
 * Created by Administrator on 2017/10/2.
 */

import android.app.Activity;


public class SendMessageUtil extends Activity{

    private static String appKey = "20f7e6408a56e";
    private static String appSecret = "4e49c65c784cedb5dfc93230250a6ad8";

    public static String getAppKey(){
        return appKey;
    }
    public static String getAppSecret(){
        return appSecret;
    }

}