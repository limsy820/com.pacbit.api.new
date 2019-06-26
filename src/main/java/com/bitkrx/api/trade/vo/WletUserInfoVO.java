package com.bitkrx.api.trade.vo;

public class WletUserInfoVO {

    private String wletAdr = "";
    private String cnKndCd = "";
    private String cnKndNm = "";
    private String userEmail = "";
    private String totAmt = "";
    private String posAmt = "";
    private String useAmt = "";

    public String getWletAdr() {
        return wletAdr;
    }

    public void setWletAdr(String wletAdr) {
        this.wletAdr = wletAdr;
    }

    public String getCnKndCd() {
        return cnKndCd;
    }

    public void setCnKndCd(String cnKndCd) {
        this.cnKndCd = cnKndCd;
    }

    public String getCnKndNm() {
        return cnKndNm;
    }

    public void setCnKndNm(String cnKndNm) {
        this.cnKndNm = cnKndNm;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTotAmt() {
        return totAmt;
    }

    public void setTotAmt(String totAmt) {
        this.totAmt = totAmt;
    }

    public String getPosAmt() {
        return posAmt;
    }

    public void setPosAmt(String posAmt) {
        this.posAmt = posAmt;
    }

    public String getUseAmt() {
        return useAmt;
    }

    public void setUseAmt(String useAmt) {
        this.useAmt = useAmt;
    }
}
