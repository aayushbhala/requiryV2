package com.example.android.requiryv2;

/**
 * Created by MAHE on 01-Nov-17.
 */
public class RequiryUser {
    private String uID;
    private String uName;
    private String uNumber;
    private String uEmail;
    private String uUsername;
    private String uWho;
    private String uDesc;


    public RequiryUser() {
    }

    public RequiryUser(String uID, String uName, String uNumber, String uEmail, String uUsername, String uWho, String uDesc) {
        this.uID = uID;
        this.uName = uName;
        this.uNumber = uNumber;
        this.uEmail = uEmail;
        this.uUsername = uUsername;
        this.uWho = uWho;
        this.uDesc = uDesc;
    }



    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuUsername() {
        return uUsername;
    }

    public void setuUsername(String uUsername) {
        this.uUsername = uUsername;
    }

    public String getuWho() {
        return uWho;
    }

    public void setuWho(String uWho) {
        this.uWho = uWho;
    }

    public String getuDesc() {
        return uDesc;
    }

    public void setuDesc(String uDesc) {
        this.uDesc = uDesc;
    }
}

