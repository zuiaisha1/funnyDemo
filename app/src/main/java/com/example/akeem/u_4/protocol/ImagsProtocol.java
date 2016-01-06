package com.example.akeem.u_4.protocol;

import com.example.akeem.u_4.base.BaseProtocol;
import com.example.akeem.u_4.utils.UriHelper;
import com.lidroid.xutils.util.LogUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Akeem on 2016/1/3.
 */
public class ImagsProtocol extends BaseProtocol {


    public static String loadData(String key, int pager) {
        String resultJsonString = null;
        try {
        String url = UriHelper.getInstance().getImagesListUrl(key, pager);

        Request request = new Request.Builder().get()// get方法
                .url(url)// 对应的url
                .build();

            Response response = okHttpClient.newCall(request).execute();
            resultJsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultJsonString;
    }


}
