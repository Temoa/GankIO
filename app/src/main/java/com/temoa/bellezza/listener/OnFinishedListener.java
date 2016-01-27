package com.temoa.bellezza.listener;

import java.util.List;

public interface OnFinishedListener {

    void onLoadFinished(List<String> items);

    void onLoadMoreFinished(List<String> items);
}