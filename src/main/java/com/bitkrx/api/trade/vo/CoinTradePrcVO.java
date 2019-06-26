package com.bitkrx.api.trade.vo;

public class CoinTradePrcVO {

    private String cnKndCd = "";
    private String cnKndNm = "";
    private String coinPrc = "";
    private String krw = "";
    private String btc = "";
    private String eth = "";
    private String usdt = "";

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

    public String getCoinPrc() {
        return coinPrc;
    }

    public void setCoinPrc(String coinPrc) {
        this.coinPrc = coinPrc;
    }

    public String getKrw() {
        return krw;
    }

    public void setKrw(String krw) {
        this.krw = krw;
    }

    public String getBtc() {
        return btc;
    }

    public void setBtc(String btc) {
        this.btc = btc;
    }

    public String getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }

    public String getUsdt() {
        return usdt;
    }

    public void setUsdt(String usdt) {
        this.usdt = usdt;
    }
}
