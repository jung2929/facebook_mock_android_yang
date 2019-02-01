package com.hojune.facebook.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout mTabLayout = (TabLayout)findViewById(R.id.tabs);
        ViewPager mViewPager = (ViewPager)findViewById(R.id.viewpager);

        mViewPager.setAdapter(new ViewPagerAdapter(mFragmentManager));
        mTabLayout.setupWithViewPager(mViewPager); //뷰페이저와 탭레이아웃 연동
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
                    Log.e("onActivityResult", editMessage);
                    MyProfileFragment myProfileFragment = (MyProfileFragment) mFragmentManager.findFragmentById(R.id.viewpager);//프래그먼트가 쓰이는 레이아웃의 id를 쓰랬어
                    //그럼 여기서는 viewpager가 맞겠지??

                    myProfileFragment.UpdateAdapter(editMessage); //이부분이 잘 되는지는 모르겠다.. 흠흠흠
            }
        }
    }
}
