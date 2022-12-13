package com.frt.customer.bean;

public class UserModel {

    String user_deviceID;
    String status;

    public UserModel(String user_deviceID, String status) {
        this.user_deviceID = user_deviceID;
        this.status = status;
    }
    public UserModel(){

    }

    public String getUser_deviceID() {
        return user_deviceID;
    }

    public void setUser_deviceID(String user_deviceID) {
        this.user_deviceID = user_deviceID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
