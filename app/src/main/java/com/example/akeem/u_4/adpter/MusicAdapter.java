package com.example.akeem.u_4.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akeem.u_4.R;
import com.example.akeem.u_4.activity.MuiscActivity;
import com.example.akeem.u_4.base.BaseRecyAdapter;
import com.example.akeem.u_4.bean.MusicBean;
import com.example.akeem.u_4.utils.UiUtils;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Akeem on 2016/1/4.
 */
public class MusicAdapter extends BaseRecyAdapter<MusicBean >{
    @Bind(R.id.iv_icon)
    ImageView mIvIcon;
    @Bind(R.id.tv_name)
    TextView mTvName;

    public MusicAdapter(Context context, List<MusicBean> datas) {
        super(context, datas);
    }

    @Override
    public BaseHolder CreateViewHolder() {
        View view = UiUtils.inflate(R.layout.item_music);
        ButterKnife.bind(this, view);
      BaseHolder musicHolder =  new BaseHolder(view) {
            @Override
            protected void initViews(View mRootView) {
                hasLoadMore(false);
            }

            @Override
            public void setDataAndRefreshUI(MusicBean data,  int position) {
                mBitmapUtils.display(mIvIcon, mDatas.get(position).song.get(0).picture);
                mTvName.setText(mDatas.get(position).song.get(0).title);
                final int pos = position;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        intent.putExtra("music",data.get(0));

                        LogUtils.d( "谁被点了"+pos);
                        LogUtils.d(mDatas.toString());
                        Intent intent = new Intent(mContext, MuiscActivity.class);
                        UiUtils.getMainMap().put("music", mDatas);
                        intent.putExtra("index", pos);
                        ((Activity) mContext).startActivity(intent);

                    }
                });
            }
        };
        musicHolder.setIsRecyclable(false);
        return musicHolder;
    }
    @Override
    public List<MusicBean> onLoadMore() {
        return null;
    }
//    public BaseHolder CreateViewHolder() {
//        View view = UiUtils.inflate(R.layout.item_music);
//        ButterKnife.bind(this, view);
//        return new BaseHolder(view) {
//            @Override
//            protected void initViews(View mRootView) {
//                hasLoadMore(false);
//
//            }

//            @Override
//            public void setDataAndRefreshUI( <List<MusicBean>> data, final int position) {
//                mBitmapUtils.display(mIvIcon, data.gget(0).picture);
//                mTvName.setText(data.get(0).title);
//                final int pos = position;
//                final List<MusicBean.SongBean> finalData = data;
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        intent.putExtra("music",data.get(0));
//                        Intent intent = new Intent(mContext, MuiscActivity.class);
//
//                        UiUtils.getMainMap().put("music", finalData);
//
//
//                        intent.putExtra("index", pos);
//                        ((Activity) mContext).startActivity(intent);
//                    }
//                });

//            }
//        }


    }