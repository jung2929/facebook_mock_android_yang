package com.hojune.facebook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.hojune.facebook.activity.LoginActivity;
import com.hojune.facebook.activity.MainActivity;
import com.hojune.facebook.fragment.MyProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class ConnectToWonnie {
    OkHttpClient client = new OkHttpClient();

    private String jwt;

    public String message;
    public String name;
    boolean success = false; //연결이 잘 되었는지 외부에서 판단할 수 있게 해주는 boolean 변수

    public void ReadProfile(String userFullname, String userNickname, Context context, Callback callback){
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("jiwondomain.com")
                .addPathSegment("profile")
                .build();

        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        jwt = pref.getString("jwt","emety");
        Log.e("프로필 읽을때의 jwt", jwt);

        JSONObject jsonInput = new JSONObject();

        try{
            jsonInput.put("hometown",userFullname);
            jsonInput.put("userNickname",userNickname);
        }catch(JSONException e){
            e.printStackTrace();
        }

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .addHeader("x-access-token",jwt)
                .url(httpUrl)
                //.post(reqBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void WriteProfile(String hometown, String job, String userNickname, Context context, Callback callback){
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("jiwondomain.com")
                .addPathSegment("profile")
                .build();

        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        jwt = pref.getString("jwt","emety");
        Log.e("프로필 작성할때의 jwt", jwt);

        JSONObject jsonInput = new JSONObject();

        try{
            jsonInput.put("hometown",hometown);
            jsonInput.put("job",job);
            jsonInput.put("userNickname",userNickname);
        }catch(JSONException e){
            e.printStackTrace();
        }

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .addHeader("x-access-token",jwt)
                .url(httpUrl)
                .post(reqBody)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public void AddTimeLine(String message, Context context, Callback callback){
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("jiwondomain.com")
                .addPathSegment("timeline")
                .build();

        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        jwt = pref.getString("jwt","emety");

        JSONObject jsonInput = new JSONObject();

        try{
            jsonInput.put("content",message);
        }catch(JSONException e){
            e.printStackTrace();
        }

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .addHeader("x-access-token",jwt)
                .url(httpUrl)
                .post(reqBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void MyTimeLine(Context context, Callback callback){

        //on response 위에있던걸 내가 잠깐 빼옴. 여기있으면 handler가 필요없잖아
        //mainthread에서 어댑터를 리셋하고 싶어.
        MyProfileFragment myProfileFragment = MyProfileFragment.newInstance();
        myProfileFragment.timeLineItemAdapter.DeleteAll();
        Log.e("MyTimeline함수내에서","리스트 다 삭제");

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("jiwondomain.com")
                .addPathSegment("timeline")
                .build();

        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        jwt = pref.getString("jwt","emety");
        Log.e("ConnectToWonnie", pref.getString("jwt","empty"));

        JSONObject jsonInput = new JSONObject();

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .addHeader("x-access-token",jwt)
                .url(httpUrl)
                .build();

        client.newCall(request).enqueue(callback);

    }

    public void SearchAll(String name,Context context, Callback callback){
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("jiwondomain.com")
                .addPathSegment("users/"+name)
                .build();

        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        jwt = pref.getString("jwt","empty");



        JSONObject jsonInput = new JSONObject();

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .addHeader("x-access-token",jwt)
                .url(httpUrl)
                //.post(reqBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void CreateAccount(String name1, String name2, String bir, String gender, String id, String pw, Callback callback) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("jiwondomain.com")
                .addPathSegment("user")
                .build();
        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("user_Firstname", name1);
            jsonInput.put("user_Lastname", name2);
            jsonInput.put("user_Id", id);
            jsonInput.put("user_Pw", pw);
            jsonInput.put("user_Birth", bir);
            jsonInput.put("user_Gender", gender);

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .url(httpUrl)
                .post(reqBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void Login(String Id, String Pw, Context context){
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("jiwondomain.com")
                .addPathSegment("login")
                .build();
        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("user_Id", Id);
            jsonInput.put("user_Pw", Pw);

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .url(httpUrl)
                .post(reqBody)
                .build();

        //client.newCall(request).enqueue(callback);
        try {
            Log.e("로그인 API","pref값 변경 전");
            Response response = client.newCall(request).execute();
            final JSONObject jsonObject = new JSONObject(response.body().string());

            SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("jwt",jsonObject.getJSONObject("data").getString("jwt"));
            editor.commit();

            Log.e("pref값 변경완료", pref.getString("jwt","emety"));
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }





    //아 이부분이 메서드가 아니라 변수구나..
    /*private Callback callbackAfterRequest = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            try {
                JSONObject jsonObject = new JSONObject(response.body().string());

                if(jsonObject.getString("isSuccess")=="true"){
                    success = true;
                }

                message = jsonObject.getString("message");
                //name = jsonObject.getString("")

                Log.e("onResponse", jsonObject.getString("message"));

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        /*@Override
        public void onFailure(Request request, IOException e) {
        }

        @Override
        public void onResponse(Response response) throws IOException {
            final String strJsonOutput = response.body().string();
            final JSONObject jsonOutput = JsonUtil.getJSONObjectFrom(strJsonOutput);
        }*/
    };
