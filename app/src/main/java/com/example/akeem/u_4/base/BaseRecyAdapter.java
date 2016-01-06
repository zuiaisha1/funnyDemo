package com.example.akeem.u_4.base;

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

import static android.view.View.*;

/**
 * Created by Akeem on 2016/1/3.
 */
public abstract class BaseRecyAdapter<DATAS>  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context mContext;
    public List<DATAS> mDatas;
    public static  BitmapUtils mBitmapUtils = new BitmapUtils(UiUtils.getContext());
    private View mFootView;

    public BaseRecyAdapter(Context context, List<DATAS> datas) {  //决定跟布局
        mContext = context;
        mDatas = datas;
    }
    public void clearDatas() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }


    public void setDatas(List<DATAS> datas) {
        mDatas = datas;
    }
    public void addDatas(List<DATAS> datas ) {
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
            BaseHolder baseHolder = CreateViewHolder();
            return baseHolder;
        } else if (viewType == TYPE_FOOT) {
            mFootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_foot_loading, parent, false);
            return new FootHolder(mFootView);
        }
        return null;

    }

    public abstract BaseHolder CreateViewHolder();


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (TYPE_FOOT == getItemViewType(position)) {
            //还有跟多加载
            if (hasMore()) {
                ((FootHolder) holder).triggerLoadMoreData();
            } else {
            }
        } else {
            ((BaseHolder)holder).setDataAndRefreshUI(mDatas.get(position),position);
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

    boolean hasLoadMore = true;
    public void hasLoadMore(boolean flag) {
        hasLoadMore = flag;
    }
    public class FootHolder extends RecyclerView.ViewHolder {
        public FootHolder(View itemView) {
            super(itemView);
            if (hasLoadMore) {
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
            }

        }

        public void triggerLoadMoreData() {
            ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(800);

                    List<DATAS> loadmoreDatas = null;
                    loadmoreDatas = onLoadMore();


                    final List<DATAS> finalLoadMoreList = loadmoreDatas;
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

    public abstract List<DATAS> onLoadMore();

    public  abstract class BaseHolder extends RecyclerView.ViewHolder {
        View mRootView;
        public BaseHolder(View itemView) {
            super(itemView);
             mRootView  = itemView;
            initViews( mRootView);
        }

        protected abstract void initViews( View mRootView);

        public abstract void setDataAndRefreshUI(DATAS data,int position);
    }
}
