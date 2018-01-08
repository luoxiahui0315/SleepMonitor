package com.lzhz.lxh.sleepmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.decompression.DecompressionFragment;
import com.lzhz.lxh.sleepmonitor.home.HomeFragment;
import com.lzhz.lxh.sleepmonitor.home.adapter.FragmentAdapter;
import com.lzhz.lxh.sleepmonitor.relatives.RelativesFragment;
import com.lzhz.lxh.sleepmonitor.sideslip.PersonalDetailsActivity;
import com.lzhz.lxh.sleepmonitor.sideslip.VersionsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.nav_view)
    NavigationView navView;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
    }
    private void getHead(){

    }

    @Override
    public void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
//                当第一项被选择时BlankFragmentOne显示，以此类推
                    switch (itemId) {
                        case R.id.btm_nav_item1:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.btm_nav_item2:
                            viewPager.setCurrentItem(1);
                        case R.id.btm_nav_item3:
                            viewPager.setCurrentItem(2);
                            break;
                        case R.id.btm_nav_item4:
                            viewPager.setCurrentItem(3);
                            break;
                    }
                    return true;
                }
            });
            setupViewPager(viewPager);
        }
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        super.initData();
    }


    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new DecompressionFragment());
        adapter.addFragment(new RelativesFragment());
        viewPager.setAdapter(adapter);

        //viewPager 改变监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //个人信息
            case R.id.it_plm:
                startActivity(new Intent(this, PersonalDetailsActivity.class));
                break;
            case R.id.it_version_information:
                startActivity(new Intent(this, VersionsActivity.class));
                break;
            case R.id.it_user_feedback:
                break;
            case R.id.it_usinghelp:
                break;
            case R.id.it_about_us:
                break;
            case R.id.it_log_out:
                this.finish();
                break;

        }

        return true;
    }
}