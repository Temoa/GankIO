package com.temoa.gankio.mvpFragment;

import com.temoa.gankio.bean.NewGankData.Results;

import java.util.List;

/**
 * Created by Temoa
 * on 2016/10/20 18:11
 */

public interface NetworkListener {

    void onSucceed(String type, List<Results> data);

    void onError(String type, String error);

    void onLoadMoreSucceed(String type, List<Results> data);
}
