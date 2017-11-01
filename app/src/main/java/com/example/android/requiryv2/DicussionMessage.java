package com.example.android.requiryv2;

/**
 * Created by upamanyughose on 01/11/17.
 */
public class DicussionMessage {

    private String pId;
    private String uId;
    private String message;
    private String timestamp;

    public DicussionMessage(String pName, String uUserName, String message, String timestamp) {
        this.pId = pName;
        this.uId = uUserName;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
