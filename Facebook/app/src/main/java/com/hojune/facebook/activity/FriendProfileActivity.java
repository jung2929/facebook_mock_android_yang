package com.hojune.facebook.activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hojune.facebook.ConnectToWonnie;
import com.hojune.facebook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FriendProfileActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tvHometown;
    TextView tvJob;
    TextView tvNickname;
    TextView tvUserfullname;
    boolean isFriend;
    Context mContext = this;

    ConnectToWonnie connectToWonnie = new ConnectToWonnie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        imageView = (ImageView)findViewById(R.id.add_friend);
        tvHometown = (TextView)findViewById(R.id.hometown);
        tvJob = (TextView)findViewById(R.id.job);
        tvNickname = (TextView)findViewById(R.id.nickname);
        tvUserfullname = (TextView)findViewById(R.id.user_fullname);
        Intent intent = getIntent();

        tvHometown.setText(intent.getStringExtra("hometown"));
        tvJob.setText(intent.getStringExtra("job"));
        tvNickname.setText(intent.getStringExtra("nickname"));
        tvUserfullname.setText(intent.getStringExtra("userFullname"));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFriend){
                    Log.e("FriendProfileActivity","isFriend가 true");
                }

                else{
                    connectToWonnie.AddFriend(tvUserfullname.getText().toString(), mContext, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try{
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                Log.e("haha",jsonObject.getString("code"));

                            }
                            catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
