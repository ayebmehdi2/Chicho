package com.mehdi.kikkik.Profile;

public class DARNA {

    private String uid;
    private String name;
    private String img;
    private int numPost;

    public DARNA(){}

    DARNA(String uid ,String name, String img, int numPost){
        this.uid = uid;
        this.name = name;
        this.img = img;
        this.numPost = numPost;
    }

    public String getUid() {
        return uid;
    }

    public int getNumPost() {
        return numPost;
    }

    public void setNumPost(int numPost) {
        this.numPost = numPost;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }
}
