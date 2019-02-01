package com.hojune.facebook.model;

public class MyTimeLineData {
    private String message;
    private String name;

    public void setMessage(String message){
        this.message = message;
    }

    public void setName(String name) {this.name = name;}

    public String getMessage(){
        return message;
    }

    public String getName() { return name; }

}
