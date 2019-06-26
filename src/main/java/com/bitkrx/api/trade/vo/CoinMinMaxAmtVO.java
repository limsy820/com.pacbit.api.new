package com.bitkrx.api.trade.vo;

public class CoinMinMaxAmtVO {

    private String userEmail = "";
    private String price = "";
    private String payCuycyCd = "";
    private String buyCuycyCd = "";
    private String payCnAmt = "";
    private String payMaxPrc = "";
    private String payMinPrc = "";
    private String buyMaxPrc = "";
    private String buyMinPrc = "";

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayCuycyCd() {
        return payCuycyCd;
    }

    public void setPayCuycyCd(String payCuycyCd) {
        this.payCuycyCd = payCuycyCd;
    }

    public String getBuyCuycyCd() {
        return buyCuycyCd;
    }

    public void setBuyCuycyCd(String buyCuycyCd) {
        this.buyCuycyCd = buyCuycyCd;
    }

    public String getPayCnAmt() {
        return payCnAmt;
    }

    public void setPayCnAmt(String payCnAmt) {
        this.payCnAmt = payCnAmt;
    }

    public String getPayMaxPrc() {
        return payMaxPrc;
    }

    public void setPayMaxPrc(String payMaxPrc) {
        this.payMaxPrc = payMaxPrc;
    }

    public String getPayMinPrc() {
        return payMinPrc;
    }

    public void setPayMinPrc(String payMinPrc) {
        this.payMinPrc = payMinPrc;
    }

    public String getBuyMaxPrc() {
        return buyMaxPrc;
    }

    public void setBuyMaxPrc(String buyMaxPrc) {
        this.buyMaxPrc = buyMaxPrc;
    }

    public String getBuyMinPrc() {
        return buyMinPrc;
    }

    public void setBuyMinPrc(String buyMinPrc) {
        this.buyMinPrc = buyMinPrc;
    }
}
