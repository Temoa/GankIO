package com.temoa.bellezza.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RollViewPagerAdapter extends StaticPagerAdapter {

    private List<String> welfareUrls = new ArrayList<>();
    public RollViewPagerAdapter(List<String> urls) {
        this.welfareUrls = urls;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        //view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(container.getContext())
                .load(welfareUrls.get(position))
                .into(view);
        return view;
    }

    @Override
    public int getCount() {
        return welfareUrls.size();
    }
}
