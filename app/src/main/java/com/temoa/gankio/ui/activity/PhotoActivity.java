package com.temoa.gankio.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.temoa.gankio.R;

public class PhotoActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "url";

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Intent intent = getIntent();
        if (intent != null)
            url = intent.getStringExtra(EXTRA_URL);

        initToolbar();
        initPhoto();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.photo_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initPhoto() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.photo_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ImageView img = (ImageView) findViewById(R.id.photo_img);
        Glide.with(this)
                .load(url)
                .dontAnimate()
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(img) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public static void launch(Activity activity, String url) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra(EXTRA_URL, url);
        activity.startActivity(intent);
    }
}
