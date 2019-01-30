package com.hojune.facebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class CreateAccountActivity_4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_4);


        final Intent intent0 = getIntent();

        final EditText id = (EditText)findViewById(R.id.id);

        ImageButton next = (ImageButton)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity_4.this,CreateAccountActivity_5.class);

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
