package com.hojune.facebook.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTimeLineData {
    private String message;
    private String time;
    private int position;

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setTime(){

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
        String formatDate = sdfNow.format(date);

        time = formatDate;
    }

    public String getTime(){
        return time;
    }

}
