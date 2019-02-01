package com.hojune.facebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hojune.facebook.R;
import com.hojune.facebook.model.MyTimeLineData;

import java.util.ArrayList;

public class TimeLineItemAdapter extends BaseAdapter {

    ArrayList<MyTimeLineData> arrayList = new ArrayList<MyTimeLineData>();


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_my_profile_timeline,parent,false);
        }

        TextView message = (TextView)convertView.findViewById(R.id.message);
        TextView name = (TextView)convertView.findViewById(R.id.name);


        MyTimeLineData myTimeLineData = arrayList.get(arrayList.size()-1-position);

        message.setText(myTimeLineData.getMessage());

        return convertView;
    }

    public void addItem(String message){
        MyTimeLineData data = new MyTimeLineData();

        data.setMessage(message);

        arrayList.add(data);
    }
}
