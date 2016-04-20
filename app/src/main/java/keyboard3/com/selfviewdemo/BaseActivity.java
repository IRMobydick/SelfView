package keyboard3.com.selfviewdemo;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Desc:
 * Author: ganchunyu
 * Date: 2016-04-20 10:32
 */
public abstract class BaseActivity extends AppCompatActivity {
    public  String TAG="gcy";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public  QQHelthFragment qqHelthFragment;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initViewId());
        ButterKnife.bind(this);
        initBar();
        qqHelthFragment = QQHelthFragment.newInstance();
        initView();
    }
    protected void replaceFragment(int resIs,Fragment fragment){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(resIs,fragment);
        fragmentTransaction.commit();
    }
    private void initBar(){
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected abstract int initViewId();
}
