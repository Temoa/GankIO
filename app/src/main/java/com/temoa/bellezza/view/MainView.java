package com.temoa.bellezza.view;

import java.util.List;

public interface MainView {

    void showProgress();

    void hideProgress();

    void getItem(List<String> item);

    void showToast(String str);
}
