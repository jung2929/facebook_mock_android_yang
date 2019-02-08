package com.hojune.facebook.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hojune.facebook.R;
import com.hojune.facebook.adapter.ViewPagerAdapter;
import com.hojune.facebook.fragment.MyProfileFragment;

public class MainActivity extends AppCompatActivity {

    Dialog mDialog;


    String editMessage;
    FragmentManager mFragmentManager = getSupportFragmentManager();
    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mFragmentManager);
    MyProfileFragment myProfileFragment = MyProfileFragment.newInstance();


    LinearLayout otherSpace;
    LinearLayout deleteSpace;

    public boolean isPageOpen; //애니메이션 작동 여부를 판단할 변수


    public int deletePosition; // 삭제를 위해서 listview로부터 올라온 int 값. 이 변수로 리스트뷰 안에있는 아이템에 접근하고
    // 그 아이템에 들어있는 현재 아이템의 위치정보를 참조해서 해당 아이템 삭제
    // 간단하게는 리스트뷰 클릭 리스너를 사용하면 되지만 난 아이템이 클릭되는것과 아이템 안에있는 이미지를 클릭했을때를
    // 구분짓고 싶었음

    Button btDelete; //삭제하기 버튼

    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDialog = new Dialog(MainActivity.this);
        mTabLayout = (TabLayout)findViewById(R.id.tabs);
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager); //뷰페이저와 탭레이아웃 연동

        final Animation translateBottom = AnimationUtils.loadAnimation(MainActivity.this,R.anim.translate_bottom);
        //translateBottom.setAnimationListener(listener);

        createDialog();
        mDialog.show();

        btDelete = (Button)findViewById(R.id.delete_button);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProfileFragment.timeLineItemAdapter.DeleteItem(deletePosition);
            }
        });



        otherSpace = (LinearLayout)findViewById(R.id.other_space);
        deleteSpace = (LinearLayout)findViewById(R.id.delete_space);
        otherSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MainActivity","onClick 리스너");
                deleteSpace.startAnimation(translateBottom);
            }
        });



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
                    myProfileFragment.timeLineItemAdapter.AddItem(editMessage);


                    /**
                     * 이 코드 힘들게 검색해서 알아낸건데 사실 그럴 필요가 없었음.
                     * myProfileFragment를 싱글톤으로 구현하면 바로바로 참조 가능한거니까..
                     * wow.. 개꿀
                     */
                    //myProfileFragment = (MyProfileFragment)viewPagerAdapter.getItem(0);
                    //myProfileFragment.AddTimeLineItemAdapter(editMessage); //이부분이 잘 되는지는 모르겠다.. 흠흠흠
            }
        }
    }

    private void createDialog() {
        /*final View innerView = getLayoutInflater().inflate(R.layout.dialog, null);

        mDialog = new Dialog(this);
        mDialog.setTitle("Title");
        mDialog.setContentView(innerView);

        // Back키 눌렀을 경우 Dialog Cancle 여부 설정
        mDialog.setCancelable(true);

        // Dialog 생성시 배경화면 어둡게 하지 않기
        mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // Dialog 밖을 터치 했을 경우 Dialog 사라지게 하기
        // mDialog.setCanceledOnTouchOutside(true);

        // Dialog 밖의 View를 터치할 수 있게 하기 (다른 View를 터치시 Dialog Dismiss)
        //mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                //WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // Dialog 자체 배경을 투명하게 하기
//      mDialog.getWindow().setBackgroundDrawable
//              (new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Dialog Cancle시 Event 받기
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "cancle listener",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Dialog Show시 Event 받기
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "show listener",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Dialog Dismiss시 Event 받기
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "dismiss listener",
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        LayoutInflater inflater = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE));

        View zoomDiaglogView = inflater.inflate(R.layout.dialog, null);

        //이름 겹칠텐데 문제 없으려나
        Button deleteButton = (Button)zoomDiaglogView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener();
        Animation translate_top = AnimationUtils.loadAnimation(this,R.anim.translate_top);
        zoomDiaglogView.startAnimation(translate_top);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.addContentView(zoomDiaglogView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        mDialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void dismissDialog() {
        if(mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}
