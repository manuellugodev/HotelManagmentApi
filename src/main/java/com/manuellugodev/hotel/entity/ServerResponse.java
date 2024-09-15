package com.manuellugodev.hotel.entity;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

public class ServerResponse<T> {


    private T data;
    private int status;
    private String messsage;
    private String errorType;

    private long timeStamp;

    public ServerResponse(){
    }
    public ServerResponse(T data,int status, String messsage, String errorType, long timeStamp) {
        this.data=data;
        this.status = status;
        this.messsage = messsage;
        this.errorType = errorType;
        this.timeStamp = timeStamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }


}
