package com.music.musicspring.entity;

public class Fail {
    private int code;
    private String reason;

    public Fail(int code,String reason){
        this.code = code;
        this.reason = reason;
    }

    public int getCode(){
        return code;
    }
    public void setCode(int code){
        this.code=code;
    }
    public String getReason(){
        return reason;
    }
    public void setReason(String reason){
        this.reason=reason;
    }
}
