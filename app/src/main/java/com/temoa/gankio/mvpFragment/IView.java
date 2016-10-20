package com.temoa.gankio.mvpFragment;

import com.temoa.gankio.bean.NewGankData.Results;

import java.util.List;

/**
 * Created by Temoa
 * on 2016/10/20 17:31
 */

public interface IView {
    void getData(String type, List<Results> data);

    void getMoreData(String type, List<Results> data);

    void showToast(String msg);

    void hideProgressbar(boolean state);
}
