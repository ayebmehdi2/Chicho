package com.mehdi.kikkik.Messaging;

public class Message {

    private String userId;
    private String userImage;
    private String conStr;
    private String conImg;
    private int time;

    public Message(){}

    Message(String userId, String userImage, String conStr, String conImg, int time){
        this.userId = userId;
        this.userImage = userImage;
        this.conStr = conStr;
        this.conImg = conImg;
        this.time = time;
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

    public String getUserId() {
        return userId;
    }

    public String getUserImage() {
        return userImage;
    }
}
