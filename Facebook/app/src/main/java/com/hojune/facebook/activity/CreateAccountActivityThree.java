package com.hojune.facebook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hojune.facebook.R;

public class CreateAccountActivityThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_three);
        final RadioGroup rg = (RadioGroup)findViewById(R.id.rg);

        final Intent intent0 = getIntent();

        ImageButton next = (ImageButton)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)findViewById(id);

                Intent intent = new Intent(CreateAccountActivityThree.this,CreateAccountActivityFour.class);
                intent.putExtra("name1",intent0.getStringExtra("name1").toString());
                intent.putExtra("name2",intent0.getStringExtra("name2").toString());
                intent.putExtra("bir",intent0.getStringExtra("bir").toString());
                intent.putExtra("gender",String.valueOf(rb.getText().toString()));

                startActivity(intent);
            }
        });
    }
}
