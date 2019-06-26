package com.bitkrx.api.trade.vo;

public class TrnInfoVO {

    private String genKey = "";
    private String compCode = "";
    private String userEmail = "";
    private String exCryCode = "";
    private String exRate = "";
    private String crgPrc = "";
    private String absTxt = "";
    private String dpsAmt = "";
    private String cardNo = "";
    private String dptFee = "";
    private String status = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDptFee() {
        return dptFee;
    }

    public void setDptFee(String dptFee) {
        this.dptFee = dptFee;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getAbsTxt() {
        return absTxt;
    }

    public void setAbsTxt(String absTxt) {
        this.absTxt = absTxt;
    }

    public String getDpsAmt() {
        return dpsAmt;
    }

    public void setDpsAmt(String dpsAmt) {
        this.dpsAmt = dpsAmt;
    }

    public String getGenKey() {
        return genKey;
    }

    public void setGenKey(String genKey) {
        this.genKey = genKey;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getExCryCode() {
        return exCryCode;
    }

    public void setExCryCode(String exCryCode) {
        this.exCryCode = exCryCode;
    }

    public String getExRate() {
        return exRate;
    }

    public void setExRate(String exRate) {
        this.exRate = exRate;
    }

    public String getCrgPrc() {
        return crgPrc;
    }

    public void setCrgPrc(String crgPrc) {
        this.crgPrc = crgPrc;
    }
}
