package com.hojune.facebook.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hojune.facebook.activity.MainActivity;
import com.hojune.facebook.adapter.TimeLineItemAdapter;
import com.hojune.facebook.R;
import com.hojune.facebook.custom.SlidingAnimationListener;

public class MyProfileFragment extends Fragment {

    ListView listview;
    public TimeLineItemAdapter timeLineItemAdapter = new TimeLineItemAdapter();
    TextView name;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_profile,container,false);


        name = (TextView)view.findViewById(R.id.name);
        listview =(ListView)view.findViewById(R.id.listview);
        listview.setAdapter(timeLineItemAdapter);



        setListViewHeightBasedOnChildren(listview);


        ViewGroup think = (ViewGroup)view.findViewById(R.id.think);

        think.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).CallAddTimeLine();
            }
        });


        //애니메이션 설정을 위한 변수들
        SlidingAnimationListener listener = new SlidingAnimationListener();
        translateTop = AnimationUtils.loadAnimation(getContext(), R.anim.translate_top);
        translateTop.setAnimationListener(listener);


        return view;
    }

    public void AddTimeLineItemAdapter(String message){
        timeLineItemAdapter.AddItem(message);
        timeLineItemAdapter.notifyDataSetChanged();

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

    public void AnimationButton(int position){ //부모 액티비티의 버튼의 속성을 invisible로 바꿔보기 위한 함수
        View deleteSpace = getActivity().findViewById(R.id.delete_space);
        ((MainActivity)getActivity()).deletePosition = position;

        //버튼을 감싸고 있는 레이아웃이 보이게끔 함
        deleteSpace.setVisibility(View.VISIBLE);
        deleteSpace.startAnimation(translateTop);
    }
}
