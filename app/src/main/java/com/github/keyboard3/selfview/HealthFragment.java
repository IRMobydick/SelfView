package com.github.keyboard3.selfview;

import android.os.Bundle;

/**
 * QQ 健康
 *
 * @author keyboard
 */
public class HealthFragment extends BaseFragment {

    public static HealthFragment newInstance() {
        Bundle args = new Bundle();
        HealthFragment fragment = new HealthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initContentViewId() {
        return R.layout.fragment_qqhelth;
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
