package com.hojune.facebook.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hojune.facebook.fragment.MyProfileFragment;
import com.hojune.facebook.fragment.TimeLineFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    static final int PAGE_NUMBER = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return MyProfileFragment.newInstance();

            case 1:
                return TimeLineFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "내 프로필";
            case 1:
                return "타임라인";
            default:
                return null;
        }
    }
}
