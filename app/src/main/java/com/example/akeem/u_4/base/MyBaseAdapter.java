package com.example.akeem.u_4.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * listView适配器
 * Created by Akeem on 2015/12/30.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    List<T> mList ;
    public MyBaseAdapter(List list) {
        mList = list;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return  mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mList != null) {
            return  mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public  abstract View getView(int position, View convertView, ViewGroup parent);
}
