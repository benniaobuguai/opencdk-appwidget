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
import android.widget.Toast;

import com.opencdk.appwidget.activity.NewsListActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

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
            actionBar.setTitle(R.string.app_name);
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

        private Button btnNewsList;
        private Button btnOpenCDKHome;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_main_fm, container, false);

            btnNewsList = (Button) rootView.findViewById(R.id.btn_news_list);
            btnOpenCDKHome = (Button) rootView.findViewById(R.id.btn_opencdk_home);

            btnNewsList.setOnClickListener(this);
            btnOpenCDKHome.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_news_list:
                    onNewsListClick();
                    break;
                case R.id.btn_opencdk_home:
                    onOpenCDKHomeClick();
                    break;

            }
        }

        private void onNewsListClick() {
            Intent intent = new Intent(getActivity(), NewsListActivity.class);
            startActivity(intent);
        }

        private void onOpenCDKHomeClick() {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("http://www.opencdk.com");
                intent.setData(uri);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Not found a browser!", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
