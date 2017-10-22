package com.github.keyboard3.selfview;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

/**
 * @author keyboard3
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView nvLeft;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void initView() {
        MainActivity.this.replaceFragment(R.id.content_main, new HealthFragment());
        nvLeft = findViewById(R.id.nv_left);
        drawerLayout = findViewById(R.id.drawer_layout);
        setupDrawer();
        initNavigationView();
    }

    private void initNavigationView() {
        nvLeft.setNavigationItemSelectedListener(this);
        nvLeft.getMenu().getItem(0).setChecked(true);
    }

    /**
     * drawerLayout与toolbar进行关联
     */
    private void setupDrawer() {
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();//将默认的箭头改成三杠
    }

    /**
     * 左侧点击打开左侧栏
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected int initViewId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.heal_menu:
                MainActivity.this.replaceFragment(R.id.content_main, fragment);
                break;
        }
        item.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }
}