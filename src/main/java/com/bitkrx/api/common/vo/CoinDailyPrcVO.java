package com.bitkrx.api.common.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

public class CoinDailyPrcVO extends CmeExtendListVO {

    private String prcPer       = ""; //전일대비
    private String cnKndCd      = ""; //코인코드
    private String regDt        = ""; //일자
    private String fstPrc       = ""; //오픈가
    private String minPrc       = ""; //최저가
    private String maxPrc       = ""; //최고가
    private String lstPrc       = ""; //종가
    private String totAmt       = ""; //거래량
    private String stdDate      = "";
    private String endDate      = "";
    private String mkState      = "";

    public String getMkState() {
        return mkState;
    }

    public void setMkState(String mkState) {
        this.mkState = mkState;
    }

    public String getPrcPer() {
        return prcPer;
    }

    public void setPrcPer(String prcPer) {
        this.prcPer = prcPer;
    }

    public String getCnKndCd() {
        return cnKndCd;
    }

    public void setCnKndCd(String cnKndCd) {
        this.cnKndCd = cnKndCd;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getFstPrc() {
        return fstPrc;
    }

    public void setFstPrc(String fstPrc) {
        this.fstPrc = fstPrc;
    }

    public String getMinPrc() {
        return minPrc;
    }

    public void setMinPrc(String minPrc) {
        this.minPrc = minPrc;
    }

    public String getMaxPrc() {
        return maxPrc;
    }

    public void setMaxPrc(String maxPrc) {
        this.maxPrc = maxPrc;
    }

    public String getLstPrc() {
        return lstPrc;
    }

    public void setLstPrc(String lstPrc) {
        this.lstPrc = lstPrc;
    }

    public String getTotAmt() {
        return totAmt;
    }

    public void setTotAmt(String totAmt) {
        this.totAmt = totAmt;
    }

    public String getStdDate() {
        return stdDate;
    }

    public void setStdDate(String stdDate) {
        this.stdDate = stdDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
