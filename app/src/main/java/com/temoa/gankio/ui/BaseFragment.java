package com.temoa.gankio.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (!hasCreateView && getUserVisibleHint()) {
      onFragmentVisibleChange(true);
      isFragmentVisible = true;
    }
  }

  protected void onFragmentVisibleChange(boolean isVisible) {

  }
}
