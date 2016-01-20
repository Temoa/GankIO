package com.temoa.bellezza.presenter;

import com.temoa.bellezza.listener.OnFinishedListener;
import com.temoa.bellezza.model.GankTipsData;
import com.temoa.bellezza.model.IGankTipsData;
import com.temoa.bellezza.view.IMainView;

import java.util.List;

public class MainViewPresenter implements OnFinishedListener {

    private IMainView mainView;
    private IGankTipsData gankTipsData;

    public MainViewPresenter(IMainView mainView) {
        this.mainView = mainView;
        this.gankTipsData = new GankTipsData();
    }

    public void onItemClick(int position) {
        if (mainView == null) {
            return;
        }
        mainView.toWebActivity(gankTipsData.loadTipsUrl().get(position));
    }

    public void onResume() {
        if (mainView == null) {
            return;
        }
        mainView.showProgress();
        gankTipsData.loadTipsData(this);
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
