package com.hojune.facebook.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hojune.facebook.R;
import com.hojune.facebook.activity.FindFriend;
import com.hojune.facebook.model.FindFriendData;
import com.hojune.facebook.model.FriendListData;
import com.hojune.facebook.model.MyTimeLineData;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FriendListItemAdapter extends BaseAdapter {

    ArrayList<FriendListData> arrayList = new ArrayList<FriendListData>();

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
            convertView = inflater.inflate(R.layout.activity_friend_list_item,parent,false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.name);

        FriendListData friendListData = arrayList.get(position);

        name.setText(friendListData.getName());

        Log.e("친구목록 어댑터", "getView 내부");
        return convertView;
    }

    public void DeleteAll(){
        arrayList.clear();
    }

    public void AddItem(String name){
        FriendListData friendListData = new FriendListData();
        friendListData.setName(name);

        arrayList.add(friendListData);

        //이 notify없으면 add해도 화면에 변함이 없음
        //notifyDataSetChanged();
    }
}
