package com.hojune.facebook.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hojune.facebook.R;
import com.hojune.facebook.adapter.ViewPagerAdapter;
import com.hojune.facebook.fragment.MyProfileFragment;

public class MainActivity extends AppCompatActivity {

    String editMessage;
    FragmentManager mFragmentManager = getSupportFragmentManager();
    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mFragmentManager);
    MyProfileFragment myProfileFragment = MyProfileFragment.newInstance();

    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout)findViewById(R.id.tabs);
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager); //뷰페이저와 탭레이아웃 연동
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPagerAdapter.notifyDataSetChanged();
    }

    public void CallAddTimeLine(){
        Intent intent = new Intent(this, AddTimeLineActivity.class);
        startActivityForResult(intent, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case 100:
                    editMessage = data.getExtras().getString("edit_message");

                    myProfileFragment = (MyProfileFragment)viewPagerAdapter.getItem(0);
                    myProfileFragment.UpdateTimeLineItemAdapter(editMessage); //이부분이 잘 되는지는 모르겠다.. 흠흠흠
            }
        }
    }
}
