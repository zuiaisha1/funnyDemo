package com.example.akeem.u_4.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.akeem.u_4.view.LoadingPage;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Akeem on 2015/12/30.
 */
public abstract class BaseFragment extends Fragment {

    public Activity mActivity;
    public Gson mGson;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new Gson();
        mActivity = getActivity();
        init();
    }

    public  void init(){};
    private LoadingPage loadingPage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (loadingPage == null) {
            loadingPage = new LoadingPage(getActivity()){
                @Override
                public View createSuccessView() {
                    return BaseFragment.this.initView();
                }
                @Override
                protected LoadingPage.LoadingResult load() {
                    return BaseFragment.this.initData();
                }
            };
        }else{
            ViewParent parent = loadingPage.getParent();
            if(parent instanceof ViewGroup){
                ViewGroup group=(ViewGroup) parent;
                group.removeView(loadingPage);
            }
        }

//        loadingPage.show();
        return loadingPage;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }
    public void show() {
        if (loadingPage != null) {
            loadingPage.show();
        }
    }



    public abstract LoadingPage.LoadingResult initData();

    public abstract View initView() ;

    public LoadingPage.LoadingResult checkData(List datas) {
        if(datas==null){
            return LoadingPage.LoadingResult.ERROR;//  请求服务器失败
        }else{
            if(datas.size()==0){
                return LoadingPage.LoadingResult.EMPTY;
            }else{
                return LoadingPage.LoadingResult.SUCCESS;
            }
        }

    }
}
