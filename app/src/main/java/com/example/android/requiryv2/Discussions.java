package com.example.android.requiryv2;

/**
 * Created by tito on 4/11/17.
 */

public class Discussions {
    private String pID;
    private String uID;
    private String pName;
    private String uUserName;
    private String msg;
    private String timestamp;

    public Discussions() {
    }

    public Discussions(String pId, String uId, String pName, String uUserName, String msg, String timestamp) {
        this.pID = pId;
        this.uID = uId;
        this.pName = pName;
        this.uUserName = uUserName;
        this.msg = msg;
        this.timestamp = timestamp;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getuUserName() {
        return uUserName;
    }

    public void setuUserName(String uUserName) {
        this.uUserName = uUserName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
