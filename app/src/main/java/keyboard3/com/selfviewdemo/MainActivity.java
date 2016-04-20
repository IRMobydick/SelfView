package keyboard3.com.selfviewdemo;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import butterknife.Bind;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nv_left)
    NavigationView nvLeft;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void initView() {
        setupDrawer();
        initNavigationView();
    }
    private void initNavigationView(){
        MainActivity.this.replaceFragment(R.id.content_main,qqHelthFragment);
        nvLeft.setNavigationItemSelectedListener(this);//设置点击监听
        nvLeft.getMenu().getItem(0).setChecked(true);//设置默认数据
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
        switch (item.getItemId()){
            case R.id.heal_menu:
                MainActivity.this.replaceFragment(R.id.content_main,qqHelthFragment);
                break;
        }
        item.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }
}