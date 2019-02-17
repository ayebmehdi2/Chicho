package com.mehdi.kikkik.Search;

public class DataPerson {

    private String uid;
    private String  img;
    private String name;

    public DataPerson(String uid, String  img, String name){
        this.uid = uid;
        this.img = img;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

}
