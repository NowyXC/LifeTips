package com.nowy.android.lifetips.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nowy.android.basiclib.base.BaseActivity;
import com.nowy.android.basiclib.utils.Tips;
import com.nowy.android.lifetips.R;
import com.nowy.android.lifetips.adapter.TabFragmentAdapter;
import com.nowy.android.lifetips.ui.fragment.EmptyFragment;
import com.nowy.android.lifetips.ui.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nowy on 2016/1/25.
 */
public class HomeActivity extends BaseActivity {
    private String[] titles= new String[]{"全部","热门","情感","科技","段子","搞笑"};
    private ActionBarDrawerToggle mDrawerToggle;
    private ViewPager mViewPager;
    private TabLayout mTl_title;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void initViews() {


        setContentView(R.layout.home);


        initToolbar();
        //drawerlayout
        initDrawerLayout();
        initNavigation();
        initTabLayout();

        initViewPager();
    }




    private void initToolbar(){
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("title");
        toolbar.setSubtitle("subTitle");
//        toolbar.setNavigationIcon(android.R.drawable.ic_menu_view);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(menuItemClickListener);
        //toolbar.setNavigationIcon(android.R.drawable.ic_menu_view);
        // toolbar.setNavigationOnClickListener(navigationClickListener);
    }


    private void initDrawerLayout(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open,R.string.drawer_close);

        mDrawerLayout.setDrawerListener(new MyDrawerListener());
    }


    private void initCoor(){
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_layout);
        //coordinatorLayout.set
    }


    private void initNavigation(){
        NavigationView navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(navigationItemSelectedListener);
    }


    private void initTabLayout() {
        mTl_title = (TabLayout) findViewById(R.id.tl_title);

        mTl_title.setTabMode(TabLayout.MODE_SCROLLABLE);
        for(String title :titles)
            mTl_title.addTab(mTl_title.newTab().setText(title));


    }

    private void initViewPager(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TabFragment.newInstance(null));
        fragments.add(EmptyFragment.newInstance());
        fragments.add(EmptyFragment.newInstance());
        fragments.add(EmptyFragment.newInstance());
        fragments.add(EmptyFragment.newInstance());
        fragments.add(EmptyFragment.newInstance());
        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(
                getSupportFragmentManager(),fragments,titles);

        mViewPager.setAdapter(tabFragmentAdapter);
        mTl_title.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTl_title.setTabsFromPagerAdapter(tabFragmentAdapter);//给Tabs设置适配器
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
        mDrawerToggle.syncState();
    }

    /** 菜单键点击的事件处理 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }



    /** 设备配置改变时 */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    View.OnClickListener navigationClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Tips.showShort(HomeActivity.this,"nav");
        }
    };


    Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){

                case R.id.action_edit:

                    break;
                case R.id.action_share:
                    break;
                case R.id.action_settings:
                    break;

            }

            Tips.showShort(HomeActivity.this,item.getTitle());
            return true;
        }
    };


    /** drawer的监听 */
    private class MyDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {// 打开drawer
            mDrawerToggle.onDrawerOpened(drawerView);//开关状态改为opened
        }

        @Override
        public void onDrawerClosed(View drawerView) {// 关闭drawer
            mDrawerToggle.onDrawerClosed(drawerView);//开关状态改为closed
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {// drawer滑动的回调
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {// drawer状态改变的回调
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }


    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
                                        = new NavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            item.setChecked(true); // 改变item选中状态
            setTitle(item.getTitle()); // 改变页面标题，标明导航状态
            mDrawerLayout.closeDrawers(); // 关闭导航菜单
            return true;
        }
    };

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


}
