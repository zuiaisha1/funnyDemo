package com.example.akeem.u_4.view;


import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.akeem.u_4.R;
import com.example.akeem.u_4.utils.ThreadManager;
import com.example.akeem.u_4.utils.UiUtils;

public abstract class LoadingPage extends FrameLayout {

	public static final int STATE_UNKOWN = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_ERROR = 2;
	public static final int STATE_EMPTY = 3;
	public static final int STATE_SUCCESS = 4;
	public int state = STATE_UNKOWN;

	private View loadingView;
	private View errorView;
	private View emptyView;
	private View successView;
	private Context mContext;

	public LoadingPage(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		loadingView = createLoadingView();
		if (loadingView != null) {
			this.addView(loadingView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		errorView = createErrorView();
		if (errorView != null) {
			this.addView(errorView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		emptyView = createEmptyView();
		if (emptyView != null) {
			this.addView(emptyView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		showPage();
	}

	public void showPage() {
		if (loadingView != null) {
			loadingView.setVisibility(state == STATE_UNKOWN
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (errorView != null) {
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (emptyView != null) {
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (state == STATE_SUCCESS) {
			if (successView == null) {
				successView = createSuccessView();
				this.addView(successView, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			}
			successView.setVisibility(View.VISIBLE);
		} else {
			if (successView != null) {
				successView.setVisibility(View.INVISIBLE);
			}
		}
	}

	private View createEmptyView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.pager_empty,
				null);
		return view;
	}

	private View createErrorView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.pager_error,
				null);
		Button page_bt = (Button) view.findViewById(R.id.error_btn_retry);
		page_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}

	private View createLoadingView() {
		View view = View.inflate(UiUtils.getContext(),
				R.layout.pager_loading, null);
		return view;
	}
	public enum LoadingResult {
		ERROR(2),EMPTY(3), SUCCESS(4);
		int state ;
		LoadingResult(int state){
			this.state = state;
		}
		public int getState() {
			return state;
		}
	}

	public void show() {
		if (state == STATE_ERROR || state == STATE_EMPTY) {
			state = STATE_LOADING;
		}
		ThreadManager.getInstance().createLongPool().execute(new Runnable() {

			@Override
			public void run() {
				SystemClock.sleep(500);
				final LoadingResult result = load();
				UiUtils.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (result != null) {
							state = result.getState();
								showPage();
						}
					}
				});
			}
		});
		showPage();

//		if (state != STATE_SUCCESS && state != STATE_LOADING) {
//			state = STATE_LOADING;
//			showPage();
//			ThreadManager.getInstance().createLongPool().execute(new Runnable() {
//				@Override
//				public void run() {
//					SystemClock.sleep(800);
//					LoadingResult tempState = load();
//					state = tempState.getState();
//					UiUtils.runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							showPage();
//						}
//					});
//				}
//			});
//		}
	}


	public abstract View createSuccessView();
	protected abstract LoadingResult load();
}
