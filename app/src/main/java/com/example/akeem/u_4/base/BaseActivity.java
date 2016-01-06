package com.example.akeem.u_4.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;


import com.example.akeem.u_4.utils.UiUtils;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {
	public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

	public static BaseActivity activity;

	@Override
	protected void onResume() {
		super.onResume();
		activity=this;
	}
	@Override
	protected void onPause() {
		super.onPause();
		activity=null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		synchronized (mActivities) {
			mActivities.add(this);
		}
		init();
		initView();
		initToolBar();
		initFragment();

	}

	protected void initFragment() {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		synchronized (mActivities) {
			mActivities.remove(this);
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {

//                DialogUtil.showExitSystemDialog(this); //弹对话框
			return exitApplication(); //弹吐司
		}
		return super.onKeyDown(keyCode, event);
	}


	private long firstBackKeyDown;
	public boolean exitApplication() {
		if (firstBackKeyDown == 0 ? true : false) {
			firstBackKeyDown = System.currentTimeMillis();
			Toast.makeText(UiUtils.getContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			if (System.currentTimeMillis() - firstBackKeyDown <= 2000 ? true : false) {
//                android.os.Process.killProcess(android.os.Process.myPid());
				finish();
				return true;

			} else {
				firstBackKeyDown = 0;
				exitApplication();
				return true;
			}
		}
	}
	public void killAll() {
		List<BaseActivity> copy;
		synchronized (mActivities) {
			copy = new LinkedList<BaseActivity>(mActivities);
		}
		for (BaseActivity activity : copy) {
			activity.finish();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	protected void initToolBar() {



	}
	protected  abstract void initView() ;
	protected void init() {
	}
}
