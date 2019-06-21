package com.temoa.gankio.business;

import com.kunminx.core.bus.IRequest;
import com.temoa.gankio.bean.NewGankData;

/**
 * Created by lai
 * on 2019/6/21.
 */
public interface IGankRequest extends IRequest {

  void getGank(String type, int flag);

  void getMoreGank(String type, int page);

  void saveGank2Db(String type, NewGankData.Results data);
}
