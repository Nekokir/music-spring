package com.music.musicspring.entity;

public class JsonResponse {
    private boolean success;
    private Object data;
    public JsonResponse(boolean success){
        this.success=success;
    };
    public JsonResponse(boolean success,Object data){
        this.success=success;
        this.data=data;
    }

    public boolean getSuccess(){
        return success;
    }

    public Object getData(){
        return data;
    }

    public void setSuccess(boolean success){
        this.success=success;
    }

    public void setData(Object data){
        this.data=data;
    }
}
