package com.example.akeem.u_4.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.akeem.u_4.base.BaseFragment;
import com.example.akeem.u_4.utils.UiUtils;
import com.example.akeem.u_4.view.LoadingPage;

/**
 * Created by Akeem on 2015/12/31.
 */
public class UID extends BaseFragment {


    @Override
    public LoadingPage.LoadingResult initData() {
        return LoadingPage.LoadingResult.ERROR;
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText(getClass().getSimpleName());
        textView.setTextSize(UiUtils.px2dip(25));
        return textView;
    }
}
