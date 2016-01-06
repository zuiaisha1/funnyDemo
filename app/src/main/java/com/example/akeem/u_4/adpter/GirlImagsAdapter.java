package com.example.akeem.u_4.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akeem.u_4.R;
import com.example.akeem.u_4.activity.ImagesDetailActivity;
import com.example.akeem.u_4.base.BaseRecyAdapter;
import com.example.akeem.u_4.bean.ImagersBean;
import com.example.akeem.u_4.protocol.ImagsProtocol;
import com.example.akeem.u_4.utils.UiUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Akeem on 2016/1/3.
 */
public class GirlImagsAdapter extends BaseRecyAdapter<ImagersBean.ImgsBean> implements View.OnClickListener {


//    private MyItemClickListener mListener;
//    private MyItemLongClickListener mLongClickListener;


    public GirlImagsAdapter(Context context, List<ImagersBean.ImgsBean> datas) {
        super(context, datas);
    }


    @Override
    public BaseHolder CreateViewHolder() {

        View view = UiUtils.inflate(R.layout.item_stragger);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return new BaseHolder(view) {

            TextView mTvName;
            ImageView mIvIcon;
            @Override
            protected void initViews(View mRootView) {
                    mTvName = (TextView) mRootView.findViewById(R.id.item_straggered_tv);
                    mIvIcon = (ImageView) mRootView.findViewById(R.id.item_straggered_iv);
//                mRootView.setOnClickListener();
            }


            @Override
            public void setDataAndRefreshUI(final ImagersBean.ImgsBean data, final int position) {
                mTvName.setText(data.title);
                mBitmapUtils.display(mIvIcon, data.imageUrl);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(mContext, "点击了"+position, Toast.LENGTH_SHORT).show();
//                        UIHelper.showHouseDetailActivity((Activity) mContext);

                        Intent intent = new Intent(mContext, ImagesDetailActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("imag", data.imageUrl);
                        ((Activity) mContext).startActivityForResult(intent,0);

                    }
                });
            }
        };
    }

    static  int pager = 0;
    @Override
    public List onLoadMore() {
        String  resultJsonString = ImagsProtocol.loadData("美女", ++pager);
//        Toast.makeText(mActivity, resultJsonString, Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        ImagersBean imagersBean = gson.fromJson(resultJsonString, ImagersBean.class);

        if (imagersBean == null) {
            pager = 0;
            List<ImagersBean.ImgsBean> imgsBeen = onLoadMore();
            return  imgsBeen;
        } else{
            if (imagersBean.imgs.size() == 0) {
                pager = 0;
                List<ImagersBean.ImgsBean> imgsBeen = onLoadMore();
                return  imgsBeen;
            }
        }
        return imagersBean.imgs;
    }


    @Override
    public void onClick(View v) {

    }
}
