package com.temoa.gankio.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.temoa.gankio.Constants;
import com.temoa.gankio.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        toolbar.setTitle(R.string.about_me);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TextView tv_weibo = (TextView) findViewById(R.id.about_tv_weibo);
        TextView tv_github = (TextView) findViewById(R.id.about_tv_github);
        TextView tv_gank = (TextView) findViewById(R.id.about_tv_gank);

        tv_weibo.setOnClickListener(this);
        tv_github.setOnClickListener(this);
        tv_gank.setOnClickListener(this);
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
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}
