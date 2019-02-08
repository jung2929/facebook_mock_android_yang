package com.hojune.facebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMessageBoard extends AppCompatActivity {

    Button share;
    EditText edit_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message_board);

        share = (Button)findViewById(R.id.share);
        edit_message = (EditText)findViewById(R.id.edit_message);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("edit_message", edit_message.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
