package com.opencdk.appwidget.widget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.opencdk.appwidget.model.News;
import com.opencdk.appwidget.utils.DataProvider;

class NewsListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

	private static final String TAG = "NewsListRemoteViewsFactory";

	private static final int VIEW_TYPE_COUNT = 1;

	private List<News> mNewsList = new ArrayList<News>();

	private Context mContext;

	public NewsListRemoteViewsFactory(Context context, Intent intent) {
		this.mContext = context;
	}

	@Override
	public void onCreate() {

	}

	@Override
	public int getCount() {
		if (mNewsList == null) {
			return 0;
		}

		return mNewsList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private News getNews(int index) {
		return mNewsList.get(index);
	}

	@Override
	public RemoteViews getLoadingView() {
		// System.out.println("getLoadingView");
		return null;
	}

	@SuppressLint("NewApi")
	@Override
	public RemoteViews getViewAt(int position) {
		if (getCount() == 0) {
			return null;
		}

		System.out.println("getViewAt");

		NewsRemoteViews newsRemoteViews = new NewsRemoteViews(mContext);
		newsRemoteViews.loadComplete();

		News newsItem = getNews(position);
		return newsRemoteViews.applyNewsView(newsItem);
	}

	/**
	 * 设置字体大小
	 * 
	 * @param views
	 * @param viewId
	 * @param textSize
	 */
	@SuppressLint("NewApi")
	private void setRemoteViewsTextSize(RemoteViews views, int viewId, int textSize) {
		if (android.os.Build.VERSION.SDK_INT >= 16) {
			// 低版本不支持字体适配
			views.setTextViewTextSize(viewId, TypedValue.COMPLEX_UNIT_SP, textSize / 2);
		}
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onDataSetChanged() {
		mNewsList.clear();

		SystemClock.sleep(2000);
		mNewsList = getNews();
	}

	private List<News> getNews() {
		List<News> newsList = new ArrayList<News>();

		final int newsCount = 10;
		News news = new News();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		String dateString = null;
		// 随机新闻
		String[] newsArray = DataProvider.getRandomNewsArray(newsCount);
		// 随机新闻图片标签
		int[] randMarkArray = DataProvider.getRandomArray(newsCount);
		for (int i = 0; i < newsCount; i++) {
			news = new News();
			news.setTitle(newsArray[i]);
			dateString = sdf.format(new Date());
			news.setDate(dateString);
			news.setNewsMark(randMarkArray[i]);

			newsList.add(news);
		}

		return newsList;
	}

	@Override
	public void onDestroy() {
		mNewsList.clear();
	}
	
//	private void setNetImage(final Context context) {
//		// 对图片的特殊处理逻辑
//		Bitmap src = imageManager.obtainBitmapRemoteViews(item.img);
//		if (src != null) {
//			item.bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Config.ARGB_8888);
//			Canvas c = new Canvas(item.bitmap);
//			c.drawBitmap(src, new Matrix(), null);
//		}
//	}

}