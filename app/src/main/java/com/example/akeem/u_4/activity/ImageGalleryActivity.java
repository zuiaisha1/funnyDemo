package com.example.akeem.u_4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.example.akeem.u_4.R;
import com.example.akeem.u_4.view.photoview.PhotoViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tiansj on 15/8/6.
 */
public class ImageGalleryActivity extends AppCompatActivity {

    @Bind(R.id.headTitle)
    TextView mHeadTitle;
//    @Bind(R.id.viewer)
//    ViewPager mViewer;
    private int position;
    private List<String> imgUrls; //图片列表

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_gallery);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        imgUrls = intent.getStringArrayListExtra("images");
        if (imgUrls == null) {
            imgUrls = new ArrayList<>();
        }
        initView();
        initViewEvent();
        initGalleryViewPager();
    }

    private void initView() {
//        headBackBtn = (Button)findViewById(R.id.btnBack);
//        headBackBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViewEvent() {
    }

    private void initGalleryViewPager() {
        PhotoViewAdapter pagerAdapter = new PhotoViewAdapter(this, imgUrls);
        pagerAdapter.setOnItemChangeListener(new PhotoViewAdapter.OnItemChangeListener() {
            int len = imgUrls.size();

            @Override
            public void onItemChange(int currentPosition) {
                mHeadTitle.setText((currentPosition + 1) + "/" + len);
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.viewer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(position);
    }

}
