package com.opencdk.appwidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.opencdk.appwidget.GConstants;
import com.opencdk.appwidget.R;
import com.opencdk.appwidget.model.News;

/**
 * 
 * @author 笨鸟不乖
 * @email benniaobuguai@gmail.com
 * @version 1.0.0
 * @since 2016-3-22
 * @Modify 2016-3-22
 */
public class NewsRemoteViews extends RemoteViews {

	private static final int[] MARK_ARRAY = { R.drawable.ic_mark_pic, R.drawable.ic_mark_pic_many, R.drawable.ic_mark_video };

	private Context mContext;

	private AppWidgetManager mAppWidgetManager;

	private int[] mAppWidgetIds;

	public NewsRemoteViews(Context context) {
		super(context.getPackageName(), R.layout.layout_widget_news_list);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		this.mAppWidgetManager = AppWidgetManager.getInstance(mContext);
		this.mAppWidgetIds = getAppWidgetIds();
	}

	private Class<? extends AppWidgetProvider> getAppWidgetProvider() {
		return NewsAppWidgetProvider.class;
	}

	private Intent getProviderIntent() {
		return new Intent(mContext, getAppWidgetProvider());
	}

	public int[] getAppWidgetIds() {
		ComponentName provider = new ComponentName(mContext, getAppWidgetProvider());
		return mAppWidgetManager.getAppWidgetIds(provider);
	}

	public void loading() {
		final int widgetLoading = R.id.widget_loading;
		final int widgetRefresh = R.id.widget_refresh;
		setViewVisibility(widgetLoading, View.VISIBLE);
		setViewVisibility(widgetRefresh, View.GONE);
		mAppWidgetManager.updateAppWidget(mAppWidgetIds, this);
	}

	public void loadComplete() {
		final int widgetLoading = R.id.widget_loading;
		final int widgetRefresh = R.id.widget_refresh;
		setViewVisibility(widgetLoading, View.GONE);
		setViewVisibility(widgetRefresh, View.VISIBLE);
		mAppWidgetManager.updateAppWidget(mAppWidgetIds, this);
	}

	public void setOnLogoClickPendingIntent() {
		Intent intent = getProviderIntent();
		intent.setAction(NewsAppWidgetProvider.ACTION_JUMP_LOGO);
		PendingIntent logoPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
		setOnClickPendingIntent(R.id.widget_logo, logoPendingIntent);
	}

	public void setOnRefreshClickPendingIntent() {
		Intent intent = getProviderIntent();
		intent.setAction(NewsAppWidgetProvider.ACTION_REFRESH_MANUAL);
		PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
		setOnClickPendingIntent(R.id.widget_refresh, refreshPendingIntent);
	}
	
	public void bindListViewAdapter() {
		int listViewResId = R.id.listView;
		Intent serviceIntent = new Intent(mContext, NewsWidgetService.class);
		serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
		// rv.setEmptyView(listViewResId, R.id.tv_empty);//指定集合view为空时显示的view
		setRemoteAdapter(listViewResId, serviceIntent);
		
		// 设置响应 ListView 的intent模板
		// (01) 通过 setPendingIntentTemplate设置"intent模板"
		// (02) 然后在处理该"集合控件"的RemoteViewsFactory类的getViewAt()接口中通过 setOnClickFillInIntent设置"集合控件的某一项的数据"
		Intent listItemIntent = getProviderIntent();
		listItemIntent.setAction(NewsAppWidgetProvider.ACTION_JUMP_LISTITEM);
		listItemIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, listViewResId);
		PendingIntent pendingIntentTemplate = PendingIntent.getBroadcast(mContext, 0, listItemIntent, 0);
		// 设置intent模板
		setPendingIntentTemplate(listViewResId, pendingIntentTemplate);
	}

	public void notifyAppWidgetViewDataChanged() {
		int[] appIds = getAppWidgetIds();
		// 更新ListView
		mAppWidgetManager.notifyAppWidgetViewDataChanged(appIds, R.id.listView);
	}

	/**
	 * 
	 * @param news
	 * @return
	 */
	public RemoteViews applyNewsView(final News news) {
		if (news == null) {
			return null;
		}

		RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.layout_widget_news_list_item);
		views.setViewVisibility(R.id.tv_title, View.VISIBLE);
		views.setViewVisibility(R.id.tv_date, View.VISIBLE);
		views.setViewVisibility(R.id.iv_mark, View.VISIBLE);

		views.setTextViewText(R.id.tv_title, news.getTitle());
		views.setTextViewText(R.id.tv_date, news.getDate());
		views.setImageViewResource(R.id.iv_mark, MARK_ARRAY[news.getNewsMark() % MARK_ARRAY.length]);

		Intent fillInIntent = new Intent();
		Bundle extras = new Bundle();
		extras.putString(GConstants.SCHEME_DATA_KEY, news.toJSON().toString());
		fillInIntent.putExtras(extras);
		// 设置 第position位的"视图"对应的响应事件, api 11
		views.setOnClickFillInIntent(R.id.news_container, fillInIntent);

		return views;
	}

}
