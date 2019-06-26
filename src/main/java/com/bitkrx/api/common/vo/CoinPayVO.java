package com.bitkrx.api.common.vo;

public class CoinPayVO {

    private String cnKndCd = "";
    private String cnKndNm = "";
    private String cnPrc = "";
    private String payAmt = "";

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

    public String getCnPrc() {
        return cnPrc;
    }

    public void setCnPrc(String cnPrc) {
        this.cnPrc = cnPrc;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }
}
