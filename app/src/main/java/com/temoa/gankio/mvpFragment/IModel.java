package com.temoa.gankio.mvpFragment;

import com.temoa.gankio.bean.NewGankData;

/**
 * Created by Temoa
 * on 2016/10/20 17:31
 */

public interface IModel {

    void getData(String type, int flag, NetworkListener listener);

    void getMoreData(String type, int page, NetworkListener listener);

    boolean addDataToDb(NewGankData.Results data);

    void cancel();
}
