package com.temoa.bellezza.view;

import java.util.List;

public interface IMainView {

    void showProgress();

    void hideProgress();

    void getItem(List<String> items);

    void loadMoreItem();

    void showToast(String str);

    void toWebActivity(String url);
}
