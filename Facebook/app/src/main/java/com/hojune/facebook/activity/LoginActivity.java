package com.hojune.facebook.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etId = (EditText)findViewById(R.id.id);
        final EditText etPw = (EditText)findViewById(R.id.pw);

        ImageButton ibCreateAccount = (ImageButton)findViewById(R.id.create_account);
        ImageButton ibLogin = (ImageButton) findViewById(R.id.login);



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
                ConnectToWonnie connectToWonnie = new ConnectToWonnie();
                connectToWonnie.Login(etId.getText().toString(), etPw.getText().toString(), new Callback() {
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
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            //
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
                });
                //내가 여기서 callback 객체를 파라미터로 넘겨주는것과 thread.sleep을 해서 하는것의 차이를 명확히 알아보자..




                //바로 if문으로 넘어가니까 login.success가 true로 바뀌기도 전에 if문 실행함
                //그래서 1초 텀을 줬더니 내 의도대로 잘 됨.


            }
        });
    }
}
