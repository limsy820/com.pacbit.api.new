package com.bitkrx.api.auth.vo;

public class ForeignIpVO {

    private String ip             = "";
    private String regDt          = "";
    private String internalYn     = "";


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getInternalYn() {
        return internalYn;
    }

    public void setInternalYn(String internalYn) {
        this.internalYn = internalYn;
    }
}
