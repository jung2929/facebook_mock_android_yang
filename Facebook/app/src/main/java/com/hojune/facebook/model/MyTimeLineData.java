package com.hojune.facebook.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTimeLineData {
    private String message;
    private String date;
    private String name;

    private int position;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

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

    public void setDate(String date){
        this.date = date;

        /*long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
        String formatDate = sdfNow.format(date);

        this.date = formatDate;*/
    }

    public String getDate(){
        return date;
    }

}
