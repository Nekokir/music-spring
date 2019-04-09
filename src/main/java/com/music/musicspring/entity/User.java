package com.music.musicspring.entity;

public class User {
    private String userId;
    private String pwd;

    /*
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
    */
    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getPwd(){
        return pwd;
    }

    public void setPwd(String pwd){
        this.pwd = pwd;
    }
}
