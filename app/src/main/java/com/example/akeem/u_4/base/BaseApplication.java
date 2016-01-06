package com.example.akeem.u_4.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

public class BaseApplication extends Application {
	private static BaseApplication application;
	private static int mainTid;
	private static Handler handler;
	private static Map mainMap;
	private static Handler musicHandler;
	@Override
	public void onCreate() {
		super.onCreate();
		application=this;
		mainTid = android.os.Process.myTid();
		handler=new Handler();
		mainMap = new HashMap();
		musicHandler = new Handler();
	}

	public static Handler getMusicHandler() {
		return musicHandler;
	}

	public static Map getMainMap() {
		return mainMap;
	}
	public static Context getApplication() {
		return application;
	}
	public static int getMainTid() {
		return mainTid;
	}
	public static Handler getHandler() {
		return handler;
	}
	
	
}
