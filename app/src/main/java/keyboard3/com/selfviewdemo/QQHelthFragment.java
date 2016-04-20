package keyboard3.com.selfviewdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import keyboard3.com.selfviewdemo.widget.CircleView;

public class QQHelthFragment extends BaseFragment {
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.view_line)
    View viewLine;
    @Bind(R.id.circle)
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
        viewLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//设置虚线
    }
    @OnClick(R.id.circle)
    public void onClick() {
        circle.startCustomAnimation();
    }
}
