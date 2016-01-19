package com.temoa.bellezza.presenter;

import com.temoa.bellezza.listener.OnFinishedListener;
import com.temoa.bellezza.model.GankAndroidTipsData;
import com.temoa.bellezza.model.GankAndroidTipsDataImpl;
import com.temoa.bellezza.view.MainView;

import java.util.List;

public class MainPresenterImpl implements MainPresenter,OnFinishedListener {

    private MainView mainView;
    private GankAndroidTipsData gankAndroidTipsData;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        gankAndroidTipsData = new GankAndroidTipsDataImpl();
    }

    @Override
    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }
        gankAndroidTipsData.loadAndroidTipsData(this);
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onItemClick(int position) {
        if (mainView != null){
            mainView.showToast(String.format("Position %d clicked",position));
        }
    }

    @Override
    public void onFinished(List<String> item) {
        if (mainView == null){
            return;
        }
        mainView.getItem(item);
        mainView.hideProgress();
    }
}
