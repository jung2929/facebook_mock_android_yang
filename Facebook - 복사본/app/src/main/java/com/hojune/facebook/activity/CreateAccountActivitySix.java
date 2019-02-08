package com.hojune.facebook.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hojune.facebook.ConnectToWonnie;
import com.hojune.facebook.R;
import com.hojune.facebook.fragment.MyProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateAccountActivitySix extends AppCompatActivity {

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_six);

        Intent intent0 = getIntent();


        //여태까지 가져온 회원정보들을 변수에 쫙 담음
        final String name1 = intent0.getStringExtra("name1").toString();
        final String name2 = intent0.getStringExtra("name2").toString();
        final String bir = intent0.getStringExtra("bir").toString();
        final String gender = intent0.getStringExtra("gender").toString();
        final String id = intent0.getStringExtra("id").toString();
        final String pw = intent0.getStringExtra("pw").toString();

        TextView txId = (TextView)findViewById(R.id.Id);
        TextView txPw = (TextView)findViewById(R.id.Pw);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", name1+name2);
        editor.commit();

        Log.e("akfjklasf", pref.getString("name",""));

        txId.setText(intent0.getStringExtra("id"));
        txPw.setText(intent0.getStringExtra("pw"));

        ImageButton ibConfirm = (ImageButton) findViewById(R.id.confirm);
        ibConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccountActivitySix.this,MainActivity.class);

                ConnectToWonnie connectToWonnie = new ConnectToWonnie();
                connectToWonnie.CreateAccount(name1, name2, bir, gender, id, pw, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("CreateAccount Response", "서버연결 성공");

                        try {
                            final JSONObject jsonObject = new JSONObject(response.body().string());


                            Log.e("CreateAccount Response", jsonObject.getString("message"));

                            //서버에서 로그인 성공이라는 json형식의 값이 오면 로그인 성공이라는 메시지 띄워주고 내 프로필로 이동
                            if(jsonObject.getString("isSuccess")=="true"){
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Toast.makeText(CreateAccountActivitySix.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                Intent intent = new Intent(CreateAccountActivitySix.this, MainActivity.class);
                                startActivity(intent);
                            }

                            //
                            else{
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Toast.makeText(CreateAccountActivitySix.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }
}
