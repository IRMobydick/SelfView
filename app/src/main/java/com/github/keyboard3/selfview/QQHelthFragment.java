package com.github.keyboard3.selfview;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.github.keyboard3.selfview.widget.CircleView;

public class QQHelthFragment extends BaseFragment {
    TextView tvNum;
    View viewLine;
    CircleView circle;
    private int process = 13140;

    public static QQHelthFragment newInstance() {
        Bundle args = new Bundle();
        QQHelthFragment fragment = new QQHelthFragment();
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
       /* circle = getView().findViewById(R.id.circle);
        viewLine = getView().findViewById(R.id.view_line);
        tvNum = getView().findViewById(R.id.tv_num);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circle.startCustomAnimation();
            }
        });
        circle.postDelayed(new Runnable() {
            @Override
            public void run() {
                circle.startCustomAnimation();
            }
        }, 1000);
        circle.setProcessChangeLinstener(new CircleView.processChangeLinstener() {
            @Override
            public void onProcessChange(float interpolatedTime) {
                tvNum.setText((int) (process * interpolatedTime) + "");
            }
        });
        viewLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//设置虚线*/
    }
}
