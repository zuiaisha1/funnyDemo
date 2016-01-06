package com.example.akeem.u_4.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Akeem on 2015/12/30.
 */
public abstract class BasePagerAdapter extends PagerAdapter {

    @Override
    public abstract  Object instantiateItem(ViewGroup container, int position) ;


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


}
