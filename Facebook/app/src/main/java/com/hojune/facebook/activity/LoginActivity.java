package com.hojune.facebook.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class LoginActivity extends AppCompatActivity {

    Handler mHandler = new Handler();
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etId = (EditText)findViewById(R.id.id);
        final EditText etPw = (EditText)findViewById(R.id.pw);

        Button ibCreateAccount = (Button)findViewById(R.id.create_account);
        Button ibLogin = (Button) findViewById(R.id.login);

        ibCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,CreateAccountActivityOne.class);
                startActivity(intent);
            }
        });

        ibLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("userId",etId.getText().toString());
                editor.commit();
                final ConnectToWonnie connectToWonnie = new ConnectToWonnie();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("LoginActivity","connectTowonnie.login을 쓰레드로 start");
                        connectToWonnie.Login(etId.getText().toString(), etPw.getText().toString(),mContext);
                    }
                }).start();
                        /*new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("LoginActivity onFailure", "서버연결 실패");
                    }

                    @Override    //서버와 연결이 성공하면 이 함수가 호출됨. 로그인 성공 실패 여부와 상관없는것임. 그저 서버와의 연결이 잘 되었을 때
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("LoginActivity Response", "서버연결 성공");

                        try {
                           final JSONObject jsonObject = new JSONObject(response.body().string());
                           Log.e("LoginActivity Response", jsonObject.getString("message"));

                            //서버에서 로그인 성공이라는 json형식의 값이 오면 로그인 성공이라는 메시지 띄워주고 내 프로필로 이동
                            if(jsonObject.getString("isSuccess")=="true"){
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                //jwt값 db에 저장하기
                                Log.e("LoginActivity",jsonObject.getJSONObject("data").getString("jwt"));
                                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("jwt",jsonObject.getJSONObject("data").getString("jwt"));
                                editor.commit();

                                Log.e("pref값 가져오기", pref.getString("jwt","emety"));
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                            else{
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
              });*/
            }
        });
    }
}
