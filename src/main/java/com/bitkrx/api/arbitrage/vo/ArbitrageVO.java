package com.bitkrx.api.arbitrage.vo;

import java.util.List;
import java.util.Map;

public class ArbitrageVO {
    private String userEmail = "";
    private String reqDt = "";
    private String endDt = "";
    private String payAmt = "";
    private List<Map> RESULT = null;

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

    public List<Map> getRESULT() {
        return RESULT;
    }

    public void setRESULT(List<Map> RESULT) {
        this.RESULT = RESULT;
    }
}
