package com.example.akeem.u_4.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.example.akeem.u_4.base.BaseActivity;
import com.example.akeem.u_4.base.BaseApplication;

import java.util.Map;


public class UiUtils {

	public static String[] getStringArray(int tabNames) {
		return getResource().getStringArray(tabNames);
	}

	public static Map getMainMap() {
		return BaseApplication.getMainMap();
	}
	public static Handler getMusicHandler(){
		return BaseApplication.getMusicHandler();
	}

	public static Resources getResource() {
		return BaseApplication.getApplication().getResources();
	}
	public static Context getContext(){
		return BaseApplication.getApplication();
	}
	public static int dip2px(int dip) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}


	public static int px2dip(int px) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	public static void runOnUiThread(Runnable runnable) {

		if(android.os.Process.myTid()==BaseApplication.getMainTid()){

			runnable.run();
		}else{
			if (BaseApplication.getHandler().getLooper() == null) {
				Looper.prepare();
			}
			BaseApplication.getHandler().post(runnable);
		}
	}

	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	public static Drawable getDrawalbe(int id) {
		return getResource().getDrawable(id);
	}

	public static int getDimens(int homePictureHeight) {
		return (int) getResource().getDimension(homePictureHeight);
	}
	public static void postDelayed(Runnable run, int time) {
		BaseApplication.getHandler().postDelayed(run, time);
	}
	public static void cancel(Runnable auToRunTask) {
		BaseApplication.getHandler().removeCallbacks(auToRunTask);
	}
	public static void startActivity(Intent intent) {
		if(BaseActivity.activity==null){


			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getContext().startActivity(intent);
		}else{
			BaseActivity.activity.startActivity(intent);
		}
	}
	
}
