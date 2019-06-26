package com.bitkrx.api.auth.vo;

public class SendEmailCustVO {

    private String sndCode = "";
    private String userEmail = "";
    private String sndYn = "";
    private String ctntsCode = "";
    private String clientCd = "";
    private String regIp = "";

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getClientCd() {
        return clientCd;
    }

    public void setClientCd(String clientCd) {
        this.clientCd = clientCd;
    }

    public String getSndCode() {
        return sndCode;
    }

    public void setSndCode(String sndCode) {
        this.sndCode = sndCode;
    }

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

    public String getCtntsCode() {
        return ctntsCode;
    }

    public void setCtntsCode(String ctntsCode) {
        this.ctntsCode = ctntsCode;
    }
}
