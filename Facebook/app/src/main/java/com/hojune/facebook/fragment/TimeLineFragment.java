package com.hojune.facebook.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hojune.facebook.R;
import com.hojune.facebook.activity.AddTimeLineActivity;
import com.hojune.facebook.activity.MainActivity;

public class TimeLineFragment extends Fragment {

    private static TimeLineFragment fragment;
    public static TimeLineFragment newInstance() {
        if(fragment==null){
            fragment = new TimeLineFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_timeline,container,false);

        ViewGroup think = (ViewGroup)view.findViewById(R.id.think);


        think.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ((MainActivity)getActivity()).CallAddTimeLine();
            }
        });

        return view;
    }
}
