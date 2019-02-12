package com.hojune.facebook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.hojune.facebook.ConnectToWonnie;
import com.hojune.facebook.R;
import com.hojune.facebook.adapter.FindFriendItemAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FriendListActivity extends AppCompatActivity {

    Context mContext = this;
    Handler mHandler = new Handler();
    ConnectToWonnie connectToWonnie = new ConnectToWonnie();
    FindFriendItemAdapter findFriendItemAdapter = new FindFriendItemAdapter();
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        mListView = (ListView)findViewById(R.id.friend_listview);
        mListView.setAdapter(findFriendItemAdapter);

        connectToWonnie.FriendList(mContext, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("FindFriendActivity","onResponse()");
                try{
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    //Log.e("FindFriend",String.valueOf(jsonObject.getJSONArray("data").length()));
                    //final JSONArray jsonArray = new JSONArray(response.body().string());
                    //Log.e("FindFriend",String.valueOf(jsonArray.length()));

                    for(int i = 0; i<jsonObject.getJSONArray("data").length();i++){
                        //String name = jsonArray.getJSONObject(i).getString("userFullname");
                        String name = jsonObject.getJSONArray("data").getJSONObject(i).getString("friendName");
                        findFriendItemAdapter.AddItem(name);
                    }
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            findFriendItemAdapter.notifyDataSetChanged();
                        }
                    });
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try{
                                final int pos = position;
                                //final String userId = jsonArray.getJSONObject(position).getString("user_Id");
                                //Log.e("????????????", jsonArray.getJSONObject(position).getString("user_Id"));
                                //final String userId = jsonArray.getJSONObject(position).getString("user_Id");
                                final String userId = jsonObject.getJSONArray("data").getJSONObject(pos).getString("user_Id");


                                /**
                                 *원래는 여기에 사용자 아이디 들어가야하는데 지금 일단 임시로 별명
                                 */
                                connectToWonnie.ReadProfile(userId, mContext, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        try{
                                            //final JSONArray array = new JSONArray(response.body().string());
                                            final JSONObject object = new JSONObject(response.body().string());
                                            //Log.e("FindFriend",String.valueOf(jsonObject2.getJSONArray("data").length()));

                                            /**
                                             * 이부분도 말해야해 지금 범위가 ,가 안찍혀있어서 그런지 index 0 으로 접근 불가함
                                             * 지금 이 주석 바로 밑에 적은 코드는 임시방편
                                             */
                                                                /*String userFullname =array.getJSONObject(0).getString("userFullname");
                                                                String hometown = array.getJSONObject(0).getString("hometown");
                                                                String job = array.getJSONObject(0).getString("job");
                                                                String nickname =array.getJSONObject(0).getString("userNickname");*/
                                            String hometown = object.getJSONArray("data").getJSONObject(0).getString("hometown");
                                            String userFullname = object.getJSONArray("data").getJSONObject(0).getString("userFullname");
                                            String job = object.getJSONArray("data").getJSONObject(0).getString("job");
                                            String nickname = object.getJSONArray("data").getJSONObject(0).getString("userNickname");
                                            Log.e("FindFriend","서버에서 불러온 호철이 값"+hometown+job+nickname);


                                            Intent intent = new Intent(FriendListActivity.this,FriendProfileActivity.class);
                                            intent.putExtra("userFullname",userFullname);
                                            intent.putExtra("hometown",hometown);
                                            intent.putExtra("job",job);
                                            intent.putExtra("nickname",nickname);

                                            startActivity(intent);
                                        }catch(Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                    });


                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }

}
