package com.example.akeem.u_4.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.akeem.u_4.R;
import com.example.akeem.u_4.view.loopviewpager.AutoLoopViewPager;
import com.example.akeem.u_4.view.viewpagerindicator.CirclePageIndicator;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tiansj on 15/6/29.
 */
public class ImagesDetailActivity extends AppCompatActivity {

    @Bind(R.id.pager)
    AutoLoopViewPager pager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    private GalleryPagerAdapter galleryAdapter;
    private List<String> imageList = new ArrayList<String>();
    private Activity mActivity;
    private BitmapUtils mBitmapUtils;
    private String mImagUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_house_detail);
        mActivity = this;
         ButterKnife.bind(this);


        Intent intent = getIntent();
        mImagUrl = intent.getStringExtra("imag");

        imageList.add(mImagUrl);
        imageList.add(mImagUrl);
        imageList.add(mImagUrl);

        initView();
         initEvent();
    }


    public void initView() {
        mBitmapUtils = new BitmapUtils(mActivity);
        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
    }

    private void initEvent() {

    }


    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(ImagesDetailActivity.this);

            mBitmapUtils.display(item,mImagUrl);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
//            item.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(item);

            final int pos = position;

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ImagesDetailActivity.this, ImageGalleryActivity.class);
                    intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
                    intent.putExtra("position", pos);




                    startActivity(intent);
                }
            });

            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pager.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pager.stopAutoScroll();
    }
}
