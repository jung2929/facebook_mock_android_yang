package com.hojune.facebook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hojune.facebook.R;

public class FriendProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        TextView tvHometown = (TextView)findViewById(R.id.hometown);
        TextView tvJob = (TextView)findViewById(R.id.job);
        TextView tvNickname = (TextView)findViewById(R.id.nickname);
        TextView tvUserfullname = (TextView)findViewById(R.id.user_fullname);
        Intent intent = getIntent();

        tvHometown.setText(intent.getStringExtra("hometown"));
        tvJob.setText(intent.getStringExtra("job"));
        tvNickname.setText(intent.getStringExtra("nickname"));
        tvUserfullname.setText(intent.getStringExtra("userFullname"));
    }
}
