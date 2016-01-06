package com.example.akeem.u_4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.akeem.u_4.R;


public class RatioLayout extends FrameLayout {
    public static final int   RELATIVE_WIDTH  = 0;//已知宽度,能够动态计算RatioLayout高度
    public static final int   RELATIVE_HEIGHT = 1;//已知高度,能够动态计算RatioLayout宽度

    private             float mPicRatio       = 1f;//默认就是1
    private             int   mRelative       = RELATIVE_WIDTH;

    /**设置图片的宽高比*/
    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }

    /**
     * 设置计算模式
     * RELATIVE_WIDTH:已知宽度,能够动态计算RatioLayout高度
     * RELATIVE_HEIGHT:已知高度,能够动态计算RatioLayout宽度
     * @param relative
     */
    public void setRelative(int relative) {
        mRelative = relative;
    }

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //取出自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);
        mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_picRatio, 1);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 得到RatioLayout layout_width设置的模式
        int selfWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 得到RatioLayout layout_height设置的模式
        int selfHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 已知宽度
        if (selfWidthMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_WIDTH) {// 代表在xml配置的是match_parent或者具体的数值
            // 得到RatioLayout宽度
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
            // 动态计算高度
            // selfWidth/selftHeight = mPicRatio;
            int selftHeight = (int) (selfWidth / mPicRatio + .5f);

            // 得到孩子的宽度和高度
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selftHeight - getPaddingBottom() - getPaddingTop();

            // 针对自身而言
            setMeasuredDimension(selfWidth, selftHeight);
            // 针对孩子
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

        } else if (selfHeightMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_HEIGHT) {// 已知高度

            // 得到自身的高度
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
            // 动态的计算自身的宽度
            // selfWidth/selftHeight = mPicRatio;
            int selfWidth = (int) (selfHeight * mPicRatio + .5f);

            //孩子的宽度和高度
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();

            //针对自身
            setMeasuredDimension(selfWidth, selfHeight);

            //针对孩子
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
