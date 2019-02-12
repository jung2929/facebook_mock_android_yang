package com.hojune.facebook.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hojune.facebook.ConnectToWonnie;
import com.hojune.facebook.R;
import com.hojune.facebook.adapter.TimeLineItemAdapter;
import com.hojune.facebook.adapter.ViewPagerAdapter;
import com.hojune.facebook.fragment.MyProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * 다른 데서 이 액티비티를 참조하게 하기위한 싱글톤 구현
     * 에러나면 여기 그냥 지워 보료~
     */
    /*private static MainActivity mainActivity;

    public static MainActivity newInstance(){

        if(mainActivity == null){
            mainActivity = new MainActivity();
        }

        return mainActivity;
    }*/

    Handler mHandler = new Handler();

    ConnectToWonnie connectToWonnie = new ConnectToWonnie();

    Context mContext = this;

    public Dialog mDialog;

    EditText etName;

    ActionBar abar;


    String editMessage;
    TextView tvHometown;
    TextView tvJob;
    TextView tvNickname;

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

        myProfileFragment.timeLineItemAdapter.DeleteAll();

        abar = getSupportActionBar();

        /*tvHometown = (TextView)findViewById(R.id.hometown);
        tvJob = (TextView)findViewById(R.id.job);
        tvNickname = (TextView)findViewById(R.id.nickname);*/

        mDialog = new Dialog(MainActivity.this);
        mTabLayout = (TabLayout)findViewById(R.id.tabs);
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager); //뷰페이저와 탭레이아웃 연동

        final Animation translateBottom = AnimationUtils.loadAnimation(MainActivity.this,R.anim.translate_bottom);
        //translateBottom.setAnimationListener(listener);

        createDialog();
        //mDialog.show();





        otherSpace = (LinearLayout)findViewById(R.id.other_space);
//        deleteSpace = (LinearLayout)findViewById(R.id.delete_space);
        otherSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MainActivity","onClick 리스너");
                deleteSpace.startAnimation(translateBottom);
            }
        });

        /**
         * 핵심코드!! 화면 시작하면 이 아이디로 적은 게시글 불러오는 기능임
         * 지운다음에 추가해야지만이 addtimeline에서 복귀한 후에 새로운 리스트가 생성됨
         */
        connectToWonnie.MyTimeLine(mContext, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                    Log.e("내 의도는 바뀐 jwt", pref.getString("jwt","empty"));

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    for(int i=0; i<jsonObject.getJSONArray("data").length();i++){
                        String message = jsonObject.getJSONArray("data").getJSONObject(i).getString("content");
                        String date = jsonObject.getJSONArray("data").getJSONObject(i).getString("date");
                        String name = jsonObject.getJSONArray("data").getJSONObject(i).getString("name");
                        myProfileFragment.timeLineItemAdapter.AddItem(message, date, name);
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
                Log.e("현재 arraylist", "아이템 갯수"+myProfileFragment.timeLineItemAdapter.getCount());
            }
        });


    }

    public void refresh(){
        viewPagerAdapter.notifyDataSetChanged();
    }

    //액션바 꾸미기 위한 함수
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        View v = menu.findItem(R.id.menu_search).getActionView();
        if (v != null) {
            etName = (EditText) v.findViewById(R.id.editText);

            if (etName != null) {
                etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (event == null || event.getAction() == KeyEvent.ACTION_UP) {
                            // 검색 메소드 호출
                            Toast.makeText(MainActivity.this, "afaf", Toast.LENGTH_SHORT).show();

                            // 키패드 닫기
                            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }

                        return (true);
                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "ActionView is null.", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch(curId){
            case R.id.menu_search:
                Toast.makeText(this, "친구목록 검색창 띄움", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,FindFriend.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //이상한 에러나면 여기 바로 지워버리자
    protected void onResume() {
        super.onResume();
        //connectToWonnie.ReadProfile();
    }

   public void CallWriteProfile(){
       Intent intent = new Intent(this, WriteProfileActivity.class);
       startActivityForResult(intent,500);
   }

   public void CallFriendList(){
       Intent intent = new Intent(this, FriendListActivity.class);
       startActivity(intent);
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED){
            if (requestCode == 500) {
                String a = data.getStringExtra("home");
                String b = data.getStringExtra("job");
                String c = data.getStringExtra("nickname");
                myProfileFragment.SetFunc(a,b,c);
                /*tvHometown.setText(data.getStringExtra("home"));
                tvJob.setText(data.getStringExtra("job").toString());
                tvNickname.setText(data.getStringExtra("nickname").toString());*/
            }
        }
        //아하 저 3개의 텍스트는 mainactivity에 있는게아니라 fragment에 있는것임
    }

    public void CallAddTimeLine(){

        Intent intent = new Intent(this, AddTimeLineActivity.class);
        startActivity(intent);
    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case 100:
                    *//**
                     * 핵심코드!! 화면 시작하면 이 아이디로 적은 게시글 불러오는 기능임
                     * 지운다음에 추가해야지만이 addtimeline에서 복귀한 후에 새로운 리스트가 생성됨
                     *//*

                    *//*connectToWonnie.MyTimeLine(mContext, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try{
                                JSONObject jsonObject = new JSONObject(response.body().string());

                                //Log.e("jwt값 확인", )
                                Log.e("onActivityResult에서json",jsonObject.getJSONArray("data").toString());
                                for(int i=0; i<jsonObject.getJSONArray("data").length();i++){
                                    final String message = jsonObject.getJSONArray("data").getJSONObject(i).getString("content");
                                    final String date = jsonObject.getJSONArray("data").getJSONObject(i).getString("date");
                                    final String name = jsonObject.getJSONArray("data").getJSONObject(i).getString("name");
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    myProfileFragment.timeLineItemAdapter.AddItem(message, date, name);
                                                }
                                            });
                                        }
                                    }).start();
                                }
                            }
                            catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
*//*

                    *//**
                     * 이 코드 힘들게 검색해서 알아낸건데 사실 그럴 필요가 없었음.
                     * myProfileFragment를 싱글톤으로 구현하면 바로바로 참조 가능한거니까..
                     * wow.. 개꿀
                     *//*
                    //myProfileFragment = (MyProfileFragment)viewPagerAdapter.getItem(0);
                    //myProfileFragment.AddTimeLineItemAdapter(editMessage); //이부분이 잘 되는지는 모르겠다.. 흠흠흠
            }
        }
    }*/

    private void createDialog() {
        LayoutInflater inflater = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE));

        View zoomDiaglogView = inflater.inflate(R.layout.dialog, null);

        //이름 겹칠텐데 문제 없으려나
        Button deleteButton = (Button)zoomDiaglogView.findViewById(R.id.delete_button);


        //이걸로 리스트뷰 삭제하는 기능 만들자.
        //deleteButton.setOnClickListener();

        Animation translate_top = AnimationUtils.loadAnimation(this,R.anim.translate_top);
        zoomDiaglogView.startAnimation(translate_top);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.addContentView(zoomDiaglogView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btDelete = (Button)zoomDiaglogView.findViewById(R.id.delete_button);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProfileFragment.timeLineItemAdapter.DeleteItem(deletePosition);
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(mContext, "exit", Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void dismissDialog() {
        /*if(mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }*/

    }

}
