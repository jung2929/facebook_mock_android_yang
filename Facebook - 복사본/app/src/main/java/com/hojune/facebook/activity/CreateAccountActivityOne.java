package com.hojune.facebook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hojune.facebook.R;

public class CreateAccountActivityOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_one);



        final EditText name1 = (EditText) findViewById(R.id.name1);
        final EditText name2 = (EditText) findViewById(R.id.name2);

        ImageButton next = (ImageButton)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivityOne.this,CreateAccountActivityTwo.class);
                intent.putExtra("name1", name1.getText().toString());
                intent.putExtra("name2", name2.getText().toString());
                startActivity(intent);
            }
        });
    }
}
