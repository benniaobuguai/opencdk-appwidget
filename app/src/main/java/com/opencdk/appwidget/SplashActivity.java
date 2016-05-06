package com.opencdk.appwidget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.widget.ImageView;

/**
 * Splash Activity
 * 
 * @author Administrator
 * 
 */
public class SplashActivity extends BaseActivity {

	private static final String TAG = "SplashActivity";

	private HandlerThread mSubThread;
	private Handler mSubHandler;
	private Handler mUiHandler;

	private ImageView iv_splash;
	
	private static final int MSG_SUB_INIT_TASK = 1000;
	private static final int MSG_UI_INIT_FINISH = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);

		iv_splash = (ImageView) findViewById(R.id.iv_splash);
		
		mUiHandler = new Handler(this.getMainLooper(), mUiHandlerCallback);
		if (GApplication.isEntry()) {
			getUiHandler().sendEmptyMessage(MSG_UI_INIT_FINISH);
			return;
		}

		mSubThread = new HandlerThread("application_setup_thread");
		mSubThread.start();

		mSubHandler = new Handler(mSubThread.getLooper(), mSubHandlerCallback);

		mSubHandler.sendEmptyMessage(MSG_SUB_INIT_TASK);
	}
	
	@Override
	protected void onDestroy() {
		if (mSubThread != null) {
			mSubThread.quit();
			mSubThread = null;
		}

		super.onDestroy();
	}

	public Handler getSubHandler() {
		return mSubHandler;
	}

	public Handler getUiHandler() {
		return mUiHandler;
	}

	protected boolean uiHandlerCallback(Message msg) {
		switch (msg.what) {
			case MSG_UI_INIT_FINISH:
				GApplication.setEntryFlag(true);
				final Intent intent = getIntent();
				if (intent == null) {
					break;
				}

				if (GConstants.SCHEME.equalsIgnoreCase(intent.getScheme())) {
					Intent newIntent = new Intent(intent);
					newIntent.setClass(SplashActivity.this, MainActivity.class);
					newIntent.putExtras(intent.getExtras());
					startActivity(newIntent);
				} else {
					intent.setClass(SplashActivity.this, MainActivity.class);
					intent.setData(intent.getData());
					startActivity(intent);
				}

				finish();
				break;
		}

		return false;
	}

    protected boolean subHandlerCallback(Message msg) {
        switch (msg.what) {
            case MSG_SUB_INIT_TASK:
                SystemClock.sleep(2 * 1000);
                // SystemClock.sleep(600);

                getUiHandler().sendEmptyMessage(MSG_UI_INIT_FINISH);
                break;
        }

        return false;
    }

	private Handler.Callback mUiHandlerCallback = new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			return uiHandlerCallback(msg);
		}
	};

	private Handler.Callback mSubHandlerCallback = new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			return subHandlerCallback(msg);
		}
	};

}