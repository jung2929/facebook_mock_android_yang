package com.hojune.facebook.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hojune.facebook.activity.AddTimeLineActivity;
import com.hojune.facebook.activity.MainActivity;
import com.hojune.facebook.adapter.TimeLineItemAdapter;
import com.hojune.facebook.R;
import com.hojune.facebook.adapter.ViewPagerAdapter;

public class MyProfileFragment extends Fragment {

    ListView listview;
    TimeLineItemAdapter timeLineItemAdapter = new TimeLineItemAdapter();
    TextView name;


//    Context context = MyProfileFragment.this;

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


        return view;
    }

    public void UpdateTimeLineItemAdapter(String message){
        timeLineItemAdapter.addItem(message);
        timeLineItemAdapter.notifyDataSetChanged();
        Log.e("MyProfileFragment","UpdateTimeLineItemAdpater() 호출");


    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        name = (TextView)findViewById(R.id.name);


        ViewGroup think = (ViewGroup)findViewById(R.id.think);
        think.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileFragment.this, AddTimeLineActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        listview =(ListView)findViewById(R.id.listview);


        listview.setAdapter(timeLineItemAdapter);



    }*/



   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case 100:
                    timeLineItemAdapter.addItem(data.getExtras().getString("edit_message"));
                    timeLineItemAdapter.notifyDataSetChanged();

                    setListViewHeightBasedOnChildren(listview);
            }
        }
    }*/

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
}
