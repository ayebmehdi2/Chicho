package com.mehdi.kikkik.Messaging;

public class UserInfo {

    public String youName;
    public String yourPhoto;
    public String lastMessage;
    public String childName;

    public UserInfo(){}

    public UserInfo(String childName, String youName, String yourPhoto, String lastMessage){
        this.childName = childName;
        this.youName = youName;
        this.yourPhoto = yourPhoto;
        this.lastMessage = lastMessage;
    }

    String getLastMessage() {
        return lastMessage;
    }

    String getYouName() {
        return youName;
    }

    String getYourPhoto() {
        return yourPhoto;
    }

    String getChildName() {
        return childName;
    }
}
