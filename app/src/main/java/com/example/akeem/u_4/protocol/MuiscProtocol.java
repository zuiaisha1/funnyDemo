package com.example.akeem.u_4.protocol;

import com.example.akeem.u_4.base.BaseProtocol;
import com.example.akeem.u_4.utils.UriHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Akeem on 2016/1/4.
 */
public class MuiscProtocol extends BaseProtocol {


    public static   String loadData( int index) {
        String str = UriHelper.getInstance().getDoubanPlayListUrl(index+"");
        Request request = new Request.Builder().get().url(str).build();

        try {
            Response execute = okHttpClient.newCall(request).execute();
            String json = execute.body().string();
            return json;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }



}
