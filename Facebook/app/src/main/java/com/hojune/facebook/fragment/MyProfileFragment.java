package com.hojune.facebook.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hojune.facebook.activity.MainActivity;
import com.hojune.facebook.adapter.TimeLineItemAdapter;
import com.hojune.facebook.R;
import com.hojune.facebook.custom.SlidingAnimationListener;

public class MyProfileFragment extends Fragment {
    public ListView listview;
    public TimeLineItemAdapter timeLineItemAdapter = new TimeLineItemAdapter();
    TextView name;

    TextView hometown;
    TextView job;
    TextView nickname;

    ImageView friendlist;

    MainActivity mainActivity;

    Animation translateTop;
    Animation translateBottom;



//    Context context = MyProfileFragment.this;

    /**
     * MyProfileFragment객체가 단 하나만 존재할 수 있도록 하는 코드(Singleton)
     */
    private static MyProfileFragment fragment;
    public static MyProfileFragment newInstance() {
        
        if(fragment == null){
            fragment = new MyProfileFragment();
        }

        return fragment;
    }



    public int show(){
        return timeLineItemAdapter.getCount();
    }

    public void SetFunc(String hometown, String job, String nickname){
        this.hometown.setText(hometown);
        this.job.setText(job);
        this.nickname.setText(nickname);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_my_profile,container,false);

        Button writeProfile = (Button)view.findViewById(R.id.write_profile);
        name = (TextView)view.findViewById(R.id.name);
        listview =(ListView)view.findViewById(R.id.fragment_listview);
        friendlist = (ImageView)view.findViewById(R.id.friendlist);
        listview.setAdapter(timeLineItemAdapter);

        hometown =(TextView)view.findViewById(R.id.hometown);
        job =(TextView)view.findViewById(R.id.job);
        nickname =(TextView)view.findViewById(R.id.nickname);

        //왕아아아아아아 미친 이 코드 신의한수
        mainActivity=(MainActivity)getActivity();

        //로그인 한 직후에 setListview함수가 내가 원하는 타이밍에 호출되게하기 위함
        //이렇게 안하면 로그인하고 아이템이 추가되기도 전에 이 함수를 호출해버려서 높이조절이 안되는듯??
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setListViewHeightBasedOnChildren(listview);
        Log.e("onCreateView","리스트뷰 높이조절 직후");


        ViewGroup think = (ViewGroup)view.findViewById(R.id.think);

        think.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).CallAddTimeLine();
            }
        });


        writeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).CallWriteProfile();
            }
        });

        friendlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).CallFriendList();
            }
        });


        //애니메이션 설정을 위한 변수들
        SlidingAnimationListener listener = new SlidingAnimationListener();
        translateTop = AnimationUtils.loadAnimation(getContext(), R.anim.translate_top);
        translateTop.setAnimationListener(listener);

        return view;
    }



    /**
     * 이 함수는 MainActivity를 직접 참조하고 있는게 아닐수도 있을것같음..
     * 뭔가 문제가 생길 느낌이야..
     */

    //여기서의 number는 TimeLineItemAdapter함수에서 내가 클릭한 아이템이 가지고 있는 게시글 정보임
    public void ShowOption(int number){
        mainActivity.mSweetSheet.toggle();
        mainActivity.deleteNumber = number;
        Log.e("ShowOption","deleteNumber = "+mainActivity.deleteNumber);
    }

    //여기 지난번에 adapter로 바로 가게 한걸로 기억함
    public void AddTimeLineItemAdapter(String message, String date, String name){
        //timeLineItemAdapter.AddItem(message, date, name);
        timeLineItemAdapter.notifyDataSetChanged();
        //mainActivity.refresh();

    }



    public void DeleteTimeLineItemAdapter(int position){
        timeLineItemAdapter.DeleteItem(position);
        timeLineItemAdapter.notifyDataSetChanged();
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /*public void AnimationButton(int position){ //부모 액티비티의 버튼의 속성을 invisible로 바꿔보기 위한 함수
        View deleteSpace = getActivity().findViewById(R.id.delete_space);
        ((MainActivity)getActivity()).deletePosition = position;

        //버튼을 감싸고 있는 레이아웃이 보이게끔 함
        deleteSpace.setVisibility(View.VISIBLE);
        deleteSpace.startAnimation(translateTop);
    }*/
}
