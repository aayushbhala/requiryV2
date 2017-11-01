package com.example.android.requiryv2;

/**
 * Created by MAHE on 01-Nov-17.
 */
public class RequiryUser {
    private int uID;
    private String uName;
    private String uNumber;
    private String uEmail;
    private String uUsername;
    private int uWho;
    private String uDesc;

    public RequiryUser(int id, String name, String number, String email, String username, int who, String desc) {
        uID = id;
        uName = name;
        uNumber = number;
        uEmail = email;
        uUsername = username;
        uWho = who;
        uDesc = desc;
    }

    public int getuID() {
        return uID;
    }

    public String getName() {
        return uName;
    }

    public String getNumber() {
        return uNumber;
    }

    public String getEmail() {
        return uEmail;
    }

    public String getUsername() {
        return uUsername;
    }

    public int getWho() {
        return uWho;
    }

    public String getDesc() {
        return uDesc;
    }
    public void setuID(int uID) {
        this.uID = uID;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public void setuUsername(String uUsername) {
        this.uUsername = uUsername;
    }

    public void setuWho(int uWho) {
        this.uWho = uWho;
    }

    public void setuDesc(String uDesc) {
        this.uDesc = uDesc;
    }
}

