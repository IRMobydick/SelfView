package com.github.keyboard3.selfview;

import android.os.Bundle;

public class QQHealthFragment extends BaseFragment {

    public static QQHealthFragment newInstance() {
        Bundle args = new Bundle();
        QQHealthFragment fragment = new QQHealthFragment();
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
