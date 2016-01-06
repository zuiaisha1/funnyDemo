package com.example.akeem.u_4.adpter;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akeem.u_4.R;
import com.example.akeem.u_4.bean.ImagersBean;
import com.example.akeem.u_4.protocol.ImagsProtocol;
import com.example.akeem.u_4.utils.ThreadManager;
import com.example.akeem.u_4.utils.UiUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;


/**
 * Created by Akeem on 2016/1/2.
 */
public class MyListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private  Context mContext;
    private List<ImagersBean.ImgsBean> mDatas;
    private final BitmapUtils mBitmapUtils;

    public MyListViewAdapter(Context context, List<ImagersBean.ImgsBean> datas) {  //决定跟布局
        mContext = context;
        mDatas = datas;
        mBitmapUtils =
                new BitmapUtils(mContext);
    }

    public void clearDatas() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }


    public void setDatas(List<ImagersBean.ImgsBean> datas) {
        mDatas = datas;
    }
    public void addDatas(List<ImagersBean.ImgsBean> datas ) {
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    int TYPE_FOOT = 2;
    int TYPE_ITEM = 4;
    @Override
    public int getItemViewType(int position) {
        if (position+1 == getItemCount()) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == TYPE_ITEM) {
            View view = UiUtils.inflate(R.layout.item_stragger);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new MyHolder(view);
        } else if (viewType == TYPE_FOOT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_foot_loading, parent, false);
            return new FootHolder(view);
        }
        return null;

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof MyHolder) {
            ((MyHolder) holder).setDataAndRefreshUI(mDatas.get(position));
        } else {
            //还有跟多加载
            if (hasMore()) {
                ((FootHolder) holder).triggerLoadMoreData();
            } else {

            }

        }


    }

    public  boolean hasMore() {
        return true;
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }


    public class FootHolder extends RecyclerView.ViewHolder {

        public FootHolder(View itemView) {
            super(itemView);
        }

        public void triggerLoadMoreData() {
            ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(800);

                    List<ImagersBean.ImgsBean> loadmoreDatas = null;
                    loadmoreDatas = loadMore();
                    final List<ImagersBean.ImgsBean> finalLoadMoreList = loadmoreDatas;
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (finalLoadMoreList != null) {
                            // 追加到已有的dataSet
                           addDatas(finalLoadMoreList);
                            // 刷新listview
                        }               }
                });

                }
            });

        }


    }

    static int pager ;
    public List<ImagersBean.ImgsBean> loadMore() {
//        String url = UriHelper.getInstance().getImagesListUrl("美女", ++pager);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().get()// get方法
//                .url(url)// 对应的url
//                .build();
//
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            resultJsonString = response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String resultJsonString = null;
        String  resultJsonString =ImagsProtocol.loadData("美女", ++pager);
//        Toast.makeText(mActivity, resultJsonString, Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        ImagersBean imagersBean = gson.fromJson(resultJsonString, ImagersBean.class);

        if (imagersBean == null) {
            pager = 0;
            List<ImagersBean.ImgsBean> imgsBeen = loadMore();
//            imgsBeen.get(2).
            return  imgsBeen;
        } else{
            if (imagersBean.imgs.size() == 0) {
                pager = 0;
                List<ImagersBean.ImgsBean> imgsBeen = loadMore();
                return  imgsBeen;
            }
        }

        return imagersBean.imgs;
    }
        public class MyHolder extends RecyclerView.ViewHolder {
            private final TextView mTvName;
            private final ImageView mIvIcon;

            public MyHolder(View itemView) {
                super(itemView);
                mTvName = (TextView) itemView.findViewById(R.id.item_straggered_tv);
                mIvIcon = (ImageView) itemView.findViewById(R.id.item_straggered_iv);
            }

            public void setDataAndRefreshUI(ImagersBean.ImgsBean dataAndRefreshUI) {
                mTvName.setText(dataAndRefreshUI.title);
                mBitmapUtils.display(mIvIcon, dataAndRefreshUI.imageUrl);
//            Picasso.with(mContext)
//                    .load(dataAndRefreshUI.imageUrl).fade(200)
//                    .into(mIvIcon);
            }
        }
    }
