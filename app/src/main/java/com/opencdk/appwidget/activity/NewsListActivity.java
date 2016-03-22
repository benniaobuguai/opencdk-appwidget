package com.opencdk.appwidget.activity;

import android.os.Bundle;

import com.opencdk.appwidget.BaseActivity;
import com.opencdk.appwidget.R;

public class NewsListActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_news_list_main);

		setTitle(R.string.news_main_title);
	}

}
