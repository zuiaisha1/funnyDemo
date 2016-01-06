package com.example.akeem.u_4.fragment.imager_imp;

import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.akeem.u_4.R;
import com.example.akeem.u_4.adpter.GirlImagsAdapter;
import com.example.akeem.u_4.base.BaseFragment;
import com.example.akeem.u_4.bean.ImagersBean;
import com.example.akeem.u_4.protocol.ImagsProtocol;
import com.example.akeem.u_4.utils.ThreadManager;
import com.example.akeem.u_4.utils.UiUtils;
import com.example.akeem.u_4.view.LoadingPage;
import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Akeem on 2016/1/2.
 */
public class GirlFragment extends BaseFragment {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.srl)
    SwipeRefreshLayout mSrl;
    private List<ImagersBean.ImgsBean> mDatas;
    private GirlImagsAdapter mAdapter;

    @Override
    public LoadingPage.LoadingResult initData() {
//        Looper.prepare();
        String resultJsonString = ImagsProtocol.loadData("美女", 1);
        Gson gson = new Gson();
        ImagersBean imagersBean = gson.fromJson(resultJsonString, ImagersBean.class);
        if (imagersBean != null) {
            mDatas = imagersBean.imgs;
        }

        return checkData(mDatas);

    }



    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.fragment_girl);
        ButterKnife.bind(this, view);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        //设置adapter
        mAdapter = new GirlImagsAdapter(mActivity, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        initSwipeRefreshLayout();

        return view;
    }

    private void initSwipeRefreshLayout() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clearDatas();
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<ImagersBean.ImgsBean> imgsBeen = mAdapter.onLoadMore();
                        mAdapter.setDatas(imgsBeen);
                        SystemClock.sleep(300);
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(mActivity, "刷新成功", Toast.LENGTH_SHORT).show();
                                if (mSrl!=null) {
                                    if (mSrl.isRefreshing()) {
                                        mSrl.setRefreshing(false);
                                    }
                                }
                            }
                        });
                    }
                });


            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
