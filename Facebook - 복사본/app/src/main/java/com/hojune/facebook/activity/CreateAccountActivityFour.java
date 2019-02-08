package com.hojune.facebook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hojune.facebook.R;

public class CreateAccountActivityFour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_four);


        final Intent intent0 = getIntent();

        final EditText id = (EditText)findViewById(R.id.id);

        ImageButton next = (ImageButton)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivityFour.this,CreateAccountActivityFive.class);

                intent.putExtra("name1",intent0.getStringExtra("name1").toString());
                intent.putExtra("name2",intent0.getStringExtra("name2").toString());
                intent.putExtra("bir",intent0.getStringExtra("bir").toString());
                intent.putExtra("gender",intent0.getStringExtra("gender").toString());
                intent.putExtra("id", id.getText().toString());

                startActivity(intent);
            }
        });
    }
}
