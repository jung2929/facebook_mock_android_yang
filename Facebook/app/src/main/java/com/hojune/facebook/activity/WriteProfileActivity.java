package com.hojune.facebook.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hojune.facebook.ConnectToWonnie;
import com.hojune.facebook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WriteProfileActivity extends AppCompatActivity {
    Context mContext = this;
    EditText etHometown;
    EditText etJob;
    EditText etNickname;
    ImageButton callWrite;
    ConnectToWonnie connectToWonnie = new ConnectToWonnie();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_profile);

        callWrite = (ImageButton)findViewById(R.id.call_write);
        etHometown = (EditText)findViewById(R.id.hometown);
        etJob = (EditText)findViewById(R.id.job);
        etNickname = (EditText)findViewById(R.id.nickname);

        callWrite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                connectToWonnie.WriteProfile(etHometown.getText().toString(), etJob.getText().toString(), etNickname.getText().toString(), mContext, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //flag를 이용해서 이전에 있던 MainActivity를 호출(갱신되게함)
                        //그리고 그 MainActivity에선 getIntent로 이 intent 잡은 다음에 글자 변경
                        //일단 애초에 MainActivity의 oncreate에서 프로필 조회하게 해야겠네

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            Log.e("프로필 작성화면의 response",jsonObject.getString("code"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent();
                        intent.putExtra("home",etHometown.getText().toString());
                        intent.putExtra("job",etJob.getText().toString());
                        intent.putExtra("nickname",etNickname.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        });
    }
}
