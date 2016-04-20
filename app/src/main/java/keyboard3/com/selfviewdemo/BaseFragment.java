package keyboard3.com.selfviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Desc:
 * Author: ganchunyu
 * Date: 2016-04-20 10:20
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mRootView==null){
            mRootView = inflater.inflate(initContentViewId(),container,false);
            ButterKnife.bind(this,mRootView);
            initView();
        }else{
            ButterKnife.bind(this,mRootView);
        }
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(mRootView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected void initData(){};

    protected void initView(){}
    protected abstract int initContentViewId();
}
