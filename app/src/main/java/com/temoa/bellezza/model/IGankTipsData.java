package com.temoa.bellezza.model;

import com.temoa.bellezza.listener.OnFinishedListener;

import java.util.List;

public interface IGankTipsData {

    void loadTipsData(OnFinishedListener listener);
    List<String> loadTipsUrl();
}
