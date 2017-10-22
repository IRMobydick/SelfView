package com.github.keyboard3.selfview;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * 基类
 *
 * @author keyboard3
 */
public abstract class BaseActivity extends AppCompatActivity {
    public String TAG = "gcy";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public Fragment fragment;
    public Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initViewId());
        initBar();
        fragment = FlipboardFragment.newInstance();
        initView();
    }

    protected void replaceFragment(int resIs, Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(resIs, fragment);
        fragmentTransaction.commit();
    }

    private void initBar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract void initView();

    protected abstract int initViewId();
}
