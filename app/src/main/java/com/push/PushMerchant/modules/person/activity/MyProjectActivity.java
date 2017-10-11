package com.push.PushMerchant.modules.person.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.modules.person.fragment.CurrentProjectFragment;
import com.push.PushMerchant.modules.person.fragment.HistoryProjectFragment;
import com.push.PushMerchant.view.impl.IOnItemClickListener;
import com.push.PushMerchant.weight.viewpage.SimpleViewPagerIndicator;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class MyProjectActivity extends BaseToolbarActivity {
    @BindView(R.id.id_stickynavlayout_indicator)
    SimpleViewPagerIndicator idStickynavlayoutIndicator;
    @BindView(R.id.id_stickynavlayout_viewpager)
    ViewPager viewpager;
    private String[] taskArray;
    private ArrayList<Fragment> fragments;
    private FragmentPagerAdapter mAdapter;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_my_project;
    }

    @Override
    protected void initDataBeforeView() {
        taskArray = getResources().getStringArray(R.array.task_lists);
        fragments = new ArrayList<>();
    }

    @Override
    protected void initDataAfterView() {
        setDefaultBack("");
        setTitleString("我的项目");

        idStickynavlayoutIndicator.setTitles(taskArray);
        idStickynavlayoutIndicator.setiOnItemClickListenerl(new IOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (viewpager != null) {
                    viewpager.setCurrentItem(position);
                }
            }

        });
        fragments.add(CurrentProjectFragment.newInstance(0));
        fragments.add(HistoryProjectFragment.newInstance(1));
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return taskArray.length;
            }


            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

        };
        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit(taskArray.length);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                idStickynavlayoutIndicator.selectTextColor(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                idStickynavlayoutIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
