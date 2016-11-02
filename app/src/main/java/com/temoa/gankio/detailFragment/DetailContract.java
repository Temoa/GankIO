package com.temoa.gankio.detailFragment;

import com.temoa.gankio.BasePresenter;
import com.temoa.gankio.BaseView;
import com.temoa.gankio.bean.NewGankData;

import java.util.List;

/**
 * Created by Temoa
 * on 2016/11/2 12:53
 */

interface DetailContract {

    interface View extends BaseView<Presenter> {

        void getData(String type, List<NewGankData.Results> data);

        void getMoreData(String type, List<NewGankData.Results> data);

        void showToast(String msg);

        void hideProgressbar(boolean state);
    }

    interface Presenter extends BasePresenter {

        void getNewData(String type);

        void getMoreData(String type, int page);

        void saveDataTODb(NewGankData.Results data);
    }
}
