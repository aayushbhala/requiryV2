package com.example.android.requiryv2;

import java.io.Serializable;

/**
 * Created by MAHE on 01-Nov-17.
 */
public class Project implements Serializable {
    private String pID;
    private String uID;
    private String pName;
    private String pDomain;
    private String pDateStarts;
    private String pDateEnds;
    private String pDesc;
    private String pLink;

    public Project() {
    }

    public Project(String pID, String uID, String pName, String pDomain, String pDateStarts, String pDateEnds, String pDesc, String pLink){
        this.pID = pID;
        this.uID = uID;
        this.pName = pName;
        this.pDomain = pDomain;
        this.pDateStarts = pDateStarts;
        this.pDateEnds = pDateEnds;
        this.pLink = pLink;
        this.pDesc = pDesc;
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

    public String getpDomain() {
        return pDomain;
    }

    public void setpDomain(String pDomain) {
        this.pDomain = pDomain;
    }

    public String getpDateStarts() {
        return pDateStarts;
    }

    public void setpDateStarts(String pDateStarts) {
        this.pDateStarts = pDateStarts;
    }

    public String getpDateEnds() {
        return pDateEnds;
    }

    public void setpDateEnds(String pDateEnds) {
        this.pDateEnds = pDateEnds;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getpLink() {
        return pLink;
    }

    public void setpLink(String pLink) {
        this.pLink = pLink;
    }
}
