package com.example.android.requiryv2;

import java.io.Serializable;

/**
 * Created by MAHE on 05-Nov-17.

 */
public class AddMemberRequestNotif implements Serializable {
    private String username;
    private String message;
    public AddMemberRequestNotif(){}
    public AddMemberRequestNotif(String user,String msg){
        username = user;
        message = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
