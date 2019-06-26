package com.bitkrx.api.trade.vo;

public class DepoBankVO {

    private String krxActCode = "";
    private String bankNm = "";
    private String bankCd = "";
    private String accNo = "";
    private String accNm = "";
    private String userEmail = "";
    private String userAccNm = "";

    public String getUserAccNm() { return userAccNm; }

    public void setUserAccNm(String userAccNm) { this.userAccNm = userAccNm; }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getKrxActCode() {
        return krxActCode;
    }

    public void setKrxActCode(String krxActCode) {
        this.krxActCode = krxActCode;
    }

    public String getBankNm() {
        return bankNm;
    }

    public void setBankNm(String bankNm) {
        this.bankNm = bankNm;
    }

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccNm() {
        return accNm;
    }

    public void setAccNm(String accNm) {
        this.accNm = accNm;
    }
}
