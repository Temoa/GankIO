package com.temoa.gankio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.temoa.gankio.tools.LogUtils;

/**
 * Created by Temoa
 * on 2016/11/9 10:30
 */

public abstract class BaseFragment extends Fragment {

    private boolean hasCreateView = false;
    private boolean isFragmentVisible = false;

    protected View rootView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (rootView == null) {
            return;
        }

        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }

        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
        LogUtils.d("BaseFragment", "onFragmentVisibleChange() called with: isVisible = [" + isVisible + "]");
    }
}
