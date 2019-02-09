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
import com.hojune.facebook.fragment.MyProfileFragment;
import com.hojune.facebook.model.MyTimeLineData;

import java.util.ArrayList;

public class TimeLineItemAdapter extends BaseAdapter {

    boolean isPageOpen = false;
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

        //리스트뷰 아이템이 activity_my_profile에 있는 레이아웃을 참조해서, 그 레이아웃에 애니메이션 효과를 주기 위한 코드



        TextView message = (TextView)convertView.findViewById(R.id.message);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        ImageView ivOption = (ImageView)convertView.findViewById(R.id.iv_option);


        //리스트뷰에 가장 최신으로 적은 글이 가장 윗부분으로 올라오게 하기위함
        //즉 새로운 데이터는 arraylist[0]에 들어가는 것임
        final MyTimeLineData myTimeLineData = arrayList.get(arrayList.size()-1-position);

        //데이터의 position값을 넣어줌으로써 나중에 삭제할 때 이 데이터의 position값을 사용
        myTimeLineData.setPosition(arrayList.size()-1-position);



        name.setText(myTimeLineData.getName());
        date.setText(myTimeLineData.getDate());
        message.setText(myTimeLineData.getMessage());



        Log.e("TimeLineItemAdapter", "getView");

        ivOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProfileFragment myProfileFragment = MyProfileFragment.newInstance();
                myProfileFragment.ShowOption();
            }
        });

        return convertView;
    }



    public void AddItem(String message, String date, String name){
        MyTimeLineData data = new MyTimeLineData();

        data.setMessage(message);
        data.setDate(date);
        data.setName(name);

        arrayList.add(data);
    }

    public void DeleteItem(int position){

        Log.e("TimeLineItemAdapter","DeleteItem() 호출, position 값 : "+position);
        arrayList.remove(position);
        notifyDataSetChanged();
    }

    public void DeleteAll(){
        arrayList.clear();
        //notifyDataSetChanged();
    }
}
