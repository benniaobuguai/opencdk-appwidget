package com.opencdk.appwidget.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 守护进程的广播接收器
 * 
 * @author 笨鸟不乖
 * @email benniaobuguai@gmail.com
 * @version 1.0.0
 * @since 2016-3-22
 * @Modify 2016-3-22
 * 
 * <pre>
 * I faced exactly the same problem and it seems to be that in the Google Play Services Library, 
 * they missed to put android:exported="true" in their <service> declaration.
 * 
 * Before Android 5.0, it was allowed to start services with implicit intents, 
 * but now it's not possible, and instead of having a warning, you will have an Exception.
 * They need to fix their stuff.
 * </pre>
 * 
 */
public class DaemonReceiver extends BroadcastReceiver {

	private static final String TAG = "Global.DaemonReceiver";

	public static final String ACTION_DAEMON_RECEIVER = "com.opencdk.appwidget.action.DAEMON_RECEIVER";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Global receiver, action: " + intent.getAction());

		if (android.os.Build.VERSION.SDK_INT >= 14) {
			// Widget仅支持4.0+
			Intent service = new Intent();
			service.setClass(context, NewsWidgetService.class);
			context.startService(service);
		}
	}

}
