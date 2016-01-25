package com.temoa.bellezza.presenter;

import com.temoa.bellezza.listener.OnFinishedListener;
import com.temoa.bellezza.model.GankAPIService;
import com.temoa.bellezza.view.IMainView;

import java.util.List;

public class MainViewPresenter implements OnFinishedListener {

    private IMainView mainView;
    private GankAPIService gankAPIService;

    public MainViewPresenter(IMainView mainView) {
        this.mainView = mainView;
        this.gankAPIService = new GankAPIService();
    }

    public void onItemClick(int position) {
        if (mainView == null) {
            return;
        }
        mainView.toWebActivity(gankAPIService.loadTipsUrls().get(position));
    }

    public void onResume() {
        if (mainView == null) {
            return;
        }
        mainView.showProgress();
        gankAPIService.loadTipsData(20,1,this);
    }

    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onLoadFinished(List<String> item) {
        if (mainView == null) {
            return;
        }
        mainView.getItem(item);
        mainView.hideProgress();
    }

}
