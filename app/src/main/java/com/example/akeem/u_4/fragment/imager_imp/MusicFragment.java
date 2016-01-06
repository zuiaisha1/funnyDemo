package com.example.akeem.u_4.fragment.imager_imp;

import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.akeem.u_4.R;
import com.example.akeem.u_4.adpter.MusicAdapter;
import com.example.akeem.u_4.base.BaseFragment;
import com.example.akeem.u_4.bean.ImagersBean;
import com.example.akeem.u_4.bean.MusicBean;
import com.example.akeem.u_4.protocol.MuiscProtocol;
import com.example.akeem.u_4.utils.ThreadManager;
import com.example.akeem.u_4.utils.UiUtils;
import com.example.akeem.u_4.view.LoadingPage;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Akeem on 2016/1/4.
 */
public class MusicFragment extends BaseFragment {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.srl)
    SwipeRefreshLayout mSrl;
    private MusicAdapter mMusicAdapter;
    int index = 0;

    List<MusicBean> muiscDatas ;
    @Override
    public LoadingPage.LoadingResult initData() {
        muiscDatas = new ArrayList<>();
        MusicBean musicBean;
        int tempIndex =    index ;
        for (int i = tempIndex; i < tempIndex+11; i++) {
            index++;
            String json = MuiscProtocol.loadData(index);
             musicBean = mGson.fromJson(json, MusicBean.class);
            if (musicBean != null) {
                muiscDatas.add(musicBean);
            }
        }


        return  checkData( muiscDatas);
    }

    @Override
    public View initView() {


        View view = UiUtils.inflate(R.layout.fragment_music);

        ButterKnife.bind(this,view);
//        LinearLayoutManager layoutManger = new GridLayoutManager(mActivity, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        //设置adapter
          mMusicAdapter = new MusicAdapter(getContext(), muiscDatas);
             mRecyclerView.setAdapter(mMusicAdapter);
        initSwipeRefreshLayout();

        return view;
    }





    private void initSwipeRefreshLayout() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMusicAdapter.clearDatas();
                LogUtils.i(muiscDatas.toString());
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(300);
                        MusicBean musicBean;
                        int tempIndex =    index ;
                        for (int i = tempIndex; i < tempIndex+11; i++) {
                            index++;
                            String json = MuiscProtocol.loadData(index);
                            musicBean = mGson.fromJson(json, MusicBean.class);
                            if (musicBean != null) {
                                muiscDatas.add(musicBean);
                            }
                        }
                        mMusicAdapter.setDatas(muiscDatas);
                        LogUtils.i(muiscDatas.toString());
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMusicAdapter.notifyDataSetChanged();
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
