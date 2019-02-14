package com.hojune.facebook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hojune.facebook.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // MainActivity.class 자리에 다음에 넘어갈 액티비티를 넣어주기
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("state", "launch");
        startActivity(intent);
        finish();
    }
}
