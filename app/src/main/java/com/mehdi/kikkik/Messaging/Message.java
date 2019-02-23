package com.mehdi.kikkik.Messaging;

public class Message {

    private String name;
    private String userImage;
    private String conStr;
    private String conImg;
    private int time;

    public Message(){}

    Message(String name, String userImage, String conStr, String conImg, int time){
        this.name = name;
        this.userImage = userImage;
        this.conStr = conStr;
        this.conImg = conImg;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getConImg() {
        return conImg;
    }

    public int getTime() {
        return time;
    }

    public String getConStr() {
        return conStr;
    }

    public String getUserImage() {
        return userImage;
    }
}
