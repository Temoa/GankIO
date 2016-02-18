package com.temoa.bellezza.view;

import java.util.List;

public interface IMainView {

    void showProgress();

    void hideProgress();

    void getItem(List<String> titleList);

    void getPhoto(List<String> photoUrlList);

    void loadMoreItem();

    void showToast(String str);

    void toWebActivity(String url);
}
