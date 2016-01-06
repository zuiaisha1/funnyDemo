package com.example.akeem.u_4;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStripExtends;
import com.example.akeem.u_4.adpter.MenuItemAdapter;
import com.example.akeem.u_4.base.BaseActivity;
import com.example.akeem.u_4.base.BaseFragment;
import com.example.akeem.u_4.factory.FragmentFactory;
import com.example.akeem.u_4.utils.UiUtils;
import com.example.akeem.u_4.view.jazzyviewpager.JazzyViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.vp_main)
    JazzyViewPager mVpMain;
//    @Bind(R.id.fl_left)
//    FrameLayout mFlLeft;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.main_drawerlayout)
    DrawerLayout mMainDrawerlayout;
    @Bind(R.id.vp_tab_main)
    PagerSlidingTabStripExtends mVpTabMain;
    private ListView mLvLeftMenu;

    private ActionBarDrawerToggle mToggle;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpDrawer();


    }
    private void setUpDrawer()
    {
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.header_just_username, mLvLeftMenu, false));
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this);
        mLvLeftMenu.setAdapter(menuItemAdapter);

        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (   mMainDrawerlayout.isDrawerOpen(mLvLeftMenu)) {
                    Toast.makeText(MainActivity.this, "点击了第几个"+position, Toast.LENGTH_SHORT).show();
                    mMainDrawerlayout.closeDrawers();
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mToggle = new ActionBarDrawerToggle(this, mMainDrawerlayout, mToolbar, R.string.open, R.string.close);
        //同步状态
        mToggle.syncState();
        //设置监听
        mMainDrawerlayout.setDrawerListener(mToggle);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (   mMainDrawerlayout.isDrawerOpen(mLvLeftMenu)) {
                mMainDrawerlayout.closeDrawers();
                return false;
            }
//                DialogUtil.showExitSystemDialog(this); //弹对话框
            return exitApplication(); //弹吐司
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void initFragment() {

        JazzyViewPager.TransitionEffect tablet = JazzyViewPager.TransitionEffect.valueOf("ZoomIn");
/*************************/
        mVpMain.setTransitionEffect(tablet);
//        mVpMain.setAdapter(new MainAdapter());
        mVpMain.setAdapter(new MyPageerAdapter(getSupportFragmentManager()));
        mVpMain.setPageMargin(30);
/*************************/
        mVpTabMain.setViewPager(mVpMain);
//        mVpTabMain.setUnderlineColor(Color.BLACK); //再加一个下划线
        mVpTabMain.setTextColorResource(R.color.colorThem);
//        mVpTabMain.setUnderlineColorResource(R.color.colorThem);
        mVpTabMain.setIndicatorColorResource(R.color.colorThem);
        mVpTabMain.setDividerColorResource(R.color.colorThem);
        mVpTabMain.setDividerColor(0);
    }

    String[] mTabs = UiUtils.getStringArray(R.array.tabs);

    class MyPageerAdapter extends FragmentPagerAdapter {

        public MyPageerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
//            mVpMain.setObjectForPosition(fragment, position);
//            fragment.show();
            return fragment;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            mVpMain.setObjectForPosition(obj, position);
            return obj;
        }


        public boolean isViewFromObject(View view, Object object) {
            if (object != null) {
                return ((Fragment) object).getView() == view;
            } else {
                return false;
            }
        }

        @Override
        public int getCount() {
            return mTabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position];
        }
    }


}