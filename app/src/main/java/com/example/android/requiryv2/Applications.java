package com.example.android.requiryv2;

import java.io.Serializable;

/**
 * Created by upamanyughose on 01/11/17.
 */
public class Applications implements Serializable{
    private String pId;
    private String applicantId;
    private String creatorId;
    private String applicationMsg;
    private String pName;

    public Applications() {
    }

    public Applications(String pId, String applicantId, String creatorId, String applicationMsg, String pName) {
        this.pId = pId;
        this.applicantId = applicantId;
        this.creatorId = creatorId;
        this.applicationMsg = applicationMsg;
        this.pName = pName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getApplicationMsg() {
        return applicationMsg;
    }

    public void setApplicationMsg(String applicationMsg) {
        this.applicationMsg = applicationMsg;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
