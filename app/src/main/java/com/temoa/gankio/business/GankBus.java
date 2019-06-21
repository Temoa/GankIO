package com.temoa.gankio.business;

import com.kunminx.core.bus.BaseBus;

/**
 * Created by lai
 * on 2019/6/21.
 */
public class GankBus extends BaseBus {

  public static IGankRequest gank() {
    return (IGankRequest) getRequest(IGankRequest.class);
  }
}
