package com.hojune.facebook;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    ArrayList<MessageBoardData> arrayList = new ArrayList<MessageBoardData>();


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
            convertView = inflater.inflate(R.layout.itemlayoutdefinition,parent,false);
        }

        TextView message = (TextView)convertView.findViewById(R.id.message);

        MessageBoardData messageBoardData = arrayList.get(position);

        message.setText(messageBoardData.getMessage());

        return convertView;
    }

    public void addItem(String message){
        MessageBoardData data = new MessageBoardData();

        data.setMessage(message);

        arrayList.add(data);
    }
}
