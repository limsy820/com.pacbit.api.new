package com.bitkrx.api.auth.vo;

public class UserTradeCheckVO {

    private String userEmail        = "";
    private String curcyCd          = "";
    private String tradeYn          = "";
    private String mkState          = "";
    private String stdDate          = "";
    private String endDate          = "";
    private String selUseYn         = "";
    private String mkKndCd          = "";
    private String cnKndCd          = "";
    private String tradePrc         = "";

    private String cnKndNm          = "";
    private String buyLmtAsking     = "";
    private String buyLmtRate       = "";
    private String buyUseYn       = "";

    public String getBuyUseYn() {
        return buyUseYn;
    }

    public void setBuyUseYn(String buyUseYn) {
        this.buyUseYn = buyUseYn;
    }

    public String getCnKndNm() {
        return cnKndNm;
    }

    public void setCnKndNm(String cnKndNm) {
        this.cnKndNm = cnKndNm;
    }

    public String getBuyLmtAsking() {
        return buyLmtAsking;
    }

    public void setBuyLmtAsking(String buyLmtAsking) {
        this.buyLmtAsking = buyLmtAsking;
    }

    public String getBuyLmtRate() {
        return buyLmtRate;
    }

    public void setBuyLmtRate(String buyLmtRate) {
        this.buyLmtRate = buyLmtRate;
    }

    public String getTradePrc() {
        return tradePrc;
    }

    public void setTradePrc(String tradePrc) {
        this.tradePrc = tradePrc;
    }

    public String getMkKndCd() {
        return mkKndCd;
    }

    public void setMkKndCd(String mkKndCd) {
        this.mkKndCd = mkKndCd;
    }

    public String getCnKndCd() {
        return cnKndCd;
    }

    public void setCnKndCd(String cnKndCd) {
        this.cnKndCd = cnKndCd;
    }

    public String getSelUseYn() {return selUseYn;}

    public void setSelUseYn(String selUseYn) {this.selUseYn = selUseYn;}

    public String getMkState() {return mkState;}

    public void setMkState(String mkState) {this.mkState = mkState;}

    public String getStdDate() {return stdDate;}

    public void setStdDate(String stdDate) {this.stdDate = stdDate;}

    public String getEndDate() {return endDate;}

    public void setEndDate(String endDate) {this.endDate = endDate;}

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCurcyCd() {
        return curcyCd;
    }

    public void setCurcyCd(String curcyCd) {
        this.curcyCd = curcyCd;
    }

    public String getTradeYn() {
        return tradeYn;
    }

    public void setTradeYn(String tradeYn) {
        this.tradeYn = tradeYn;
    }
}
