package com.temoa.bellezza.presenter;

import com.temoa.bellezza.view.IWebActivity;

public class WebActivityPresenter {

    private IWebActivity iWebActivity;

    public WebActivityPresenter(IWebActivity iWebActivity) {
        this.iWebActivity = iWebActivity;
    }

    public void onCreate() {
        if (iWebActivity == null) {
            return;
        }
        iWebActivity.loadUrl();
    }

    public void onDestroy() {
        iWebActivity = null;
    }


}
