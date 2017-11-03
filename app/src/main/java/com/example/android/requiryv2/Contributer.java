package com.example.android.requiryv2;

import java.io.Serializable;

/**
 * Created by upamanyughose on 01/11/17.
 */
public class Contributer implements Serializable{

    private String pId;
    private String uId;
    public Contributer(){}
    public Contributer(String pId, String uId) {
        this.pId = pId;
        this.uId = uId;
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
}
