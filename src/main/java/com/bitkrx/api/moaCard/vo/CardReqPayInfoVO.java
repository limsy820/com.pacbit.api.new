package com.bitkrx.api.moaCard.vo;

public class CardReqPayInfoVO {

    private String cardActCode = "";
    private String actNo = "";
    private String bankNm = "";
    private String cardReqPrc = "";
    private String dlvr = "";

    public String getDlvr() {return dlvr;}

    public void setDlvr(String dlvr) {this.dlvr = dlvr;}

    public String getCardActCode() {
        return cardActCode;
    }

    public void setCardActCode(String cardActCode) {
        this.cardActCode = cardActCode;
    }

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public String getBankNm() {
        return bankNm;
    }

    public void setBankNm(String bankNm) {
        this.bankNm = bankNm;
    }

    public String getCardReqPrc() {
        return cardReqPrc;
    }

    public void setCardReqPrc(String cardReqPrc) {
        this.cardReqPrc = cardReqPrc;
    }
}
