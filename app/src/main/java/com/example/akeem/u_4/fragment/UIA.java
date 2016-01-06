package com.example.akeem.u_4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.akeem.u_4.MainActivity;
import com.example.akeem.u_4.R;
import com.example.akeem.u_4.base.BaseFragment;
import com.example.akeem.u_4.bean.ImagersBean;
import com.example.akeem.u_4.utils.UiUtils;
import com.example.akeem.u_4.utils.UriHelper;
import com.example.akeem.u_4.view.LoadingPage;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Akeem on 2015/12/31.
 */
public class UIA extends BaseFragment {


    @Bind(R.id.uia_lv)
    ListView mUiaLv;
    private List<ImagersBean.ImgsBean> mImgs;
    private BitmapUtils mBitmapUtils;
    LoadingPage.LoadingResult result = LoadingPage.LoadingResult.SUCCESS;
    @Override
    public LoadingPage.LoadingResult initData() {

        mBitmapUtils = new BitmapUtils(mActivity);
        String url = UriHelper.getInstance().getImagesListUrl("美女", 2);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                Gson gson = new Gson();
                ImagersBean imagersBean = gson.fromJson( json, ImagersBean.class);
                mImgs = imagersBean.imgs;

                 result =  checkData(mImgs);

            }
            @Override
            public void onFailure(HttpException e, String s) {
                result = LoadingPage.LoadingResult.ERROR;
            }
        });





        return result;
    }

    @Override
    public View initView() {

        View view = UiUtils.inflate(R.layout.fragment_uia);
//        ButterKnife.bind(this, view);
//        mBitmapUtils = new BitmapUtils(mActivity);
//        String url = UriHelper.getInstance().getImagesListUrl("美女", 2);
//        HttpUtils httpUtils = new HttpUtils();
//        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String json = responseInfo.result;
//                Gson gson = new Gson();
//                ImagersBean imagersBean = gson.fromJson( json, ImagersBean.class);
//                mImgs = imagersBean.imgs;
//                mUiaLv.setAdapter( new MyBaseAdapter());
//            }
//            @Override
//            public void onFailure(HttpException e, String s) {
//            }
//        });

//        mUiaLv.setAdapter( new MyBaseAdapter());

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
//            if (mImgs != null) {
//                mImgs.size();
//                LogUtils.i( mImgs.size()+"");
//            }
            return mImgs.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view  = View.inflate(mActivity, R.layout.item_test_imager, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
//            imageView.setBackgroundResource(R.mipmap.ic_launcher);
            LogUtils.i( mImgs.get(position).imageUrl+"");
            mBitmapUtils.display(imageView,mImgs.get(position).imageUrl);
            return view;
        }


    }



}
