package com.example.akeem.u_4.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * �����̳߳�
 * 
 * @author itcast
 * 
 */
public class ThreadManager {
	private ThreadManager() {

	}

	private static ThreadManager instance = new ThreadManager();
	private ThreadPoolProxy longPool;
	private ThreadPoolProxy shortPool;

	public static ThreadManager getInstance() {
		return instance;
	}

	// ����ȽϺ�ʱ
	// cpu�ĺ���*2+1
	public synchronized ThreadPoolProxy createLongPool() {
		if (longPool == null) {
			longPool = new ThreadPoolProxy(5, 5, 5000L);
		}
		return longPool;
	}
	// ���������ļ�
	public synchronized ThreadPoolProxy createShortPool() {
		if(shortPool==null){
			shortPool = new ThreadPoolProxy(3, 3, 5000L);
		}
		return shortPool;
	}

	public class ThreadPoolProxy {
		private ThreadPoolExecutor pool;
		private int corePoolSize;
		private int maximumPoolSize;
		private long time;

		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.time = time;

		}
		public void execute(Runnable runnable) {
			if (pool == null) {
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
						time, TimeUnit.MILLISECONDS,
						new LinkedBlockingQueue<Runnable>(10));
			}
			pool.execute(runnable); // �����̳߳� ִ���첽����
		}
		public void cancel(Runnable runnable) {
			if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
				pool.remove(runnable); // ȡ���첽����
			}
		}
	}
}
