package com.manuellugodev.hotel.entity;

public class ServerErrorResponse {

    int status;
    String messsage;
    String exceptionType;

    long timeStamp;

    public ServerErrorResponse(){
    }
    public ServerErrorResponse(int status, String messsage, String exceptionType, long timeStamp) {
        this.status = status;
        this.messsage = messsage;
        this.exceptionType = exceptionType;
        this.timeStamp = timeStamp;
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

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
