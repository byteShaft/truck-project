package com.byteshaft.groupedirectouest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.byteshaft.groupedirectouest.fragments.FormFragment;
import com.byteshaft.groupedirectouest.fragments.MainTab;
import com.byteshaft.groupedirectouest.fragments.WebViewFragment;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    private PagerAdapter mDemoCollectionPagerAdapter;
    private ViewPager mViewPager;
    private Fragment mFragment;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });

        for (int i = 0; i < 2; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setCustomView(getLayout(i+1))
                            .setTabListener(this));
        }
        mDemoCollectionPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }

    private int getLayout(int value) {
        switch (value) {
            case 1:
                return R.layout.custom_truck_tab;
            case 2:
                return R.layout.infolayout;
            default:
                return R.layout.custom_truck_tab;
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onBackPressed() {
        if (FormFragment.formLayoutShown) {
            MainTab.mButton.setVisibility(View.VISIBLE);
            setTitle(R.string.tow_me);
            FormFragment.formLayoutShown = false;
        }
        if (WebViewFragment.sWebViewOpen && mViewPager.getCurrentItem() == 1) {
            if (WebViewFragment.sWebView.canGoBack()) {
                WebViewFragment.sWebView.goBack();
            } else {
                super.onBackPressed();
                WebViewFragment.sWebViewOpen = false;
            }
        } else {
            super.onBackPressed();
            WebViewFragment.sWebViewOpen = false;

        }
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int num) {
            mFragment = new Fragment();
            switch (num) {
                case 0:
                    mFragment = new MainTab();
                    break;
                case 1:
                    mFragment = new WebViewFragment();
                    break;
                default:
                    mFragment = new MainTab();
            }
            return mFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }
}