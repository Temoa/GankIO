package com.temoa.bellezza.presenter;

import com.temoa.bellezza.listener.OnFinishedListener;
import com.temoa.bellezza.model.GankAPIService;
import com.temoa.bellezza.view.IMainView;

import java.util.List;

public class MainViewPresenter implements OnFinishedListener {

    private int page = 1;
    private IMainView mainView;
    private GankAPIService gankAPIService;

    public MainViewPresenter(IMainView mainView) {
        this.mainView = mainView;
        this.gankAPIService = new GankAPIService();
    }

    public void onCreate() {
        if (mainView == null) {
            return;
        }
        mainView.showProgress();
        loadItem();
    }

    public void onDestroy() {
        mainView = null;
    }

    //ListView item的点击事件——跳转到WebView打开相应的网页
    public void onItemClick(int position) {
        if (mainView == null) {
            return;
        }
        mainView.toWebActivity(gankAPIService.loadTipsUrls().get(position));
    }

    //初始化和下拉加载数据
    public void loadItem(){
        gankAPIService.loadTipsData(20, 1, this);
    }
    //上拉加载更多数据
    public void addMoreItem(){
        page ++;
        gankAPIService.loadMoreTipsData(10,page,this);
    }
    //加载数据完成后显示在ListView上
    @Override
    public void onLoadFinished(List<String> item) {
        if (mainView == null) {
            return;
        }
        mainView.getItem(item);
        mainView.hideProgress();
    }
    //加载更多数据完成后显示在ListView上
    @Override
    public void onLoadMoreFinished(List<String> items) {
        if (mainView == null) {
            return;
        }
        mainView.loadMoreItem();
    }

}
