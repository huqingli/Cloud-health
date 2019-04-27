package com.moy.handler;

import com.moy.util.ConnectUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *
 *@Author Zlq
 *@Date 2018/11/30 15:29
 *@Description: 修改二级密码
 *
 * */

public class ModifySecpwdHandler {
        public static String modifysecpwd(String phoneNum, String secpwd) {
            String result = "";//注册返回的结果

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "ModifySecpwdServlet?phoneNum=" + phoneNum + "&secpwd=" + secpwd );
            try {
                HttpResponse response = httpclient.execute(request);
                if(response.getStatusLine().getStatusCode()==200){
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity,"utf-8");
                    return result;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
}
