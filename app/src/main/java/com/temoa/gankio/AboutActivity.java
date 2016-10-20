package com.temoa.gankio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("关于我");

        TextView tv_weibo = (TextView) findViewById(R.id.about_tv_weibo);
        TextView tv_github = (TextView) findViewById(R.id.about_tv_github);
        TextView tv_gank = (TextView) findViewById(R.id.about_tv_gank);

        tv_weibo.setOnClickListener(this);
        tv_github.setOnClickListener(this);
        tv_gank.setOnClickListener(this);

        tv_weibo.setText("Weibo: " + Constants.URL_Weibo);
        tv_github.setText("Github: " + Constants.URL_Github);
        tv_gank.setText("数据来源: " + Constants.URL_GANK);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.about_tv_weibo:
                openUrl(Constants.URL_Weibo);
                break;
            case R.id.about_tv_github:
                openUrl(Constants.URL_Github);
                break;
            case R.id.about_tv_gank:
                openUrl(Constants.URL_GANK);
                break;
        }
    }

    private void openUrl(String uri) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}
