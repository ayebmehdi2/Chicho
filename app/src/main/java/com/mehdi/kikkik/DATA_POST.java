package com.mehdi.kikkik;

public class DATA_POST {

    private String uid;
    private String img;
    private String name;
    private int time;
    private String content;
    private String contentImage;
    private int numLike;
    private int numComment;
    private boolean isLike;
    private boolean isComment;

    public DATA_POST(){}

    DATA_POST(String idPost, String img, String name, int time, String content, String contentImage, int numLike, int numComment, boolean isLike, boolean isComment){
        this.uid = idPost;
        this.img = img;
        this.name = name;
        this.time = time;
        this.content = content;
        this.contentImage = contentImage;
        this.numLike = numLike;
        this.numComment = numComment;
        this.isLike = isLike;
        this.isComment = isComment;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getUid() {
        return uid;
    }

    public String getContent() {
        return content;
    }

    public int getNumComment() {
        return numComment;
    }

    public int getNumLike() {
        return numLike;
    }

    public int getTime() {
        return time;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNumComment(int numComment) {
        this.numComment = numComment;
    }

    public void setNumLike(int numLike) {
        this.numLike = numLike;
    }

    public boolean getIsLike() {
        return isLike;
    }

    public boolean getIsComment() {
        return isComment;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public void setComment(boolean comment) {
        isComment = comment;
    }

}
