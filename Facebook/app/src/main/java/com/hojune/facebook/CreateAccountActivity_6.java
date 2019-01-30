package com.hojune.facebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CreateAccountActivity_6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_6);

        Intent intent0 = getIntent();


        //여태까지 가져온 회원정보들을 변수에 쫙 담음
        final String name1 = intent0.getStringExtra("name1").toString();
        final String name2 = intent0.getStringExtra("name2").toString();
        final String bir = intent0.getStringExtra("bir").toString();
        final String gender = intent0.getStringExtra("gender").toString();
        final String id = intent0.getStringExtra("id").toString();
        final String pw = intent0.getStringExtra("pw").toString();

        TextView Id = (TextView)findViewById(R.id.Id);
        TextView Pw = (TextView)findViewById(R.id.Pw);

        Id.setText(intent0.getStringExtra("id"));
        Pw.setText(intent0.getStringExtra("pw"));

        ImageButton confirm = (ImageButton) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccountActivity_6.this,MyProfileActivity.class);

                API api = new API(name1,name2,bir,gender,id,pw);

                startActivity(intent);
            }
        });
    }
}
