package com.opencdk.appwidget.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.opencdk.appwidget.GConstants;
import com.opencdk.appwidget.activity.NewsListActivity;

/**
 * 
 * @author 笨鸟不乖
 * @email benniaobuguai@gmail.com
 * @version 1.0.0
 * @since 2016-3-22
 * @Modify 2016-3-22
 */
public class NewsAppWidgetProvider extends AppWidgetProvider {
	
	private static final String TAG = "NewsAppWidgetProvider";

	public static final String ACTION_REFRESH_MANUAL = "com.opencdk.appwidget.action.APPWIDGET_REFRESH_MANUAL";
	public static final String ACTION_REFRESH_AUTO = "com.opencdk.appwidget.action.APPWIDGET_REFRESH_AUTO";
	public static final String ACTION_JUMP_LISTITEM = "com.opencdk.appwidget.action.APPWIDGET_JUMP_LISTITEM";
	public static final String ACTION_JUMP_LOGO = "com.opencdk.appwidget.action.APPWIDGET_JUMP_LOGO";

	/** 扩展信息 */
	public static final String EXT_DATA = "ext_data";

	@Override
	public void onReceive(final Context context, final Intent intent) {
		if (ACTION_JUMP_LOGO.equalsIgnoreCase(intent.getAction())) {
			Intent newsListIntent = new Intent(context, NewsListActivity.class);
			newsListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(newsListIntent);
		} else if (ACTION_REFRESH_MANUAL.equals(intent.getAction())) {
			Log.d(TAG, "-- APPWIDGET_REFRESH_MANUAL --");
			
			NewsRemoteViews remoteViews = new NewsRemoteViews(context);
			remoteViews.loading();
			remoteViews.notifyAppWidgetViewDataChanged();
		} else if (ACTION_REFRESH_AUTO.equals(intent.getAction())) {
			Log.d(TAG, "-- APPWIDGET_REFRESH_AUTO --");

			NewsRemoteViews remoteViews = new NewsRemoteViews(context);
			remoteViews.notifyAppWidgetViewDataChanged();
		} else if (ACTION_JUMP_LISTITEM.equals(intent.getAction())) {
			Log.d(TAG, "-- ACTION_JUMP_LISTITEM --");

			Bundle extras = intent.getExtras();
			if (extras == null) {
				return;
			}
			Intent newIntent = new Intent();
			Uri data = Uri.parse(GConstants.SCHEME_HOST + "?className=com.opencdk.appwidget.activity.NewsDetailActivity");
			newIntent.setData(data);
			newIntent.putExtras(extras);
			newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(newIntent);
		} else {
			System.out.println("Unknown Recevicer: " + intent.getAction());
		}

		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d(TAG, "onUpdate()");

		if (android.os.Build.VERSION.SDK_INT < 14) {
			Log.e(TAG, "Not support version less than 14!!!");
			return;
		}

		NewsRemoteViews newsRemoteViews = new NewsRemoteViews(context);
		newsRemoteViews.setOnLogoClickPendingIntent();
		newsRemoteViews.setOnRefreshClickPendingIntent();
		newsRemoteViews.bindListViewAdapter();

		// 更新所有的widget
		appWidgetManager.updateAppWidget(appWidgetIds, newsRemoteViews);

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

}
