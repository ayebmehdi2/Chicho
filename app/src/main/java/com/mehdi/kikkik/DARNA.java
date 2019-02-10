package com.mehdi.kikkik;

public class DARNA {

    private String name;
    private String img;
    private int numPost;

    public DARNA(String name, String img, int numPost){
        this.name = name;
        this.img = img;
        this.numPost = numPost;
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
