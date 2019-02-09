package com.hojune.facebook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hojune.facebook.ConnectToWonnie;
import com.hojune.facebook.R;
import com.hojune.facebook.adapter.FindFriendItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindFriend extends AppCompatActivity {

    Handler mHandler = new Handler();

    Context mContext = this;
    EditText etName;
    ConnectToWonnie connectToWonnie = new ConnectToWonnie();
    FindFriendItemAdapter findFriendItemAdapter = new FindFriendItemAdapter();
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);
        mListView = (ListView)findViewById(R.id.friend_listview);
        mListView.setAdapter(findFriendItemAdapter);

    }

    //액션바 꾸미기 위한 함수
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_friend, menu);
        View v = menu.findItem(R.id.menu_search).getActionView();
        if (v != null) {
            etName = (EditText) v.findViewById(R.id.editText);


            if (etName != null) {
                etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (event == null || event.getAction() == KeyEvent.ACTION_UP) {
                            findFriendItemAdapter.DeleteAll();

                            final String name = etName.getText().toString();

                            // 검색 메소드 호출
                            Toast.makeText(FindFriend.this, name, Toast.LENGTH_SHORT).show();
                            connectToWonnie.SearchAll(name, mContext, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Log.e("FindFriendActivity","onResponse()");
                                    try{

                                        final JSONObject jsonObject = new JSONObject(response.body().string());
                                        Log.e("FindFriend",String.valueOf(jsonObject.getJSONArray("data").length()));
                                        for(int i = 0; i<jsonObject.getJSONArray("data").length();i++){
                                            String name = jsonObject.getJSONArray("data").getJSONObject(i).getString("userFullname");
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
                                                    final String nickName = jsonObject.getJSONArray("data").getJSONObject(position).getString("userFullname");


                                                                           /**
                                                                            *원래는 여기에 사용자 아이디 들어가야하는데 지금 일단 임시로 별명
                                                                             */
                                                    connectToWonnie.ReadProfile(name, "hocheol", mContext, new Callback() {
                                                        @Override
                                                        public void onFailure(Call call, IOException e) {

                                                        }

                                                        @Override
                                                        public void onResponse(Call call, Response response) throws IOException {
                                                            try{
                                                                final JSONObject jsonObject2 = new JSONObject(response.body().string());
                                                                Log.e("FindFriend",String.valueOf(jsonObject2.getJSONArray("data").length()));

                                                                for(int i = 0; i<jsonObject2.getJSONArray("data").length();i++){
                                                                    String hometown = jsonObject2.getJSONArray("data").getJSONObject(i).getString("hometown");
                                                                    Log.e("아아아아아",hometown);
                                                                }

                                                                /**
                                                                 * 이부분도 말해야해 지금 범위가 ,가 안찍혀있어서 그런지 index 0 으로 접근 불가함
                                                                 * 지금 이 주석 바로 밑에 적은 코드는 임시방편
                                                                 */
                                                                String userFullname ="hocheolYang";
                                                                String hometown = "인천";
                                                                String job = "인천";
                                                                String nickname ="hocheol";
                                                                /*String hometown = jsonObject.getJSONArray("data").getJSONObject(0).getString("hometown");
                                                                String userFullname = jsonObject.getJSONArray("data").getJSONObject(0).getString("userFullname");
                                                                String job = jsonObject.getJSONArray("data").getJSONObject(0).getString("job");
                                                                String nickname = jsonObject.getJSONArray("data").getJSONObject(0).getString("userNickname");*/
                                                                //Log.e("FindFriend","서버에서 불러온 호철이 값"+hometown+job+nickname);


                                                                Intent intent = new Intent(FindFriend.this,FriendProfileActivity.class);
                                                                intent.putExtra("userFullname",userFullname);
                                                                intent.putExtra("hometown",hometown);
                                                                intent.putExtra("job",job);
                                                                intent.putExtra("nickname",nickname);

                                                                startActivity(intent);
                                                            }catch(JSONException e){
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
                                    catch(JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            });

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

}
