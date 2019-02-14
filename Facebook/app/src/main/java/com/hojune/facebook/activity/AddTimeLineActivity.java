package com.hojune.facebook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hojune.facebook.ConnectToWonnie;
import com.hojune.facebook.R;
import com.hojune.facebook.fragment.MyProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.hojune.facebook.Constant.REQUEST_TAKE_PHOTO;

public class AddTimeLineActivity extends AppCompatActivity {

    static final int GALLERY = 200;

    ImageView ivGallery;
    ImageView ivGalleryImage;
    Handler mHandler = new Handler();
    MyProfileFragment myProfileFragment = MyProfileFragment.newInstance();
    Button share;

    EditText edit_message;
    Context mContext = this;
    ImageView back;
    TextView tvShare;
    ConnectToWonnie connectToWonnie = new ConnectToWonnie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timeline);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvShare = (TextView) findViewById(R.id.tv_share);
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToWonnie.AddTimeLine(edit_message.getText().toString(), mContext, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        connectToWonnie.MyTimeLine(mContext, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());

                                    for (int i = 0; i < jsonObject.getJSONArray("data").length(); i++) {
                                        final String message = jsonObject.getJSONArray("data").getJSONObject(i).getString("content");
                                        final String date = jsonObject.getJSONArray("data").getJSONObject(i).getString("date");
                                        final String name = jsonObject.getJSONArray("data").getJSONObject(i).getString("name");
                                        final int number = Integer.parseInt(jsonObject.getJSONArray("data").getJSONObject(i).getString("number"));
                                        myProfileFragment.timeLineItemAdapter.AddItem(message, date, name, number);

                                        //와 코드 이렇게 줬더니 간신히 해결
                                        //근데 이거 도대체 왜 이런거지
                                        //syncronize도 했는데
                                /*try{
                                    Thread.sleep(1000);
                                }catch(InterruptedException e){
                                    e.printStackTrace();
                                }*/
                                    }
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            myProfileFragment.timeLineItemAdapter.notifyDataSetChanged();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //여기서 리스트뷰 높이 조절하는 함수를 호출했더니 getview가 두배로 더 호출됨.. 흠..
                                //일단 내가 우려했던 문제는 해결완료..
                                //높이를 2분의1로 줄여볼까 증가하는..
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyProfileFragment.setListViewHeightBasedOnChildren(myProfileFragment.listview);
                                        Log.e("AddTimeLineActivity", "리스트뷰 높이조절 직후");
                                    }
                                });
                                Intent intent = new Intent(AddTimeLineActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                finish();
                                startActivity(intent);

                            }
                        });
                    }
                });
                    }
                });

        ivGalleryImage = (ImageView)findViewById(R.id.galley_image);

        ivGallery = (ImageView)findViewById(R.id.call_gallery);
        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, GALLERY);
            }
        });

        edit_message = (EditText)findViewById(R.id.edit_message);
        /*share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                connectToWonnie.AddTimeLine(edit_message.getText().toString(), mContext, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                });

                //onResponse 이후에 밑으로 mainthread가 진행하게 하기 위함
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }


                connectToWonnie.MyTimeLine(mContext, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try{
                            JSONObject jsonObject = new JSONObject(response.body().string());

                            for(int i=0; i<jsonObject.getJSONArray("data").length();i++){
                                final String message = jsonObject.getJSONArray("data").getJSONObject(i).getString("content");
                                final String date = jsonObject.getJSONArray("data").getJSONObject(i).getString("date");
                                final String name = jsonObject.getJSONArray("data").getJSONObject(i).getString("name");
                                myProfileFragment.timeLineItemAdapter.AddItem(message, date, name);

                                //와 코드 이렇게 줬더니 간신히 해결
                                //근데 이거 도대체 왜 이런거지
                                //syncronize도 했는데
                                *//*try{
                                    Thread.sleep(1000);
                                }catch(InterruptedException e){
                                    e.printStackTrace();
                                }*//*
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    myProfileFragment.timeLineItemAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }

                        //여기서 리스트뷰 높이 조절하는 함수를 호출했더니 getview가 두배로 더 호출됨.. 흠..
                        //일단 내가 우려했던 문제는 해결완료..
                        //높이를 2분의1로 줄여볼까 증가하는..
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                MyProfileFragment.setListViewHeightBasedOnChildren(myProfileFragment.listview);
                                Log.e("AddTimeLineActivity","리스트뷰 높이조절 직후");
                            }
                        });
                        Intent intent = new Intent(AddTimeLineActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        finish();
                        startActivity(intent);

                    }
                });
            }
        });*/
    }

    //갤러리를 startActivityForResult로 호출했을때
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    ivGalleryImage.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
