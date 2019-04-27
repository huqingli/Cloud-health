package com.moy.util;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;

import static com.moy.util.ConnectUtil.BASE_URL;

/**
 * Created by Administrator on 2017/11/13.
 */

public class UploadUtils {

    public UploadUtils(final Context mContext, String imgpath, String filename) {
        try{
            String path = BASE_URL + "uploadServlet";
            RequestParams params = new RequestParams();
            params.put("filename",filename);
            params.put("file",new File(imgpath));

            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.post(path, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    Toast.makeText(mContext, "头像上传成功", Toast.LENGTH_SHORT).show();
                    Log.e("上传结果", "成功");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(mContext, "头像上传失败", Toast.LENGTH_SHORT).show();
                    Log.e("上传结果", "失败");
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
