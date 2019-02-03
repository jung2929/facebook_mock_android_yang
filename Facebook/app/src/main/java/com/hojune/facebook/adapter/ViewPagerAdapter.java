package com.hojune.facebook.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.hojune.facebook.fragment.MyProfileFragment;
import com.hojune.facebook.fragment.TimeLineFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    static final int PAGE_NUMBER = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        Log.e("ViewPagerAdapter","getItem() 호출");


        switch(position){
            case 0:
                Log.e("getItem", String.valueOf(MyProfileFragment.newInstance().show()));
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
