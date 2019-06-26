package com.bitkrx.api.trade.vo;

public class FreeWithdrawVO {

    private String cnKndCd = "";
    private String CnKndNm = "";
    private String stdDt = "";
    private String endDt = "";

    public String getCnKndNm() {
        return CnKndNm;
    }

    public void setCnKndNm(String cnKndNm) {
        CnKndNm = cnKndNm;
    }

    public String getCnKndCd() {
        return cnKndCd;
    }

    public void setCnKndCd(String cnKndCd) {
        this.cnKndCd = cnKndCd;
    }

    public String getStdDt() {
        return stdDt;
    }

    public void setStdDt(String stdDt) {
        this.stdDt = stdDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }
}
