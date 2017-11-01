package com.example.android.requiryv2;

/**
 * Created by upamanyughose on 01/11/17.
 */
public class Domain {

    private String dName;
    private int dNumOfProj;

    public Domain(String dName, int dNumOfProj) {
        this.dName = dName;
        this.dNumOfProj = dNumOfProj;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public void setdNumOfProj(int dNumOfProj) {
        this.dNumOfProj = dNumOfProj;
    }

    public String getdName() {

        return dName;
    }

    public int getdNumOfProj() {
        return dNumOfProj;
    }
}
