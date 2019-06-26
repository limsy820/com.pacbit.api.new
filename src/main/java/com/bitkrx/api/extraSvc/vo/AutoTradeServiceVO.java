package com.bitkrx.api.extraSvc.vo;

import java.util.List;
import java.util.Map;

public class AutoTradeServiceVO {

    private String cnKndCd = "";
    private String userEmail = "";
    private String reqDt = "";
    private String endDt = "";
    private String payAmt = "";
    private List<Map> RESULT = null;

    public List<Map> getRESULT() {
        return RESULT;
    }

    public void setRESULT(List<Map> RESULT) {
        this.RESULT = RESULT;
    }

    public String getCnKndCd() {
        return cnKndCd;
    }

    public void setCnKndCd(String cnKndCd) {
        this.cnKndCd = cnKndCd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getReqDt() {
        return reqDt;
    }

    public void setReqDt(String reqDt) {
        this.reqDt = reqDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }
}
