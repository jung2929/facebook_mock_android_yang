package com.hojune.facebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText id = (EditText)findViewById(R.id.id);
        final EditText pw = (EditText)findViewById(R.id.pw);

        ImageButton create_account = (ImageButton)findViewById(R.id.create_account);
        ImageButton login = (ImageButton) findViewById(R.id.login);



        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,CreateAccountActivity_1.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = new API(id.getText().toString(), pw.getText().toString());

                //바로 if문으로 넘어가니까 api.success가 true로 바뀌기도 전에 if문 실행함
                //그래서 1초 텀을 줬더니 내 의도대로 잘 됨.

                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                if(api.success){
                    Log.d("LoginActivity", "api.success가 true");
                    Intent intent = new Intent(LoginActivity.this, MyProfileActivity.class);
                    Toast.makeText(LoginActivity.this, api.message,Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }

                else{
                    Log.d("LoginActivity", "api.success가 false");
                    Toast.makeText(LoginActivity.this, api.message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
