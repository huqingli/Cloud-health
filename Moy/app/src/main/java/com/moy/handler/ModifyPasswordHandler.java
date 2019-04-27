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

public class ModifyPasswordHandler {
    /**
     *
     *@Author Zlq
     *@Date 2018/11/26 21:50
     *@Description: 个人中心密码修改
     *
     * */
    public static String modifyPassword(String userName, String password) {
        String result = "";//注册返回的结果

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "ModifyPwdServlet?userName=" + userName + "&password=" + password);

        try {
            HttpResponse response = httpclient.execute(request);
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity,"utf-8");
                return result;
            }
        } catch (ClientProtocolException e) {
            System.out.println("HTTP ERROR!!!!!!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO ERROR!!!!!!!!");
            e.printStackTrace();
        }
        return result;
    }

}
