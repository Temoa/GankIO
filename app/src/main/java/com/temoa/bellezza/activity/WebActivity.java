package com.temoa.bellezza.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.temoa.bellezza.R;
import com.temoa.bellezza.presenter.WebActivityPresenter;
import com.temoa.bellezza.view.IWebActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity implements IWebActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webView)
    WebView webView;

    private Intent intent;
    private WebActivityPresenter webActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.write));
        webView.setWebViewClient(new WebClient());
        intent = getIntent();
        webActivityPresenter = new WebActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webActivityPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webActivityPresenter.onDestroy();
    }

    @Override
    public void loadUrl() {
        String url = intent.getStringExtra("url");
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            webView.goBack();
            return true;
        }else{
            finish();
        }
        return false;
    }

    public class WebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
