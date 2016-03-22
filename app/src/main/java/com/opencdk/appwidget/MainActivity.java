package com.opencdk.appwidget;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends BaseActivity {
	
	private static final String TAG = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setDisplayShowHomeEnabled(true);

			actionBar.setDisplayHomeAsUpEnabled(false);
			actionBar.setTitle(R.string.toutiao);
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		handleIntent();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		try {
			handleIntent();
		} catch (Exception e) {
			Log.e(TAG, "", e);
		}
	}

	private void handleIntent() {
		final Intent intent = getIntent();
		if (intent != null) {
			String scheme = intent.getScheme();
			if (GConstants.SCHEME.equalsIgnoreCase(scheme)) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// String uriString = intent.getData().toString();
						try {
							Uri uri = intent.getData();
							String className = uri.getQueryParameter("className");

							Intent transferIntent = new Intent();
							transferIntent.putExtras(intent);
							transferIntent.setClassName(MainActivity.this, className);
							startActivity(transferIntent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, 300);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Official website
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment implements OnClickListener {

		private Button btnMusic;
		private Button btnVideo;
		private Button btnBrowser;

		public PlaceholderFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.layout_main_fm, container, false);

			btnMusic = (Button) rootView.findViewById(R.id.btn_music);
			btnVideo = (Button) rootView.findViewById(R.id.btn_video);
			btnBrowser = (Button) rootView.findViewById(R.id.btn_browser);

			btnMusic.setOnClickListener(this);
			btnVideo.setOnClickListener(this);
			btnBrowser.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_music:
				onMusicClick();
				break;
			case R.id.btn_video:
				onVideoClick();
				break;
			case R.id.btn_browser:
				onBrowserClick();
				break;
			}
		}

		private void onLoginClick() {
			
		}

		private void onMusicClick() {
			
		}

		private void onVideoClick() {
			
		}

		/**
		 * 点击进入浏览器
		 */
		private void onBrowserClick() {
			
		}

	}

}
