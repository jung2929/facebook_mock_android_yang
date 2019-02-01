package com.hojune.facebook;

import android.util.Log;

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

public class ConnectToWonnie {
    OkHttpClient client = new OkHttpClient();

    public String message;
    public String name;
    boolean success = false; //연결이 잘 되었는지 외부에서 판단할 수 있게 해주는 boolean 변수

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

    public void Login(String Id, String Pw, Callback callback){
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

        client.newCall(request).enqueue(callback);
    }





    //아 이부분이 메서드가 아니라 변수구나..
    private Callback callbackAfterRequest = new Callback() {
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
        }

        /*@Override
        public void onFailure(Request request, IOException e) {
        }

        @Override
        public void onResponse(Response response) throws IOException {
            final String strJsonOutput = response.body().string();
            final JSONObject jsonOutput = JsonUtil.getJSONObjectFrom(strJsonOutput);
        }*/
    };
}
