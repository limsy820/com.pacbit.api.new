package com.bitkrx.api.auth.vo;

public class UserEventMail {

    private String userEmail    = "";
    private String sndYn        = "";
    private String uptDt        = "";

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSndYn() {
        return sndYn;
    }

    public void setSndYn(String sndYn) {
        this.sndYn = sndYn;
    }

    public String getUptDt() {
        return uptDt;
    }

    public void setUptDt(String uptDt) {
        this.uptDt = uptDt;
    }
}
